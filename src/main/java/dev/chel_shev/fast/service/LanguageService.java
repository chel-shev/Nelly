package dev.chel_shev.fast.service;

import dev.chel_shev.fast.entity.UserWordEntity;
import dev.chel_shev.fast.entity.WordEntity;
import dev.chel_shev.fast.entity.WordTranslateEntity;
import dev.chel_shev.fast.entity.user.FastUserEntity;
import dev.chel_shev.fast.repository.FastUserWordRepository;
import dev.chel_shev.fast.repository.FastWordRepository;
import dev.chel_shev.fast.repository.FastWordTranslateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class LanguageService {

    private final FastWordRepository repository;
    private final FastUserWordRepository userWordRepository;
    private final FastWordTranslateRepository translateRepository;

    public void saveNewUserWord(LocalDateTime eventTime, WordEntity word, FastUserEntity user) {
        userWordRepository.save(new UserWordEntity(eventTime, user, word));
    }

    public WordEntity getUnknownWord(FastUserEntity user) {
        Set<WordEntity> allByLocale = repository.findAllByLocale(Locale.ENGLISH);
        List<String> list = userWordRepository.findAllByUser(user).stream().map(userWordEntity -> userWordEntity.getWord().getWord()).toList();
        List<WordEntity> unknown = allByLocale.stream().filter(e -> !list.contains(e.getWord())).toList();
        return unknown.stream().skip(new Random().nextInt(unknown.size())).limit(1).findFirst().get();
    }

    public List<WordEntity> getRandomWords(int count, Locale locale) {
        Set<WordEntity> allByLocale = repository.findAllByLocale(locale);
        return allByLocale.stream().skip(new Random().nextInt(allByLocale.size())).limit(count).toList();
    }

    public WordEntity getTranslation(WordEntity entity, Locale to) {
        List<WordTranslateEntity> allByWordId = translateRepository.findAllByWord_Id(entity.getId());
        Optional<WordTranslateEntity> wordTranslateEntity = allByWordId.stream().filter(e -> e.getTranslate().getLocale().equals(to)).findFirst();
        return wordTranslateEntity.map(WordTranslateEntity::getTranslate).orElse(null);
    }
}
