package dev.chel_shev.fast.inquiry.command.unknownUser;

import dev.chel_shev.fast.inquiry.command.FastCommandInquiryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UnknownUserHandler extends FastCommandInquiryHandler<UnknownUserInquiry> {
}