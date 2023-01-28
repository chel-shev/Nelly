package dev.chel_shev.nelly.entity.event.finance;

import lombok.Data;

import javax.persistence.*;

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
