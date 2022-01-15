package dev.chel_shev.nelly.entity.finance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "expense_category")
public class ExpenseCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "expenseCategory")
    private List<ExpenseProductEntity> productList;

    public ExpenseCategoryEntity(String name) {
        this.name = name;
    }
}