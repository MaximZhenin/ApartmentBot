package bot.telegramm.bot.handler.buttons;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface KeyboardMarkupBuilder {
    void setChatId(Long chatId);

    KeyboardMarkupBuilder setText(String text);

    KeyboardMarkupBuilder row();

    KeyboardMarkupBuilder endRow();

    SendMessage build();

}
