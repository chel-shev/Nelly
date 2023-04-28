package dev.chel_shev.fast.inquiry.command.unknownUser;

import dev.chel_shev.fast.inquiry.command.FastCommandInquiry;
import dev.chel_shev.fast.inquiry.FastInquiryId;
import dev.chel_shev.fast.type.FastInquiryType;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@FastInquiryId(type = FastInquiryType.COMMAND, command = "/unknown_user", label = "")
public class UnknownUserInquiry extends FastCommandInquiry {
}