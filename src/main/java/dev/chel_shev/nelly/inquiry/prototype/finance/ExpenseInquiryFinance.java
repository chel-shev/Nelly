package dev.chel_shev.nelly.inquiry.prototype.finance;

import dev.chel_shev.nelly.inquiry.InquiryId;
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
@InquiryId(type = InquiryType.EXPENSE, command = "/expense")
public class ExpenseInquiryFinance extends InquiryFinance {

    @Override
    public void initAnswers() {
        Set<String> firstLevel = new HashSet<>() {{
            add("Расход добавлен!");
        }};
        Set<String> secondLevel = new HashSet<>() {{
            add("Чек добавлен!");
        }};
        Set<String> thirdLevel = new HashSet<>() {{
            add("Выберите ОДНО из трёх действий:\r\n" +
                    "` 1. Отправьте фото QR-кода`\r\n" +
                    "` 2. Вышлите строку QR-кода`\r\n" +
                    "` 3. Напишите данные о расходах в виде: «Покупка: Сумма», без кавычек`");
        }};
        getAnswer().put(CommandLevel.FIRST, firstLevel);
        getAnswer().put(CommandLevel.SECOND, secondLevel);
        getAnswer().put(CommandLevel.THIRD, thirdLevel);
    }
}