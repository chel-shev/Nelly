package dev.chel_shev.fast.inquiry.command.unknown;

import dev.chel_shev.fast.inquiry.command.FastCommandInquiryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UnknownHandler extends FastCommandInquiryHandler<UnknownInquiry> {
}