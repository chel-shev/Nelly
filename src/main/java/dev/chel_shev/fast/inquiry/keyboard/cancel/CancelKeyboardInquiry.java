package dev.chel_shev.fast.inquiry.keyboard.cancel;

import dev.chel_shev.fast.inquiry.FastInquiryId;
import dev.chel_shev.fast.inquiry.keyboard.FastKeyboardInquiry;
import dev.chel_shev.fast.type.FastInquiryType;

@FastInquiryId(command = "/cancel", buttons = {}, type = FastInquiryType.KEYBOARD, label = "❌Отмена")
public class CancelKeyboardInquiry extends FastKeyboardInquiry {
}