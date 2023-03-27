package dev.chel_shev.fast.inquiry.command.subscription.subscribe;

import dev.chel_shev.fast.entity.FastCommandEntity;
import dev.chel_shev.fast.entity.inquiry.FastInquiryEntity;
import dev.chel_shev.fast.entity.inquiry.SubscriptionInquiryEntity;
import dev.chel_shev.fast.inquiry.FastInquiry;
import dev.chel_shev.fast.inquiry.FastInquiryId;
import dev.chel_shev.fast.inquiry.command.FastCommandInquiry;
import dev.chel_shev.fast.type.FastInquiryType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import static java.util.Objects.isNull;

@Getter
@Setter
@Slf4j
@FastInquiryId(command = "/subscription_subscribe", type = FastInquiryType.COMMAND, label = "Подписаться")
public class SubscriptionSubscribeInquiry extends FastCommandInquiry {

    private FastCommandEntity subscription;

    @Override
    public FastInquiryEntity getEntity() {
        return new SubscriptionInquiryEntity(this);
    }

    @Override
    public boolean isNotReadyForExecute() {
        return isNull(subscription);
    }
}