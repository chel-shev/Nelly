package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.entity.finance.ExpenseCategoryEntity;
import dev.chel_shev.nelly.entity.finance.ExpenseProductEntity;
import dev.chel_shev.nelly.repository.ExpenseProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseProductService {

    private final ExpenseProductRepository epr;
    private final ExpenseCategoryService ecs;

    public ExpenseProductEntity getProduct(String productName) {
        Optional<ExpenseProductEntity> epo = epr.findByName(productName);
        if (epo.isPresent())
            return epo.get();
        else {
            ExpenseCategoryEntity ec = ecs.getCategory(productName);
            return save(new ExpenseProductEntity(productName, ec));
        }
    }

    public ExpenseProductEntity save(ExpenseProductEntity ep) {
        return epr.save(ep);
    }

    public static List<String> prepareName(String productName, boolean size) {
        String[] nameArray = productName.split(" ");
        List<String> resultList = new ArrayList<>();
        for (String name : nameArray) {
            if (!name.matches("([^aA-zZаА-яЯ]*[\\d]*)*")) {
                if (name.matches("[\\d]+[aA-zZаА-яЯ]+[^aA-zZаА-яЯ]*") && !size) continue;
                resultList.add(name);
            }
        }
        return resultList;
    }

    public static String getCleanName(String productName) {
        return String.join(" ", ExpenseProductService.prepareName(productName, true));
    }
}