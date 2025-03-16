package com.expense.ds_service.services.contracts;

import com.expense.ds_service.dto.AIMessageDTO;
import com.expense.ds_service.dto.ExpenseDTO;

public interface LLMService {
    ExpenseDTO runLLM_traditional(AIMessageDTO message);
    ExpenseDTO runLLM_latestPrompt(AIMessageDTO message);
}
