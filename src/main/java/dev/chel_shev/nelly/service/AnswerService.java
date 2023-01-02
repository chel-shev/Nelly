package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.bot.inquiry.InquiryConfig;
import dev.chel_shev.nelly.type.CommandLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AnswerService {

    public String generateAnswer(CommandLevel level, InquiryConfig i) {
        Set<String> answers = i.getAnswer().get(level.label);
        return new ArrayList<>(answers).get(new Random().nextInt(answers.size()));
    }

    public String generateAnswer(CommandLevel level, InquiryConfig i, Object... value) {
        Set<String> answers = i.getAnswer().get(level.label);
        String answer = new ArrayList<>(answers).get(new Random().nextInt(answers.size()));
        return answer.formatted(value);
    }
}