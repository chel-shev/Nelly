package dev.chel_shev.fast.inquiry.keyboard.bday;

import dev.chel_shev.fast.inquiry.FastInquiryId;
import dev.chel_shev.fast.inquiry.command.bday.add.BdayAddInquiry;
import dev.chel_shev.fast.inquiry.command.bday.remove.BdayRemoveInquiry;
import dev.chel_shev.fast.inquiry.keyboard.FastKeyboardInquiry;
import dev.chel_shev.fast.inquiry.keyboard.cancel.CancelKeyboardInquiry;
import dev.chel_shev.fast.type.FastInquiryType;

@FastInquiryId(command = "/bday", buttons = {BdayAddInquiry.class, BdayRemoveInquiry.class, CancelKeyboardInquiry.class}, type = FastInquiryType.KEYBOARD_SUBSCRIPTION, label = "\uD83D\uDCC6 ДР")
public class BdayKeyboardInquiry extends FastKeyboardInquiry {
}