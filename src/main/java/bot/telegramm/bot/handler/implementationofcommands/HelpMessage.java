package bot.telegramm.bot.handler.implementationofcommands;

import bot.telegramm.bot.api.BotCondition;
import bot.telegramm.bot.handler.MessageHandler;
import bot.telegramm.bot.handler.annotation.BotCommand;
import bot.telegramm.bot.listcommands.Commands;
import bot.telegramm.bot.service.ReplyMessageService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


@BotCommand(command = Commands.HELP)
public class HelpMessage implements MessageHandler {
    private final ReplyMessageService replyMessageService;

    public HelpMessage(ReplyMessageService replyMessageService) {
        this.replyMessageService = replyMessageService;
    }

    @Override
    public SendMessage handle(Message message) {
        Long chatId = message.getChatId();
        return replyMessageService.getTextMessage(chatId,
                String.join("\n\n",
                        "Справочная инфа!"
                ));
    }

    @Override
    public boolean canHandle(BotCondition botCondition) {
        return botCondition.equals(BotCondition.HELP);
    }
}
