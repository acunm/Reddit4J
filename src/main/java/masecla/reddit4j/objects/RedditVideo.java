package masecla.reddit4j.objects;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedditVideo {

    private String isCompleted;
    private String fallbackUrl;
    private String scrubberMediaUrl;
    private String dashUrl;
    private String hlsUrl;
    private Integer bitrateKbps;
    private Integer height;
    private Integer width;
    private Integer duration;
    private Boolean isGif;
}
