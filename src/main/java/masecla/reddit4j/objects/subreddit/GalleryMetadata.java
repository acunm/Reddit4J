package masecla.reddit4j.objects.subreddit;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GalleryMetadata {
    private List<GalleryMetadataItem> galleryMetadataItemList;

    @Override
    public String toString() {
        return galleryMetadataItemList == null ? "list is null" : galleryMetadataItemList.toString();
    }

    @Getter
    @Setter
    public static class GalleryMetadataItem{
        private String id;
        private String status;

        @SerializedName("e")
        private String e;
        @SerializedName("m")
        private String mimeType;
        @SerializedName("p")
        private List<ImageData> pictures;
        private ImageSomething s;


        @Getter
        @Setter
        public static class ImageData{
            private Integer x;
            private Integer y;
            @SerializedName("u")
            private String url;
        }

        @Getter
        @Setter
        public static class ImageSomething{
            private Integer x;
            private Integer y;
            @SerializedName("u")
            private String url;
        }
    }
}
