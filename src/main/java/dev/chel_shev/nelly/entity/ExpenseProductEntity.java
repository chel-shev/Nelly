package dev.chel_shev.nelly.entity;

import lombok.Data;

import javax.persistence.*;

import static java.util.Objects.isNull;

@Data
@Entity(name = "expense_product")
public class ExpenseProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    private ExpenseCategoryEntity expenseCategory;

    public ExpenseProductEntity() {
    }

    public ExpenseProductEntity(String name, ExpenseCategoryEntity expenseCategory) {
        this.name = name;
        this.expenseCategory = expenseCategory;
    }

    public String getCategoryName(){
        return isNull(expenseCategory) ? "" : expenseCategory.getName();
    }
}