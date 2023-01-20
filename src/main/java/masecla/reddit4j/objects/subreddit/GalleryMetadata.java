package masecla.reddit4j.objects.subreddit;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

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
        private String e;
        private String m;
        private List<ImageData> p;
        private List<ImageSomething> s;


        @Getter
        @Setter
        public static class ImageData{
            private Integer x;
            private Integer y;
            private String u;
        }

        @Getter
        @Setter
        public static class ImageSomething{
            private Integer x;
            private Integer y;
            private String u;
        }
    }
}
