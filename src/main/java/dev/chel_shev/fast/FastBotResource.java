package dev.chel_shev.fast;

import dev.chel_shev.fast.event.FastEvent;
import dev.chel_shev.fast.inquiry.FastInquiry;
import dev.chel_shev.nelly.util.ApplicationContextUtils;
import dev.chel_shev.nelly.util.DecoderPhotoQR;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.Comparator;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class FastBotResource {

    private final ApplicationContext applicationContext;

    public String getQRDataFromPhoto(Message message) {
        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
        FastBot<? extends FastInquiry, ? extends FastEvent> telegramBot = (FastBot<? extends FastInquiry, ? extends FastEvent>) appCtx.getBean("nellyNotBot");
        try {
            @NotNull PhotoSize photo = getPhoto(message);
            String path = getFilePath(telegramBot, photo);
            java.io.File filePhoto = telegramBot.downloadFile(path);
            @NotNull String qr = DecoderPhotoQR.decode(filePhoto);
            return qr;
        } catch (TelegramApiException e) {
            throw new FastBotException("Ошибка получения фотографии!");
        } catch (NullPointerException | IOException e) {
            throw new FastBotException("QR-код прочитать не удалось!");
        }
    }

    public String getFilePath(FastBot telegramBot, PhotoSize photo) {
        if (isNull(photo.getFilePath()) || photo.getFilePath().isEmpty()) {
            GetFile getFileMethod = new GetFile();
            getFileMethod.setFileId(photo.getFileId());
            try {
                File file = telegramBot.execute(getFileMethod);
                return file.getFilePath();
            } catch (TelegramApiException e) {
                throw new FastBotException("Не удалось получить файл!");
            }
        } else {
            return photo.getFilePath();
        }
    }

    public PhotoSize getPhoto(Message message) {
        return message.getPhoto()
                .stream()
                .max(Comparator.comparing(PhotoSize::getFileSize))
                .orElseThrow(() -> new FastBotException("Ошибка получения фотографии!"));
    }
}
