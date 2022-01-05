package dev.chel_shev.nelly.inquiry.bot;

import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.utils.InquiryId;
import dev.chel_shev.nelly.type.InquiryType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static dev.chel_shev.nelly.type.InquiryType.START;

@InquiryId(START)
public class StartInquiry extends Inquiry {
}