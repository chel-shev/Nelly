package dev.chel_shev.fast.inquiry.keyboard;

import dev.chel_shev.fast.inquiry.FastInquiry;
import dev.chel_shev.fast.inquiry.FastInquiryId;
import dev.chel_shev.fast.type.FastInquiryType;

@FastInquiryId(type = FastInquiryType.KEYBOARD, command = "/keyboard", label = "")
public abstract class FastKeyboardInquiry extends FastInquiry {
}