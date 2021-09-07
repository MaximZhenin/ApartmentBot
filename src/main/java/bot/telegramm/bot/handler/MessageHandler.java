package bot.telegramm.bot.handler;

import bot.telegramm.bot.api.BotCondition;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;


public interface MessageHandler {

    boolean canHandle(BotCondition botCondition);

    BotApiMethod<Message> handle(Message message);

}