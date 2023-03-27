package dev.chel_shev.fast;

import dev.chel_shev.fast.type.FastKeyboardType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static dev.chel_shev.fast.type.FastKeyboardType.*;

@Component
public class FastBotKeyboardFactory {

    public ReplyKeyboard getKeyBoard(FastKeyboardType type, List<String> buttons) {
        return getKeyBoard(type, buttons, false, false);
    }

    public ReplyKeyboard getKeyBoard(FastKeyboardType type, List<String> buttons, boolean backButton, boolean cancelButton) {
        ReplyKeyboard keyboard;
        if (type == INLINE)
            keyboard = new InlineKeyboardMarkup(getInlineButtons(buttons));
        else if (type == REPLY) {
            keyboard = new ReplyKeyboardMarkup(getButtons(buttons));
            ((ReplyKeyboardMarkup) keyboard).setSelective(true);
            ((ReplyKeyboardMarkup) keyboard).setResizeKeyboard(true);
            ((ReplyKeyboardMarkup) keyboard).setOneTimeKeyboard(false);
        } else if (type == FORCE)
            keyboard = new ForceReplyKeyboard();
        else
            keyboard = new ReplyKeyboardRemove();
        return keyboard;
    }

    private List<KeyboardRow> getButtons(List<String> titles) {
        List<KeyboardRow> rows = new ArrayList<>();
        List<String> rowNames = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        for (int i = 0; i < titles.size(); i++) {
            if (i % 2 == 0) {
                if (i != 0) {
                    row.addAll(rowNames);
                    rows.add(row);
                }
                row = new KeyboardRow();
                rowNames = new ArrayList<>();
            }
            rowNames.add(titles.get(i));
        }
        row.addAll(rowNames);
        rows.add(row);
        return rows;
    }

    private List<List<InlineKeyboardButton>> getInlineButtons(List<String> titles) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> rowNames = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            if (i % 2 == 0) {
                if (i != 0) {
                    row.addAll(rowNames);
                    rows.add(row);
                }
                row = new ArrayList<>();
                rowNames = new ArrayList<>();
            }
            InlineKeyboardButton button = InlineKeyboardButton.builder().text(titles.get(i)).callbackData(titles.get(i)).build();
            rowNames.add(button);
        }
        row.addAll(rowNames);
        rows.add(row);
        return rows;
    }
}