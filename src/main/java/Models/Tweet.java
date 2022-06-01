package Models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tweet {
    private String createdAt;
    private String autorID;
    private String ID;
    private String text;
    private String sentiment;

}
