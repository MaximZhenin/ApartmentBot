package bot.telegramm.bot.listcommands;

public enum Commands {
    START_FILLING("Начать задавать"),
    SET_PRICE("Задать цену"),
    SET_LOCATION("Задать Метро"),
    GET_APARTMENT("Поиск"),
    HELP("HELP"),
    START("START");

    private final String description;

    Commands(String description) {
        this.description = description;
    }


    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "/" + this.name();
    }
}
