package dev.chel_shev.fast.scheduler.task;

import dev.chel_shev.fast.FastSender;

import java.util.TimerTask;

public class DeleteTask extends TimerTask {

    private final FastSender sender;
    private final Integer messageId;
    private final String chatId;

    public DeleteTask(FastSender sender, Integer messageId, String chatId) {
        this.sender = sender;
        this.messageId = messageId;
        this.chatId = chatId;
    }

    @Override
    public void run() {
        sender.deleteMessage(chatId, messageId);
    }
}
