package masecla.reddit4j.objects.subreddit;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GalleryData {
    private List<GalleryDataItem> items;


    @Getter
    @Setter
    public static class GalleryDataItem{
        @SerializedName("media_id")
        private String mediaId;
        private String id;
    }
}
