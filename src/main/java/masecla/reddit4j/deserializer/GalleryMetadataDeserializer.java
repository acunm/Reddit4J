package masecla.reddit4j.deserializer;

import com.google.gson.*;
import masecla.reddit4j.objects.subreddit.GalleryMetadata;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GalleryMetadataDeserializer implements JsonDeserializer<GalleryMetadata> {
    @Override
    public GalleryMetadata deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        JsonObject mediaMetadata = json.getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> keys =  mediaMetadata.entrySet();

        GalleryMetadata galleryMetadata = new GalleryMetadata();
        List<GalleryMetadata.GalleryMetadataItem> itemList = new ArrayList<>();

        for (Map.Entry<String, JsonElement> entry : keys) {
            GalleryMetadata.GalleryMetadataItem item = new GalleryMetadata.GalleryMetadataItem();
            JsonElement jsonElement  = entry.getValue();
            JsonArray p = jsonElement.getAsJsonObject().getAsJsonArray("p");
            JsonObject s = jsonElement.getAsJsonObject().getAsJsonObject("s");

            item.setE(jsonElement.getAsJsonObject().get("e").getAsString());
            item.setStatus(jsonElement.getAsJsonObject().get("status").getAsString());
            item.setId(jsonElement.getAsJsonObject().get("id").getAsString());
            item.setMimeType(jsonElement.getAsJsonObject().get("m").getAsString());

            ArrayList<GalleryMetadata.GalleryMetadataItem.ImageData> pictureList = new ArrayList<>();
            for(JsonElement element : p){
                JsonObject jsonObject = element.getAsJsonObject();
                GalleryMetadata.GalleryMetadataItem.ImageData imageData = new GalleryMetadata.GalleryMetadataItem.ImageData();
                imageData.setUrl(jsonObject.get("u").getAsString().replaceAll("&amp;", "&"));
                imageData.setX(jsonObject.get("x").getAsInt());
                imageData.setY(jsonObject.get("y").getAsInt());
                pictureList.add(imageData);
            }

            item.setPictures(pictureList);

            GalleryMetadata.GalleryMetadataItem.ImageSomething sObject = new GalleryMetadata.GalleryMetadataItem.ImageSomething();
            sObject.setUrl(s.get("u").getAsString().replaceAll("&amp;", "&"));
            sObject.setY(s.get("y").getAsInt());
            sObject.setX(s.get("x").getAsInt());

            item.setS(sObject);

            itemList.add(item);
        }
        galleryMetadata.setGalleryMetadataItemList(itemList);
        return galleryMetadata;
    }
}
