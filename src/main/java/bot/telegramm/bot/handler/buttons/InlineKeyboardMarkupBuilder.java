package bot.telegramm.bot.handler.buttons;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboardMarkupBuilder implements KeyboardMarkupBuilder{
    private Long chatId;
    private String text;

    private List<InlineKeyboardButton> row = null;
    private final List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

    private InlineKeyboardMarkupBuilder() {
    }

    @Override
    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    @Override
    public InlineKeyboardMarkupBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public static InlineKeyboardMarkupBuilder create() {
        return new InlineKeyboardMarkupBuilder();
    }

    public static InlineKeyboardMarkupBuilder create(Long chatId) {
        InlineKeyboardMarkupBuilder builder = new InlineKeyboardMarkupBuilder();
        builder.setChatId(chatId);
        return builder;
    }

    @Override
    public InlineKeyboardMarkupBuilder row() {
        this.row = new ArrayList<>();
        return this;
    }

    public InlineKeyboardMarkupBuilder button(String text, String callbackData) {
        InlineKeyboardButton obj = new InlineKeyboardButton();
        obj.setText(text);
        obj.setCallbackData(callbackData);
        row.add(obj);
        return this;
    }

    public InlineKeyboardMarkupBuilder buttonWithURL(String text, String URL) {
        InlineKeyboardButton obj = new InlineKeyboardButton();
        obj.setText(text);
        obj.setUrl(URL);
        row.add(obj);
        return this;
    }

    @Override
    public InlineKeyboardMarkupBuilder endRow() {
        this.keyboard.add(this.row);
        this.row = null;
        return this;
    }

    @Override
    public SendMessage build() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(keyboard);
        SendMessage mes = new SendMessage();
        mes.setChatId(String.valueOf(chatId));
        mes.setText(text);
        mes.setReplyMarkup(keyboardMarkup);
        return mes;
    }

    public EditMessageText rebuild(Long messageId) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(keyboard);
        EditMessageText mesText = new EditMessageText();
        mesText.setChatId(String.valueOf(chatId));
        mesText.setMessageId(Math.toIntExact(messageId));
        mesText.setReplyMarkup(keyboardMarkup);

        return mesText;
    }
}
