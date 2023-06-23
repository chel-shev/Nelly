package dev.chel_shev.fast.service;

import dev.chel_shev.fast.FastMarkdown;
import dev.chel_shev.fast.inquiry.FastInquiryConfig;
import dev.chel_shev.fast.type.FastBotCommandLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FastAnswerService {

    public String generateAnswer(FastBotCommandLevel level, FastInquiryConfig i) {
        Set<String> answers = i.getAnswer().get(level.getLabel());
        return FastMarkdown.filter(new ArrayList<>(answers).get(new Random().nextInt(answers.size())));
    }

    public String generateAnswer(FastBotCommandLevel level, FastInquiryConfig i, Object... value) {
        Set<String> answers = i.getAnswer().get(level.getLabel());
        String answer = FastMarkdown.filter(new ArrayList<>(answers).get(new Random().nextInt(answers.size())));
        return answer.formatted(value);
    }
}