package bot.telegramm.bot.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
public class Person {
    int userId;
    Long chatId;
    //String name;
    Criterion crit;

    public Person(int userId, Long chatId) {
        this.userId = userId;
        this.chatId = chatId;
        this.crit = new Criterion();
    }
}
