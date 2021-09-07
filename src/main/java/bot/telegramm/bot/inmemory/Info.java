package bot.telegramm.bot.inmemory;

import bot.telegramm.bot.models.Criterion;

import java.util.List;

public interface Info {
    List<Criterion> getUserCriterionData(int userId);
    void saveUserCriterionData(int userId, Criterion userCriterionData);
}
