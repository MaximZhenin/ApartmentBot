package bot.telegramm.bot;

import bot.telegramm.bot.api.BotCondition;
import bot.telegramm.bot.api.BotConditionContext;
import bot.telegramm.bot.handler.ConditionHandler;
import bot.telegramm.bot.handler.MessageHandler;
import bot.telegramm.bot.handler.annotation.BotCommand;
import bot.telegramm.bot.service.ReplyMessageService;
import bot.telegramm.bot.telegramexception.NoHandlerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessingUpdates {
    private final ConditionHandler botConditionHandler;

    private final BotConditionContext botConditionUserContext;

    private final ReplyMessageService replyMessageService;


    public PartialBotApiMethod<? extends Serializable> handleUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            System.out.printf("Zashel\n");
            Message message = update.getMessage();
            BotCondition botCondition1 = getBotCondition1(message);
//            BotCondition botCondition = getBotCondition(message);
            log.info(
                    "Message from: {}; " +
                            "chat id: {};  " +
                            "text: {}; " +
                            "bot condition: {}",
                    message.getFrom().getUserName(),
                    message.getChatId(),
                    message.getText(),
                    botCondition1
            );
            System.out.printf("Состояние бота1 = " + botCondition1 + '\n');
//            System.out.printf("Состояние бота =  " + botCondition + '\n');

            return botConditionHandler.handleTextMessageByCondition(message, botCondition1);
        }
        else {
            log.error(
                    "Unsupported request from: {}; " +
                            "chatId: {}",
                    update.getMessage().getFrom().getUserName(),
                    update.getMessage().getChatId()
            );

            return replyMessageService.getTextMessage(update.getMessage().getChatId(), "Я могу принимать только текстовые сообщения!");
        }
    }


/*    private BotCondition getBotCondition(Message message) {
        Integer userId = Math.toIntExact(message.getFrom().getId());
        String userTextMessage = message.getText();
        BotCondition botCondition;

        switch (userTextMessage) {
            case "/start":
                botCondition = BotCondition.START;
                break;
            case "Помощь":
                botCondition = BotCondition.HELP;
                break;
            case "Введите цену квартиры(формат start-end)":
                botCondition = BotCondition.GET_PRICE;
                break;
            case "Введите метро":
                botCondition = BotCondition.GET_SUBWAY;
                break;
            default:
                botCondition = botConditionUserContext.getCurrentBotConditionForUserById(userId);
        }
        botConditionUserContext.setCurrentBotConditionForUserWithId(userId, botCondition);
        return botCondition;
    }*/
    protected BotCondition getBotCondition1(Message message) {
        Integer userId = Math.toIntExact(message.getFrom().getId());
        String text = message.getText();
        BotCondition botCondition = EnumSet.allOf(BotCondition.class)
                .stream()
                .filter(e -> e.toString().equals(text))
                .findFirst()
                .orElse(botConditionUserContext.getCurrentBotConditionForUserById(userId));
        botConditionUserContext.setCurrentBotConditionForUserWithId(userId, botCondition);
        System.out.printf("Отработка метода getBotCondition1 = " + String.valueOf(botCondition));
        return botCondition;
    }

}
