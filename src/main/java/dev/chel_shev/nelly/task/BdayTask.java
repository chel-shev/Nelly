package dev.chel_shev.nelly.task;

import dev.chel_shev.nelly.bot.BotSender;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.inquiry.InquiryAnswer;
import dev.chel_shev.nelly.type.KeyboardType;

import java.util.TimerTask;

public class BdayTask extends TimerTask {

    private final BotSender sender;
    private final UserEntity user;
    private final String name;

    public BdayTask(BotSender sender, UserEntity user, String name) {
        this.sender = sender;
        this.user = user;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            sender.sendMessage(new InquiryAnswer(user, name + " празднует свой День Рождения, поздравь, если уместно!", KeyboardType.NONE));
        } catch (Exception ex) {
            System.out.println("error running thread " + ex.getMessage());
        }
    }
}