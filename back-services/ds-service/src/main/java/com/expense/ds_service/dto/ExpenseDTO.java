package com.expense.ds_service.dto;

import java.util.Optional;

public record ExpenseDTO(Optional<Float> amount, Optional<String> merchant, Optional<String> currency) {
}
