package bot.telegramm.bot.controller;

import bot.telegramm.bot.MyApartmentBot;
import bot.telegramm.bot.models.Person;
import bot.telegramm.bot.models.Wrapper;
import bot.telegramm.bot.service.ReplyMessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@RestController
@RequestMapping("/user")
public class CianController {
    private final MyApartmentBot telegramBot;
    private ReplyMessageService replyMessageService;

    public CianController(MyApartmentBot telegramBot, ReplyMessageService replyMessageService) {
        this.telegramBot = telegramBot;
        this.replyMessageService = replyMessageService;
    }

    @PostMapping("/advertisement")
    public void returnAdvertisement(@RequestBody Wrapper wrap) {
        System.out.printf("REST!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        telegramBot.brodCastMessage(wrap);
        return;
    }

    @PostMapping("/testperson")
    public void returnPerson(@RequestBody Person pers) throws JsonProcessingException {
        System.out.printf("TESST!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        //System.out.printf(String.valueOf(user.getChatId()));
        ObjectMapper objectMapper = new ObjectMapper();
        telegramBot.sendMessage(pers.getChatId(),objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(pers) );
        return;
    }
}
