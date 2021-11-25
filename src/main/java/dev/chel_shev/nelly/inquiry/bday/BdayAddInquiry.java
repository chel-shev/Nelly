package dev.chel_shev.nelly.inquiry.bday;

import dev.chel_shev.nelly.entity.BdayInquiryEntity;
import dev.chel_shev.nelly.entity.InquiryEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.inquiry.utils.InquiryId;
import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.type.CommandLevel;
import dev.chel_shev.nelly.type.InquiryType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Objects.isNull;

@Component
@Getter
@Setter
@Scope("prototype")
@Slf4j
@InquiryId(type = InquiryType.BDAY_ADD)
public class BdayAddInquiry extends Inquiry {

    private LocalDateTime bdayDate;
    private String name;

    @Override
    public void init(InquiryEntity entity, UserEntity user) throws TelegramBotException {
        setMessage(entity.getMessage());
        setClosed(entity.isClosed());
        setDate(entity.getDate());
        setAnswerMessage(entity.getAnswerMessage());
        setKeyboardType(entity.getKeyboardType());
        setCommand(entity.getCommand());
        setUser(user);
        this.bdayDate = ((BdayInquiryEntity) entity).getBdayDate();
        this.name = ((BdayInquiryEntity) entity).getName();
        initAnswers();
        log.info("INIT Inquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", getId(), getMessage(), getType(), getDate(), isClosed());
    }

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
            add("Введите 'дата (в формате `дд.мм.гггг` или `дд.мм`) имя', чтоб добавить день рождения!");
        }};
        getAnswer().put(CommandLevel.FIRST, firstLevel);
        getAnswer().put(CommandLevel.SECOND, secondLevel);
        getAnswer().put(CommandLevel.THIRD, thirdLevel);
    }

    @Override
    public boolean isNotReadyForExecute() {
        return isNullOrEmpty(name) || isNull(bdayDate);
    }

    @Override
    public InquiryEntity getEntity() {
        return new BdayInquiryEntity(this);
    }
}