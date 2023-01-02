package dev.chel_shev.nelly.entity.finance;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

import static java.util.Objects.isNull;

@Data
@Entity(name = "expense")
public class ExpenseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private Long price;
    private Double quantity;
    private Long amount;
    private boolean checked;
    private boolean deleted;
    private boolean confirmed;

    @ManyToOne
    private ExpenseProductEntity expenseProduct;

    @ManyToOne
    private AccountEntity account;

    public ExpenseEntity() {
    }

    public ExpenseEntity(LocalDateTime date, Long price, Long sum, Double quantity, ExpenseProductEntity expenseProduct) {
        this.date = date;
        this.price = price;
        this.amount = sum;
        this.quantity = quantity;
        this.expenseProduct = expenseProduct;
    }

    public ExpenseEntity(LocalDateTime date, Long amount, ExpenseProductEntity expenseProduct) {
        this.date = date;
        this.price = amount;
        this.amount = amount;
        this.quantity = 1.0;
        this.expenseProduct = expenseProduct;
    }

    public String getProductName() {
        return isNull(expenseProduct) ? "" : expenseProduct.getName();
    }

    public String getProductCategoryName() {
        return isNull(expenseProduct) ? "" : expenseProduct.getCategoryName();
    }

    public ExpenseCategoryEntity getProductCategory() {
        return isNull(expenseProduct) ? null : expenseProduct.getExpenseCategory();
    }
}