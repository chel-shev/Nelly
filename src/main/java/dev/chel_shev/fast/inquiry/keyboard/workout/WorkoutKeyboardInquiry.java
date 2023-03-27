package dev.chel_shev.fast.inquiry.keyboard.workout;

import dev.chel_shev.fast.inquiry.FastInquiryId;
import dev.chel_shev.fast.inquiry.command.workout.add.WorkoutAddInquiry;
import dev.chel_shev.fast.inquiry.command.workout.remove.WorkoutRemoveInquiry;
import dev.chel_shev.fast.inquiry.keyboard.FastKeyboardInquiry;
import dev.chel_shev.fast.inquiry.keyboard.cancel.CancelKeyboardInquiry;
import dev.chel_shev.fast.type.FastInquiryType;

@FastInquiryId(command = "/workout", buttons = {WorkoutAddInquiry.class, WorkoutRemoveInquiry.class, CancelKeyboardInquiry.class}, type = FastInquiryType.KEYBOARD_SUBSCRIPTION, label = "\uD83E\uDD38\u200D♀️ Спорт")
public class WorkoutKeyboardInquiry extends FastKeyboardInquiry {
}