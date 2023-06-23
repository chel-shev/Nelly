package dev.chel_shev.fast.event.language;

import dev.chel_shev.fast.FastMarkdown;
import dev.chel_shev.fast.entity.WordEntity;
import dev.chel_shev.fast.event.FastEventHandler;
import dev.chel_shev.fast.inquiry.keyboard.common.CommonKeyboardInquiry;
import dev.chel_shev.fast.service.LanguageService;
import dev.chel_shev.fast.type.FastBotCommandLevel;
import dev.chel_shev.fast.type.FastKeyboardType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class FastLanguageHandler extends FastEventHandler<FastLanguageEvent> {

    private final LanguageService service;
    private final FastLanguageConfig languageConfig;

    @Override
    public void inlineExecutionLogic(FastLanguageEvent e, CallbackQuery callbackQuery) {
        WordEntity ru = service.getTranslation(e.getWord(), new Locale("ru"));
        if (ru.getWord().equals(callbackQuery.getData())) {
            e.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.FIRST, languageConfig));
        } else {
            e.setAnswerMessage(String.format(answerService.generateAnswer(FastBotCommandLevel.SECOND, languageConfig), FastMarkdown.bolt(e.getWord().getWord()), FastMarkdown.italic(e.getWord().getTranscription()), FastMarkdown.bolt(ru.getWord())));
        }
        e.setKeyboardType(FastKeyboardType.REPLY);
        e.setKeyboardButtons(keyboardService.getButton(CommonKeyboardInquiry.class));
        e.setClosed(true);
    }
}
