package dev.chel_shev.fast.inquiry.command.bday.remove;

import dev.chel_shev.fast.FastBotException;
import dev.chel_shev.fast.entity.user.FastUserEntity;
import dev.chel_shev.fast.entity.inquiry.FastBdayInquiryEntity;
import dev.chel_shev.fast.entity.inquiry.FastInquiryEntity;
import dev.chel_shev.fast.inquiry.FastInquiry;
import dev.chel_shev.fast.inquiry.FastInquiryId;
import dev.chel_shev.fast.type.FastInquiryType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import static com.google.common.base.Strings.isNullOrEmpty;

@Getter
@Setter
@Slf4j
@FastInquiryId(command = "/bday_remove", type = FastInquiryType.COMMAND, label = "\uD83D\uDCC6 Удалить")
public class BdayRemoveInquiry extends FastInquiry {

    private String name;

    @Override
    public void init(FastInquiryEntity entity, FastUserEntity userEntity) throws FastBotException {
        super.init(entity, userEntity);
        this.name = ((FastBdayInquiryEntity) entity).getName();
        log.info("INIT {}", this);
    }

    @Override
    public boolean isNotReadyForExecute() {
        return isNullOrEmpty(name);
    }

    @Override
    public FastInquiryEntity getEntity() {
        return new FastBdayInquiryEntity(this);
    }

    @Override
    public String toString() {
        return super.toString() +
                ", name=" + name +
                '}';
    }
}
