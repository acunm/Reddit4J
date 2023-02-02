package masecla.reddit4j.objects;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RedditImage extends RedditPost{

    private String id;
    private Boolean enabled;
    private List<ImageData> imageList;
    private Source source;

    private Object variants;

    @Getter
    @Setter
    public static class ImageData{
        private String url;
        private Integer width;
        private Integer height;
    }

    @Getter
    @Setter
    public static class Source{
        private String url;
        private Integer width;
        private Integer height;
    }
}
