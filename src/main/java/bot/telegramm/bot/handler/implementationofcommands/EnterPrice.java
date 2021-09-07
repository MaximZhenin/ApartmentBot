package bot.telegramm.bot.handler.implementationofcommands;

import bot.telegramm.bot.api.BotCondition;
import bot.telegramm.bot.api.BotConditionContext;
import bot.telegramm.bot.handler.MessageHandler;
import bot.telegramm.bot.handler.annotation.BotCommand;
import bot.telegramm.bot.inmemory.UserInfo;
import bot.telegramm.bot.listcommands.Commands;
import bot.telegramm.bot.models.Person;
import bot.telegramm.bot.service.ReplyMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Arrays;
import java.util.List;

@BotCommand(command = Commands.SET_PRICE)
public class EnterPrice implements MessageHandler {
    @Autowired
    private BotConditionContext userDataCache;
    private final ReplyMessageService replyMessageService;
    private  final UserInfo info;

    public EnterPrice(ReplyMessageService replyMessageService, UserInfo info) {
        this.replyMessageService = replyMessageService;
        this.info = info;
    }

    @Override
    public SendMessage handle(Message message) {
        Long chatId = message.getChatId();
        int userId = Math.toIntExact(message.getFrom().getId());
        List<String> twoDigits = Arrays.asList(message.getText().split("-"));
        try {
            int end = Integer.parseInt(twoDigits.get(1));
            System.out.printf(String.valueOf(end));
            int start = Integer.parseInt(twoDigits.get(0));
            System.out.printf(String.valueOf(start));
            if(end < start){
                return replyMessageService.getTextMessage(chatId, "Неверный диапозон");
            } else {
                Person pers = new Person(userId, chatId);
                pers.getCrit().setEndValue(end);
                pers.getCrit().setStartValue(start);
                info.saveUserCriterionData(userId, pers.getCrit());
                userDataCache.setCurrentBotConditionForUserWithId(userId, BotCondition.SET_SUBWAY);
            }
        } catch (NumberFormatException e){
            return replyMessageService.getTextMessage(chatId, "Нужно подать два чилса разделенными -");
        }


        return replyMessageService.getTextMessage(chatId, "Цена принята \nВведите нужные станции метро:");
    }


    @Override
    public boolean canHandle(BotCondition botCondition) {
        return botCondition.equals(BotCondition.SET_PRICE);
    }
}
