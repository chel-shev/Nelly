package dev.chel_shev.nelly.inquiry.prototype.finance;

import dev.chel_shev.nelly.inquiry.InquiryId;
import dev.chel_shev.nelly.type.CommandLevel;
import dev.chel_shev.nelly.type.InquiryType;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@Scope("prototype")
@NoArgsConstructor
@InquiryId(type = InquiryType.LOAN, command = "/loan")
public class LoanInquiryFinance extends InquiryFinance {

    @Override
    public void initAnswers() {
        Set<String> firstLevel = new HashSet<>() {{
            add("Неверный формат!");
        }};
        Set<String> secondLevel = new HashSet<>() {{
            add("Займ добавлен!");
        }};
        Set<String> thirdLevel = new HashSet<>() {{
            add("Напишите данные о займе в виде:\r\n" +
                    "` «Название: -Сумма», без кавычек, знак «-», если Вы даете в долг.`\r\n" +
                    "\r\nТекущие займы (₽):\r\n%s");
        }};
        getAnswer().put(CommandLevel.FIRST, firstLevel);
        getAnswer().put(CommandLevel.SECOND, secondLevel);
        getAnswer().put(CommandLevel.THIRD, thirdLevel);
    }
}
