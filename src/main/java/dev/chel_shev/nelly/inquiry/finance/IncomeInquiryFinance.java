package dev.chel_shev.nelly.inquiry.finance;

import dev.chel_shev.nelly.inquiry.utils.InquiryId;
import dev.chel_shev.nelly.type.CommandLevel;
import dev.chel_shev.nelly.type.InquiryType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@Scope("prototype")
@InquiryId(type = InquiryType.INCOME)
public class IncomeInquiryFinance extends InquiryFinance {

    @Override
    public void initAnswers() {
        Set<String> firstLevel = new HashSet<>() {{
            add("Неверный формат!");
        }};
        Set<String> secondLevel = new HashSet<>() {{
            add("`Текущий остаток:\r\n %s`\r\n\r\n" + "Доход добавлен!");
        }};
        Set<String> thirdLevel = new HashSet<>() {{
            add("Напишите данные о доходах в виде:\r\n" +
                    "` «Название дохода: Сумма», без кавычек`");
        }};
        getAnswer().put(CommandLevel.FIRST, firstLevel);
        getAnswer().put(CommandLevel.SECOND, secondLevel);
        getAnswer().put(CommandLevel.THIRD, thirdLevel);
    }
}