package dev.chel_shev.fast.event.language;

import dev.chel_shev.fast.FastMarkdown;
import dev.chel_shev.fast.entity.WordEntity;
import dev.chel_shev.fast.entity.user.FastUserEntity;
import dev.chel_shev.fast.event.FastEventHandler;
import dev.chel_shev.fast.service.LanguageService;
import dev.chel_shev.fast.type.FastBotCommandLevel;
import dev.chel_shev.fast.type.FastKeyboardType;
import dev.chel_shev.fast.type.FastStudyTimeType;
import dev.chel_shev.fast.type.FastStudyTimelineType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static dev.chel_shev.fast.type.FastStudyTimeType.T_10_15;

@Slf4j
@Service
@RequiredArgsConstructor
public class FastLanguageHandler extends FastEventHandler<FastLanguageEvent> {

    private final LanguageService service;
    private final FastLanguageConfig languageConfig;

    @Override
    public void inlineExecutionLogic(FastLanguageEvent e, CallbackQuery callbackQuery) {
        if (Objects.equals(callbackQuery.getData(), "Учим")) {
            initNextEvents(e);
            initNextWordEvent(e);
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

    private void initNextEvents(FastLanguageEvent e) {
        for (FastStudyTimelineType timeline : FastStudyTimelineType.values()) {
            if (timeline == FastStudyTimelineType.T_0) continue;
            FastLanguageEvent next = new FastLanguageEvent();
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

    private void initNextWordEvent(FastLanguageEvent e) {
        if (e.getTimeline() == FastStudyTimelineType.T_0) {
            FastStudyTimeType next = FastStudyTimeType.getNextTo();
            if (next == T_10_15) return;
            LocalDate eventDate = next.ordinal() < e.getTime().ordinal() ? LocalDate.now().plusDays(1) : LocalDate.now();
            LocalDateTime eventDateTime = LocalDateTime.of(eventDate, next.getTimeEvent());
            e.setDateTime(eventDateTime);
            FastUserEntity fastUser = e.getUserSubscription().getFastUser();
            WordEntity unknownWord = service.getUnknownWord(fastUser);
            e.setWord(unknownWord);
            e.setTime(next);
            e.setClosed(false);
            e.setTimeline(FastStudyTimelineType.T_0);
            e.setAnswerMessageId(null);
            e.setId(null);
            eventService.save(e);
            service.saveNewUserWord(eventDateTime, unknownWord, fastUser);
        }
    }

    @Override
    public void inlinePreparationLogic(FastLanguageEvent e, CallbackQuery callbackQuery) {
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
