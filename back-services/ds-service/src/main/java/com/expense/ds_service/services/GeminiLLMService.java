package com.expense.ds_service.services;

import com.expense.ds_service.dto.AIMessageDTO;
import com.expense.ds_service.dto.ExpenseDTO;
import com.expense.ds_service.services.contracts.LLMService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GeminiLLMService implements LLMService {
    private final static Logger log = LoggerFactory.getLogger(GeminiLLMService.class);

    @Value("classpath:/prompts/system_prompt.st")
    private Resource systemPromptResource;

    @Value("classpath:/prompts/user_prompt.st")
    private Resource userPromptResource;

    private final ChatClient chatClient;
    /**
     * You are an expert data analyst who is pro at extracting data from raw message.
     * Only extract relevant information from message.
     * If you don't find relevant attribute asked to be extracted then return null for that attribute.
     * For currency please convert to respective country currency code.
     */
    public GeminiLLMService(ChatClient.Builder chatClient){
        this.chatClient = chatClient.build();
    }

    /**
     * This method uses traditional more customizable PromptTemplate</p>
     * PromptTemplate help you inject your custom message in prompt `please give me joke on this subject {topic}`
     * <b>topic</b> is your custom message to prompt.<p>
     * PromptTemplate can let build list of prompts with messages i.e. systemMessage, UserMessage etc.<p>
     *
     * <pre>
     *     {@code
     *      PromptTemplate template = new PromptTemplate("please give me joke on this subject {topic}");
     *      var userMessageTemplate = template
     *          .create(Map.of("topic", message))
     *          .getContents();
     *     }
     * </pre>
     * @param message
     * @return ExpenseDTO
     */
    @Override
    public ExpenseDTO runLLM_traditional(AIMessageDTO message) {

        var systemMessage = new SystemMessage(systemPromptResource);

        PromptTemplate template = new PromptTemplate(userPromptResource);
        var userMessageTemplate = template
                                .create(Map.of("message", message.message()))
                                .getContents();

        var userMessage = new UserMessage(userMessageTemplate);

        var prompt = new Prompt(List.of(systemMessage, userMessage));

        log.debug("Prompt created for LLM Model : {}", prompt.getContents());
        //this will return string in response from llm
        //chatClient.prompt(prompt).call().content()
        //below we will do structured output
        // in case need list of entities then use
        // chatClient.prompt(prompt).call().entity(new ParameterizedTypeReference<>() {});
        return chatClient.prompt(prompt).call().entity(ExpenseDTO.class);

    }

    /**
     * In upgraded chatClient we can use fluent api<p>
     * we can chain system, user message in same flow of prompt
     * <pre>
     *    {@code
     *       chatClient.prompt()
     *          .system(systemPromptResource)
     *          .user(u-> u.text("tell me dad joke {topic}").param("topic", message))
     *          .call()
     *          .entity(ExpenseDTO.class);
     *    }
     * </pre>
     * @param message
     * @return ExpenseDTO
     */

    @Override
    public ExpenseDTO runLLM_latestPrompt(AIMessageDTO message) {
        return chatClient.prompt()
                .system(systemPromptResource)
                .user(u-> u.text(userPromptResource).param("message", message.message()))
                .call()
                .entity(ExpenseDTO.class);
    }
}
