package dev.chel_shev.nelly.inquiry.reminder;

import dev.chel_shev.nelly.entity.InquiryEntity;
import dev.chel_shev.nelly.entity.ReminderEntity;
import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.utils.InquiryId;
import dev.chel_shev.nelly.type.CommandLevel;
import dev.chel_shev.nelly.type.InquiryType;
import dev.chel_shev.nelly.type.PeriodType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Objects.isNull;

@Component
@Getter
@Setter
@Scope("prototype")
@Slf4j
@InquiryId(type = InquiryType.REMINDER_ADD)
public class ReminderAddInquiry extends Inquiry {

    private PeriodType periodType;
    private String description;
    private String name;

    @Override
    public void initAnswers() {
        Set<String> firstLevel = new HashSet<>() {{
            add("День Рождения добавлен!");
            add("Все прошло успешно, человечек в списке:)");
            add("Напомню при необходимости поздавить!");
        }};
        Set<String> secondLevel = new HashSet<>() {{
            add("День Рождения уже существует!");
            add("Такое уже знаем :)");
        }};
        Set<String> thirdLevel = new HashSet<>() {{
            add("Введите 'название', чтоб добавить напоминание, следующим шагом нужно будет выбрать период уведомлений!");
        }};
        getAnswer().put(CommandLevel.FIRST, firstLevel);
        getAnswer().put(CommandLevel.SECOND, secondLevel);
        getAnswer().put(CommandLevel.THIRD, thirdLevel);
    }

    @Override
    public boolean isNotReadyForExecute() {
        return isNullOrEmpty(name) || isNull(periodType);
    }

    @Override
    public InquiryEntity getEntity() {
        return new ReminderEntity(this);
    }
}