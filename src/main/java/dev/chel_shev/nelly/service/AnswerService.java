package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.entity.AnswerTemplateEntity;
import dev.chel_shev.nelly.entity.CommandEntity;
import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.command.CommandLevel;
import dev.chel_shev.nelly.repository.AnswerTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerTemplateRepository repository;

    public String generateAnswer(CommandLevel level, Inquiry inquiry) {
        List<AnswerTemplateEntity> answerTemplateEntityList = repository.findAllByLevelAndCommand(level, inquiry.getCommand());
        return answerTemplateEntityList.get(new Random().nextInt(answerTemplateEntityList.size())).getTemplate();
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
