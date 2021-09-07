package bot.telegramm.bot.handler.buttons;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class ReplyKeyboardMarkupBuilder implements  KeyboardMarkupBuilder{
    private Long chatId;
    private String text;

    private final List<KeyboardRow> keyboard = new ArrayList<>();
    private KeyboardRow row;

    private ReplyKeyboardMarkupBuilder(){
    }

    @Override
    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    @Override
    public ReplyKeyboardMarkupBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public static ReplyKeyboardMarkupBuilder create() {
        return new ReplyKeyboardMarkupBuilder();
    }

    public static ReplyKeyboardMarkupBuilder create(Long chatId) {
        ReplyKeyboardMarkupBuilder builder = new ReplyKeyboardMarkupBuilder();
        builder.setChatId(chatId);
        return builder;
    }

    @Override
    public ReplyKeyboardMarkupBuilder row() {
        this.row = new KeyboardRow();
        return this;
    }

    public ReplyKeyboardMarkupBuilder button(String text) {
        row.add(text);
        return this;
    }

    @Override
    public ReplyKeyboardMarkupBuilder endRow() {
        this.keyboard.add(this.row);
        this.row = null;
        return this;
    }

    @Override
    public SendMessage build() {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);
        keyboardMarkup.setKeyboard(keyboard);

        message.setReplyMarkup(keyboardMarkup);
        return message;
    }
}
