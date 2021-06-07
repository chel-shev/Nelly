package dev.chel_shev.nelly.inquiry.command;

import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.InquiryAnswer;
import dev.chel_shev.nelly.inquiry.InquiryId;
import dev.chel_shev.nelly.inquiry.InquiryType;
import dev.chel_shev.nelly.keyboard.KeyboardType;
import dev.chel_shev.nelly.service.InquiryService;
import dev.chel_shev.nelly.service.UserService;
import org.springframework.stereotype.Component;

@Component
@InquiryId(type = InquiryType.COMMAND, command = "/start")
public class StartInquiry extends Inquiry {

    private final UserService userService;

    public StartInquiry(UserService userService, InquiryService inquiryService) {
        super(inquiryService);
        this.userService = userService;
    }

    @Override
    public InquiryAnswer logic() {
        if (userService.isExist(getUser().getChatId())) {
            return new InquiryAnswer(getUser(), "User already exists!", KeyboardType.NONE);
        } else {
            userService.save(getUser());
            return new InquiryAnswer(getUser(), "Welcome!", KeyboardType.NONE);
        }
    }
}