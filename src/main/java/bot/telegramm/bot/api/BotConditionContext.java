package bot.telegramm.bot.api;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BotConditionContext implements BotConditionUtil {
    private final Map<Integer, BotCondition> usersBotCondition = new HashMap<>();

    @Override
    public BotCondition getCurrentBotConditionForUserById(Integer userId) {
        return usersBotCondition.getOrDefault(userId, BotCondition.START);
    }

    @Override
    public void setCurrentBotConditionForUserWithId(Integer userId, BotCondition botCondition) {
        usersBotCondition.put(userId, botCondition);
    }

}
