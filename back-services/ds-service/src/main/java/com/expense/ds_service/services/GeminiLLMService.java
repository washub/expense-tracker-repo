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

    public GeminiLLMService(ChatClient.Builder chatClient){
        this.chatClient = chatClient.build();
    }
    @Override
    public ExpenseDTO runLLM(AIMessageDTO message) {
        /**
         * You are an expert data analyst who is pro at extracting data from raw message.
         * Only extract relevant information from message.
         * If you don't find relevant attribute asked to be extracted then return null for that attribute.
         * For currency please convert to respective country currency code.
         */
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
//        chatClient.prompt(prompt).call().entity(new ParameterizedTypeReference<>() {
//        });
        return chatClient.prompt(prompt).call().entity(ExpenseDTO.class);
    }
}
