package dev.chel_shev.fast.entity.inquiry;


import dev.chel_shev.fast.entity.FastCommandEntity;
import dev.chel_shev.fast.inquiry.command.reminder.remove.ReminderRemoveInquiry;
import dev.chel_shev.fast.inquiry.command.subscription.subscribe.SubscriptionSubscribeInquiry;
import dev.chel_shev.fast.inquiry.command.subscription.unsubscribe.SubscriptionUnsubscribeInquiry;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "fast_inquiry_subscription")
@NoArgsConstructor
public class SubscriptionInquiryEntity extends FastInquiryEntity{

    @ManyToOne
    private FastCommandEntity subscription;

    public SubscriptionInquiryEntity(SubscriptionSubscribeInquiry inquiry) {
        super(inquiry);
        subscription = inquiry.getSubscription();
    }

    public SubscriptionInquiryEntity(SubscriptionUnsubscribeInquiry inquiry) {
        super(inquiry);
        subscription = inquiry.getSubscription();
    }
}
