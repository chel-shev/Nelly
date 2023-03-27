package dev.chel_shev.fast.inquiry.command.start;

import dev.chel_shev.fast.inquiry.FastInquiryId;
import dev.chel_shev.fast.inquiry.command.FastCommandInquiry;
import dev.chel_shev.fast.type.FastInquiryType;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@FastInquiryId(command = "/start", type = FastInquiryType.COMMAND, label = "")
public class StartInquiry extends FastCommandInquiry {
}