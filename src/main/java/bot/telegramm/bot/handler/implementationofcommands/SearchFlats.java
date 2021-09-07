package bot.telegramm.bot.handler.implementationofcommands;

import bot.telegramm.bot.api.BotCondition;
import bot.telegramm.bot.api.BotConditionContext;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import bot.telegramm.bot.handler.MessageHandler;
import bot.telegramm.bot.handler.annotation.BotCommand;
import bot.telegramm.bot.inmemory.UserInfo;
import bot.telegramm.bot.listcommands.Commands;
import bot.telegramm.bot.models.Criterion;
import bot.telegramm.bot.models.Person;
import bot.telegramm.bot.service.ReplyMessageService;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Arrays;


@BotCommand(command = Commands.GET_APARTMENT)
public class SearchFlats implements MessageHandler {
    private final BotConditionContext userDataCache;
    private final ReplyMessageService replyMessageService;
    private  final UserInfo info;
    @Value("http://localhost:8080/user/testperson")
    private String url;

    public SearchFlats(BotConditionContext userDataCache, ReplyMessageService replyMessageService, UserInfo info) {
        this.userDataCache = userDataCache;
        this.replyMessageService = replyMessageService;
        this.info = info;
    }

    @Override
    public SendMessage handle(Message message) {
        ObjectMapper objectMapper = new ObjectMapper();
        Long chatId = message.getChatId();
        int userId = Math.toIntExact(message.getFrom().getId());
        for (Criterion crit:info.getUserCriterionData(userId)) {
            try {
                System.out.printf(objectMapper.writeValueAsString(crit));
            } catch (JsonGenerationException e) {
                System.out.printf("Lolkek");;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        Person pers = new Person(userId, chatId);
        pers.setCrit(info.getLastCrit(userId));
        sendRequest(pers);
        userDataCache.setCurrentBotConditionForUserWithId(userId, BotCondition.SET_PRICE);
        return replyMessageService.getTextMessage(chatId, "Запрос Отправлен\nОжидайте ответа:");
    }

    @Override
    public boolean canHandle(BotCondition botCondition) {
        return botCondition.equals(BotCondition.GET_FLATS);
    }


    private void sendRequest(Person pers) {
        // HttpHeaders
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();

        // Data attached to the request.
        HttpEntity<Person> requestBody = new HttpEntity<>(pers, headers);

        // Send request with POST method.
        System.out.printf(url + '\n');
        Person e = restTemplate.postForObject(url, requestBody, Person.class);

        if (e != null) {
            System.out.println("Person saved");
        } else {
            System.out.println("Something error!");
        }

    }


}
