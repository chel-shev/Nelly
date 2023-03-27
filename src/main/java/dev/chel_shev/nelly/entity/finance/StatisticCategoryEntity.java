package dev.chel_shev.nelly.entity.finance;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "statistic_category")
public class StatisticCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String singleName;
    private Long frequency;

    @ManyToOne
    private ExpenseCategoryEntity expenseCategory;
}
