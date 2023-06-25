package dev.chel_shev.fast.inquiry.keyboard.keyboard;

import dev.chel_shev.fast.inquiry.FastInquiryId;
import dev.chel_shev.fast.inquiry.keyboard.FastKeyboardInquiry;
import dev.chel_shev.fast.type.FastInquiryType;

@FastInquiryId(command = "/keyboard", type = FastInquiryType.KEYBOARD, label = "")
public class KeyboardInquiry extends FastKeyboardInquiry {
    @Override
    public String toString() {
        return super.toString() +
                '}';
    }
}