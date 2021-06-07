package dev.chel_shev.nelly.inquiry.command;

import dev.chel_shev.nelly.entity.BdayEntity;
import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.InquiryAnswer;
import dev.chel_shev.nelly.inquiry.InquiryId;
import dev.chel_shev.nelly.inquiry.InquiryType;
import dev.chel_shev.nelly.keyboard.KeyboardType;
import dev.chel_shev.nelly.service.BdayService;
import dev.chel_shev.nelly.service.InquiryService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@InquiryId(type = InquiryType.COMMAND, command = "/bday")
public class BdayInquiry extends Inquiry {

    private final BdayService service;

    protected BdayInquiry(InquiryService inquiryService, BdayService service) {
        super(inquiryService);
        this.service = service;
    }

    @Override
    public InquiryAnswer logic() {
        if (service.isExist(getArgFromMassage(1), getArgFromMassage(2))) {
            return new InquiryAnswer(getUser(), "Birthday already exists!", KeyboardType.NONE);
        } else {
            service.save(new BdayEntity(null, getArgFromMassage(1), LocalDate.parse(getArgFromMassage(2)), getUser()));
            return new InquiryAnswer(getUser(), "Birthday add!", KeyboardType.NONE);
        }
    }
}
