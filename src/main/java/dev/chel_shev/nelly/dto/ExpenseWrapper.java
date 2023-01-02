package dev.chel_shev.nelly.dto;

import dev.chel_shev.nelly.entity.finance.ExpenseEntity;
import lombok.Data;

import java.util.List;

@Data
public class ExpenseWrapper {
    private List<ExpenseEntity> expense;
}
