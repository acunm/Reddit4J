package masecla.reddit4j.deserializer;

import com.google.gson.*;
import masecla.reddit4j.objects.subreddit.GalleryData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GalleryDataDeserializer implements JsonDeserializer<GalleryData> {
    @Override
    public GalleryData deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();

        GalleryData galleryData = new GalleryData();
        List<GalleryData.GalleryDataItem> itemList = new ArrayList<>();

        for(JsonElement s : object.get("items").getAsJsonArray()){
            GalleryData.GalleryDataItem item = new GalleryData.GalleryDataItem();
            item.setId(s.getAsJsonObject().get("id").getAsString());
            item.setMediaId(s.getAsJsonObject().get("media_id").getAsString());
            itemList.add(item);
        }
        galleryData.setItems(itemList);
        return galleryData;
    }
}