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

public class WordTask extends TimerTask {

    private final FastEventService<FastLanguageEvent> eventService;
    private final LanguageService languageService;
    private final FastSender sender;
    private final String chatId;
    private final FastLanguageEvent event;
    private final Calendar calendar;


    public WordTask(FastEventService<FastLanguageEvent> eventService, LanguageService languageService, FastSender sender, FastLanguageEvent event, Calendar calendar) {
        this.eventService = eventService;
        this.languageService = languageService;
        this.sender = sender;
        this.chatId = event.getUserSubscription().getFastUser().getChatId();
        this.event = event;
        this.calendar = calendar;
    }

    @Override
    public void run() {
        try {
            WordEntity ru1 = languageService.getTranslation(event.getWord(), new Locale("ru"));
            boolean mute = calendar.get(Calendar.HOUR_OF_DAY) > 0 && calendar.get(Calendar.HOUR_OF_DAY) < 8;
            Message message;
            if (event.getTimeline() == FastStudyTimelineType.T_0) {
                String text = "Запомни \uD83C\uDDEC\uD83C\uDDE7%s \\- \\[%s\\] \\- \uD83C\uDDF7\uD83C\uDDFA%s";
                message = sender.sendMessage(chatId, String.format(text,
                        FastMarkdown.bolt(event.getWord().getWord()),
                        FastMarkdown.italic(event.getWord().getTranscription()),
                        FastMarkdown.spoiler(ru1.getWord())), true, mute);
            } else {
                List<WordEntity> ru = languageService.getRandomWords(5, new Locale("ru"));
                Set<String> answer = new HashSet<>(ru.stream().map(WordEntity::getWord).toList());
                answer.add(ru1.getWord());
                String text = "Выберите перевод слова %s";
                message = sender.sendMessage(chatId, String.format(text,
                                FastMarkdown.bolt(event.getWord().getWord())),
                        FastKeyboardType.INLINE, answer.stream().toList(), true, mute);
            }
            event.setAnswerMessageId(message.getMessageId());
            event.setClosed(true);
            eventService.save(event);
            if (event.getTimeline() == FastStudyTimelineType.T_0) {
                for (FastStudyTimelineType timeline : FastStudyTimelineType.values()) {
                    if (timeline == FastStudyTimelineType.T_0) continue;
                    LocalDateTime dateTime = event.getDateTime();
                    event.setDateTime(dateTime.plus(timeline.getMin()));
                    event.setClosed(false);
                    event.setTimeline(timeline);
                    event.setAnswerMessageId(null);
                    event.setId(null);
                    eventService.save(event);
                }
                FastStudyTimeType next = event.getTime().getNext();
                LocalDate eventDate = next.ordinal() < event.getTime().ordinal() ? LocalDate.now().plusDays(1) : LocalDate.now();
                LocalDateTime eventDateTime = LocalDateTime.of(eventDate, next.getTimeEvent());
                event.setDateTime(eventDateTime);
                FastUserEntity fastUser = event.getUserSubscription().getFastUser();
                WordEntity unknownWord = languageService.getUnknownWord(fastUser);
                event.setWord(unknownWord);
                event.setTime(next);
                event.setClosed(false);
                event.setTimeline(FastStudyTimelineType.T_0);
                event.setAnswerMessageId(null);
                event.setId(null);
                eventService.save(event);
                languageService.saveNewUserWord(eventDateTime, unknownWord, fastUser);
            }
        } catch (Exception ex) {
            System.out.println("error running thread " + ex.getMessage());
        }
    }
}