package dev.chel_shev.fast.service;

import dev.chel_shev.fast.entity.user.FastUserEntity;
import dev.chel_shev.fast.inquiry.FastInquiry;
import dev.chel_shev.fast.inquiry.FastInquiryId;
import dev.chel_shev.fast.repository.FastBotCommandRepository;
import dev.chel_shev.fast.type.FastInquiryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FastKeyboardService {

    private final FastBotCommandRepository commandRepository;
    private final FastUserSubscriptionService subscriptionService;

    public boolean isKeyboardInquiry(String text) {
        List<FastInquiryType> fastInquiryTypes = Arrays.asList(FastInquiryType.KEYBOARD, FastInquiryType.KEYBOARD_SUBSCRIPTION);
        return commandRepository.existsByLabelAndTypeInOrNameAndTypeIn(text, fastInquiryTypes, text, fastInquiryTypes);
    }

    public List<String> getButtons(FastUserEntity user){
        List<String> subscriptions = new ArrayList<>(subscriptionService.getSubscriptions(user));
        subscriptions.add("Подписки");
        return subscriptions;
    }

    public List<String> getButtons(Class<? extends FastInquiry> clazz) {
        FastInquiryId annotation = clazz.getAnnotation(FastInquiryId.class);
        return Arrays.stream(annotation.buttons()).map(c -> c.getAnnotation(FastInquiryId.class).label()).toList();
    }

    public List<String> getButton(Class<? extends FastInquiry> clazz) {
        FastInquiryId annotation = clazz.getAnnotation(FastInquiryId.class);
        return Collections.singletonList(annotation.label());
    }
}