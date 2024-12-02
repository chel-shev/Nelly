package dev.chel_shev.fast.scheduler.task;

import dev.chel_shev.fast.FastMarkdown;
import dev.chel_shev.fast.FastSender;
import dev.chel_shev.fast.entity.WordEntity;
import dev.chel_shev.fast.entity.user.FastUserEntity;
import dev.chel_shev.fast.event.language.FastLanguageEvent;
import dev.chel_shev.fast.service.FastEventService;
import dev.chel_shev.fast.service.LanguageService;
import dev.chel_shev.fast.type.FastKeyboardType;
import dev.chel_shev.fast.type.FastStudyTimeType;
import dev.chel_shev.fast.type.FastStudyTimelineType;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static dev.chel_shev.fast.type.FastStudyTimeType.T_10_15;

public class WordTask<E extends FastLanguageEvent> extends TimerTask {

    private final FastEventService<E> eventService;
    private final LanguageService languageService;
    private final FastSender sender;
    private final String chatId;
    private final E e;
    private final Calendar calendar;


    public WordTask(FastEventService<E> eventService, LanguageService languageService, FastSender sender, E e, Calendar calendar) {
        this.eventService = eventService;
        this.languageService = languageService;
        this.sender = sender;
        this.chatId = e.getUserSubscription().getFastUser().getChatId();
        this.e = e;
        this.calendar = calendar;
    }

    @Override
    public void run() {
        try {
            WordEntity ru1 = languageService.getTranslation(e.getWord(), new Locale("ru"));
            boolean mute = calendar.get(Calendar.HOUR_OF_DAY) > 0 && calendar.get(Calendar.HOUR_OF_DAY) < 8;
            Message message;
            if (e.getTimeline() == FastStudyTimelineType.T_0) {
                String text = "Запомни \uD83C\uDDEC\uD83C\uDDE7%s — \\[%s\\] — \uD83C\uDDF7\uD83C\uDDFA%s";
                message = sender.sendMessage(chatId, String.format(text,
                                FastMarkdown.bolt(e.getWord().getWord()),
                                FastMarkdown.italic(e.getWord().getTranscription()),
                                FastMarkdown.spoiler(ru1.getWord())), FastKeyboardType.INLINE,
                        List.of("Учим", "Знаю"), true, mute);
            } else {
                List<WordEntity> ru = languageService.getRandomWords(5, new Locale("ru"));
                Set<String> answer = new HashSet<>(ru.stream().map(WordEntity::getWord).toList());
                answer.add(ru1.getWord());
                String text = "Выберите перевод слова %s — \\[%s\\]";
                message = sender.sendMessage(chatId, String.format(text,
                                FastMarkdown.bolt(e.getWord().getWord()),
                                FastMarkdown.italic(e.getWord().getTranscription())),
                        FastKeyboardType.INLINE, answer.stream().toList(), true, mute);
            }
            e.setAnswerMessageId(message.getMessageId());
            eventService.save(e);
            if (e.getTimeline() == FastStudyTimelineType.T_0) {
                FastStudyTimeType next = T_10_15;
                LocalDateTime eventDateTime = LocalDateTime.of(LocalDate.now().plusDays(1), next.getTimeEvent());
                e.setDateTime(eventDateTime);
                FastUserEntity fastUser = e.getUserSubscription().getFastUser();
                WordEntity unknownWord = languageService.getUnknownWord(fastUser);
                e.setWord(unknownWord);
                e.setTime(next);
                e.setClosed(false);
                e.setTimeline(FastStudyTimelineType.T_0);
                e.setAnswerMessageId(null);
                e.setId(null);
                eventService.save(e);
                languageService.saveNewUserWord(eventDateTime, unknownWord, fastUser);
            }
        } catch (Exception ex) {
            System.out.println("error running thread " + ex.getMessage());
        }
    }
}