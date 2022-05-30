package Models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@Data
@AllArgsConstructor
public class Tweet {
    private String createdAt;
    private String autorID;
    private String ID;
    private String text;
    private String sentiment;

}
