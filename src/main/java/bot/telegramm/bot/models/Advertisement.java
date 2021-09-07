package bot.telegramm.bot.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Advertisement {
    @JsonProperty("Id")
    private long id;
    private String url;
    private int price;
    private String address;
    private String metro;
    @JsonProperty("rooms_count")
    private int roomsCount;
    private double area;
    private String images;
    private String description;
}
