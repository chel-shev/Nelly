package dev.chel_shev.nelly.inquiry.prototype.bday;

import dev.chel_shev.nelly.entity.BdayInquiryEntity;
import dev.chel_shev.nelly.entity.InquiryEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.inquiry.InquiryId;
import dev.chel_shev.nelly.inquiry.prototype.Inquiry;
import dev.chel_shev.nelly.type.CommandLevel;
import dev.chel_shev.nelly.type.InquiryType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Strings.isNullOrEmpty;

@Getter
@Setter
@Component
@Scope("prototype")
@Slf4j
@InquiryId(type = InquiryType.BDAY_REMOVE, command = "/bday_remove")
public class BdayRemoveInquiry extends Inquiry {

    private String name;

    @Override
    public void generate(InquiryEntity entity, UserEntity user) throws TelegramBotException {
        setMessage(entity.getMessage());
        setClosed(entity.isClosed());
        setDate(entity.getDate());
        setAnswerMessage(entity.getAnswerMessage());
        setKeyboardType(entity.getKeyboardType());
        setCommand(entity.getCommand());
        setUser(user);
        this.name = ((BdayInquiryEntity) entity).getName();
        log.info("INIT Inquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", getId(), getMessage(), getType(), getDate(), isClosed());
    }

    @Override
    public void initAnswers() {
        Set<String> firstLevel = new HashSet<>() {{
            add("День Рождения удален!");
            add("Ну поссорились, так и скажи..");
            add("Мне тоже не нравился...");
        }};
        Set<String> secondLevel = new HashSet<>() {{
            add("Дня Рождения не найден!");
            add("Не были знакомы, и удалять нечего..");
            add("Ты ошибся, я о таких даже не слышала.");
        }};
        Set<String> thirdLevel = new HashSet<>() {{
            add("Введите 'имя', чей день рождения хотите удалить!");
        }};
        getAnswer().put(CommandLevel.FIRST, firstLevel);
        getAnswer().put(CommandLevel.SECOND, secondLevel);
        getAnswer().put(CommandLevel.THIRD, thirdLevel);
    }

    @Override
    public boolean isNotReadyForExecute() {
        return isNullOrEmpty(name);
    }

    public void setData() {
        this.name = getMessage();
    }

    public InquiryEntity getEntity() {
        return new BdayInquiryEntity(this);
    }
}
