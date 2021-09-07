package bot.telegramm.bot.api;

public interface BotConditionUtil {
    BotCondition getCurrentBotConditionForUserById(Integer userId);

    void setCurrentBotConditionForUserWithId(Integer userId, BotCondition botCondition);
}
