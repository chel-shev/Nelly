package dev.chel_shev.nelly.entity.finance;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "income_category")
public class IncomeCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "incomeCategory")
    private List<IncomeEntity> incomeList;
}