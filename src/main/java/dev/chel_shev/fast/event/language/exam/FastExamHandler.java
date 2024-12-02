package dev.chel_shev.fast.event.language.exam;

import dev.chel_shev.fast.FastMarkdown;
import dev.chel_shev.fast.entity.WordEntity;
import dev.chel_shev.fast.event.FastEventHandler;
import dev.chel_shev.fast.service.LanguageService;
import dev.chel_shev.fast.type.FastBotCommandLevel;
import dev.chel_shev.fast.type.FastKeyboardType;
import dev.chel_shev.fast.type.FastStudyTimelineType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class FastExamHandler extends FastEventHandler<FastExamEvent> {

    private final LanguageService service;
    private final FastExamConfig languageConfig;

    @Override
    public void inlineExecutionLogic(FastExamEvent e, CallbackQuery callbackQuery) {
        if (Objects.equals(callbackQuery.getData(), "Учим")) {
            initNextEvents(e);
            WordEntity ru1 = service.getTranslation(e.getWord(), new Locale("ru"));
            String text = "Запомни \uD83C\uDDEC\uD83C\uDDE7%s — \\[%s\\] — \uD83C\uDDF7\uD83C\uDDFA%s";
            e.setAnswerMessage(String.format(text, FastMarkdown.bolt(e.getWord().getWord()), FastMarkdown.italic(e.getWord().getTranscription()), FastMarkdown.spoiler(ru1.getWord())));
        } else {
            WordEntity ru = service.getTranslation(e.getWord(), new Locale("ru"));
            if (ru.getWord().equals(callbackQuery.getData())) {
                e.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.FIRST, languageConfig));
            } else {
                e.setAnswerMessage(String.format(answerService.generateAnswer(FastBotCommandLevel.SECOND, languageConfig), FastMarkdown.bolt(e.getWord().getWord()), FastMarkdown.italic(e.getWord().getTranscription()), FastMarkdown.bolt(ru.getWord())));
            }
        }
        e.setKeyboardType(FastKeyboardType.REPLY);
        e.setKeyboardButtons(keyboardService.getButtons(e.getUserSubscription().getFastUser()));
        e.setClosed(true);
    }

    private void initNextEvents(FastExamEvent e) {
        for (FastStudyTimelineType timeline : FastStudyTimelineType.values()) {
            if (timeline == FastStudyTimelineType.T_0) continue;
            FastExamEvent next = new FastExamEvent();
            next.init(e.getEntity(), e.getUserSubscription());
            LocalDateTime now = LocalDateTime.now(e.getUserSubscription().getFastUser().getZoneOffset());
            next.setDateTime(now.plus(timeline.getMin()));
            next.setTimeline(timeline);
            next.setAnswerMessageId(null);
            next.setAnswerMessage(null);
            next.setKeyboardType(null);
            next.setId(null);
            eventService.save(next);
        }
    }

    @Override
    public void inlinePreparationLogic(FastExamEvent e, CallbackQuery callbackQuery) {
        if (Objects.equals(callbackQuery.getData(), "Знаю")) {
            WordEntity unknownWord = service.getUnknownWord(e.getUserSubscription().getFastUser());
            e.setWord(unknownWord);
            WordEntity ru1 = service.getTranslation(e.getWord(), new Locale("ru"));
            String text = "Запомни \uD83C\uDDEC\uD83C\uDDE7%s — \\[%s\\] — \uD83C\uDDF7\uD83C\uDDFA%s";
            e.setAnswerMessage(String.format(text, FastMarkdown.bolt(e.getWord().getWord()), FastMarkdown.italic(e.getWord().getTranscription()), FastMarkdown.spoiler(ru1.getWord())));
            e.setKeyboardType(FastKeyboardType.INLINE);
            e.setKeyboardButtons(List.of("Учим", "Знаю"));
        } else {
            e.setUnknownWord(true);
        }
        service.saveNewUserWord(e.getDateTime(), e.getWord(), e.getUserSubscription().getFastUser());
    }
}
