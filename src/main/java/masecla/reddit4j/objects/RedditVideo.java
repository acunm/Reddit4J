package masecla.reddit4j.objects;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedditVideo extends RedditPost {
    private VideoDetails media;

    @Getter
    @Setter
    public static class VideoDetails {
        private RedditVideoDetails redditVideo;

        @Getter
        @Setter
        public static class RedditVideoDetails{
            private String isCompleted;
            private String fallback_url;
            private String scrubberMediaUrl;
            private String dashUrl;
            private String hlsUrl;
            private Integer bitrateKbps;
            private Integer height;
            private Integer width;
            private Integer duration;
            private Boolean isGif;
        }
    }
}
