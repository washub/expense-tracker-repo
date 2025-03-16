package com.expense.ds_service.services;

import com.expense.ds_service.dto.AIMessageDTO;
import com.expense.ds_service.dto.ExpenseDTO;
import com.expense.ds_service.services.contracts.LLMService;
import com.expense.ds_service.utils.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private static final Logger log = LoggerFactory.getLogger(MessageUtils.class);

    private final LLMService llmService;
    public MessageService(LLMService llmService){
        this.llmService = llmService;
    }

    public ExpenseDTO processMessage(AIMessageDTO message){
        if(MessageUtils.isBankMessage(message.message())){
            log.info("Retrieved message belongs to expense category :: processing the message");
            return llmService.runLLM(message);
        }

        log.info("Retrieved message is not of expense category");

        return null;
    }
}
