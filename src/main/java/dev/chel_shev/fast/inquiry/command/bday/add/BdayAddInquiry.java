package dev.chel_shev.fast.inquiry.command.bday.add;

import dev.chel_shev.fast.FastBotException;
import dev.chel_shev.fast.entity.user.FastUserEntity;
import dev.chel_shev.fast.entity.inquiry.FastBdayInquiryEntity;
import dev.chel_shev.fast.entity.inquiry.FastInquiryEntity;
import dev.chel_shev.fast.inquiry.FastInquiryId;
import dev.chel_shev.fast.inquiry.command.FastCommandInquiry;
import dev.chel_shev.fast.type.FastInquiryType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Objects.isNull;

@Getter
@Setter
@Slf4j(topic = "inquiry")
@FastInquiryId(command = "/bday_add", type = FastInquiryType.COMMAND, label = "\uD83D\uDCC6 Добавить")
public class BdayAddInquiry extends FastCommandInquiry {

    private LocalDateTime bdayDate;
    private String name;

    @Override
    public void init(FastInquiryEntity entity, FastUserEntity user) throws FastBotException {
        super.init(entity, user);
        this.bdayDate = ((FastBdayInquiryEntity) entity).getBdayDate();
        this.name = ((FastBdayInquiryEntity) entity).getName();
        log.info("INIT {}", this);
    }

    @Override
    public boolean isNotReadyForExecute() {
        return isNullOrEmpty(name) || isNull(bdayDate);
    }

    @Override
    public FastInquiryEntity getEntity() {
        return new FastBdayInquiryEntity(this);
    }

    @Override
    public String toString() {
        return super.toString() +
                ", bdayDate=" + bdayDate +
                ", name=" + name +
                '}';
    }
}