package bot.telegramm.bot.handler.implementationofcommands;

import bot.telegramm.bot.api.BotCondition;
import bot.telegramm.bot.api.BotConditionContext;
import bot.telegramm.bot.handler.MessageHandler;
import bot.telegramm.bot.handler.annotation.BotCommand;
import bot.telegramm.bot.inmemory.UserInfo;
import bot.telegramm.bot.listcommands.Commands;
import bot.telegramm.bot.models.Criterion;
import bot.telegramm.bot.service.ReplyMessageService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Arrays;
import java.util.List;

@BotCommand(command = Commands.SET_LOCATION)
public class EnterSubWay implements MessageHandler {
    private BotConditionContext userDataCache;
    private final ReplyMessageService replyMessageService;
    private  final UserInfo info;

    public EnterSubWay(BotConditionContext userDataCache, ReplyMessageService replyMessageService, UserInfo info) {
        this.userDataCache = userDataCache;
        this.replyMessageService = replyMessageService;
        this.info = info;
    }

    @Override
    public SendMessage handle(Message message) {
        Long chatId = message.getChatId();
        int userId = Math.toIntExact(message.getFrom().getId());
        List<String> subways = Arrays.asList(message.getText().split(","));
        List<Criterion> crit = info.getUserCriterionData(userId);
        crit.get(crit.size() - 1).setSubway(subways);
        userDataCache.setCurrentBotConditionForUserWithId(userId, BotCondition.GET_FLATS);

        return replyMessageService.getTextMessage(chatId, "Список станций введен\nНачать поиск?");
    }

    @Override
    public boolean canHandle(BotCondition botCondition) {
        return botCondition.equals(BotCondition.SET_SUBWAY);
    }
}
