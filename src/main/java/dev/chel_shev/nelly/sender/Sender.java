package dev.chel_shev.nelly.sender;

import dev.chel_shev.nelly.TelegramBotMain;
import dev.chel_shev.nelly.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public interface Sender {

    void send(UserEntity user, String massage);
}
