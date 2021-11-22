package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.entity.AnswerTemplateEntity;
import dev.chel_shev.nelly.entity.CommandEntity;
import dev.chel_shev.nelly.inquiry.prototype.Inquiry;
import dev.chel_shev.nelly.repository.AnswerTemplateRepository;
import dev.chel_shev.nelly.type.CommandLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerTemplateRepository repository;

    public String generateAnswer(CommandLevel level, Inquiry inquiry) {
        Set<String> answers = inquiry.getAnswer().get(level);
        return new ArrayList<>(answers).get(new Random().nextInt(answers.size()));
    }

    public void saveAnswers(Map<CommandLevel, Set<String>> answer, CommandEntity command) {
        Set<AnswerTemplateEntity> all = repository.findAllByCommand(command);
        Set<AnswerTemplateEntity> collect = answer.entrySet().stream()
                .flatMap(e -> e.getValue().stream().map(a -> new AnswerTemplateEntity(null, a, e.getKey(), command)))
                .collect(Collectors.toSet());
        all.addAll(collect);
        repository.saveAll(all);
    }
}
