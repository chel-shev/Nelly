package dev.chel_shev.nelly.bot;

import dev.chel_shev.nelly.bot.event.Event;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.bot.inquiry.Inquiry;
import dev.chel_shev.nelly.type.KeyboardType;
import dev.chel_shev.nelly.util.ApplicationContextUtils;
import dev.chel_shev.nelly.util.DecoderPhotoQR;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Comparator;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class BotResources {

    public String getQRDataFromPhoto(Message message) {
        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
        NellyNotBot<? extends Inquiry, ? extends Event> telegramBot = (NellyNotBot<? extends Inquiry, ? extends Event>) appCtx.getBean("nellyNotBot");
        try {
            @NotNull PhotoSize photo = getPhoto(message);
            String path = getFilePath(telegramBot, photo);
            java.io.File filePhoto = telegramBot.downloadFile(path);
            @NotNull String qr = DecoderPhotoQR.decode(filePhoto);
            return qr;
        } catch (TelegramApiException e) {
            throw new TelegramBotException("Ошибка получения фотографии!", KeyboardType.CANCEL);
        } catch (NullPointerException | IOException e) {
            throw new TelegramBotException("QR-код прочитать не удалось!", KeyboardType.CANCEL);
        }
    }

    public String getFilePath(NellyNotBot<? extends Inquiry, ? extends Event> telegramBot, PhotoSize photo) {
        if (isNull(photo.getFilePath()) || photo.getFilePath().isEmpty()) {
            GetFile getFileMethod = new GetFile();
            getFileMethod.setFileId(photo.getFileId());
            try {
                File file = telegramBot.execute(getFileMethod);
                return file.getFilePath();
            } catch (TelegramApiException e) {
                throw new TelegramBotException("Не удалось получить файл!", KeyboardType.CANCEL);
            }
        } else {
            return photo.getFilePath();
        }
    }

    public PhotoSize getPhoto(Message message) {
        return message.getPhoto()
                .stream()
                .max(Comparator.comparing(PhotoSize::getFileSize))
                .orElseThrow(() -> new TelegramBotException("Ошибка получения фотографии!", KeyboardType.CANCEL));
    }
}
