package bot.telegramm.bot.handler;

import bot.telegramm.bot.api.BotCondition;
import bot.telegramm.bot.handler.annotation.BotCommand;
import bot.telegramm.bot.service.ReplyMessageService;
import bot.telegramm.bot.telegramexception.NoHandlerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Component
public class ConditionHandler {

    private final List<MessageHandler> messageHandlers;

    private final ReplyMessageService replyMessageService;

    public ConditionHandler(List<MessageHandler> messageHandlers, ReplyMessageService replyMessageService) {
        this.messageHandlers = messageHandlers;
        this.replyMessageService = replyMessageService;
    }

    public BotApiMethod<Message> handleTextMessageByCondition(Message message, BotCondition botCondition) {
        MessageHandler messageHandler;
        try {
            messageHandler = messageHandlers.stream()
                    .filter(m -> m.canHandle(botCondition))
                    .findAny()
                    .orElseThrow(NoHandlerException::new);
        } catch (NoHandlerException e) {
            System.out.printf("No handler was found for current bot condition: {}", botCondition);
            log.error("No handler was found for current bot condition: {}", botCondition);
            return replyMessageService.getTextMessage(message.getChatId(), "Невозможно обработать запрос.");
        }
        return messageHandler.handle(message);
    }
}
