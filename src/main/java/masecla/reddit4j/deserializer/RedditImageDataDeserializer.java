package masecla.reddit4j.deserializer;

import com.google.gson.*;
import masecla.reddit4j.objects.RedditImage;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RedditImageDataDeserializer implements JsonDeserializer<RedditImage> {
    @Override
    public RedditImage deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject preview = jsonElement.getAsJsonObject(); // name in the original json

        JsonArray images = preview.get("images").getAsJsonArray();
        JsonObject first = images.get(0).getAsJsonObject();
        JsonArray resolutions = first.get("resolutions").getAsJsonArray();

        RedditImage image = new RedditImage();
        RedditImage.Source source = new RedditImage.Source();
        List<RedditImage.ImageData> imageDataList = new ArrayList<>();

        JsonObject sourceJson = first.get("source").getAsJsonObject();
        source.setUrl(sourceJson.get("url").getAsString().replaceAll("&amp;", "&"));
        source.setWidth(sourceJson.get("width").getAsInt());
        source.setHeight(sourceJson.get("height").getAsInt());

        for(JsonElement element : resolutions) {
            JsonObject elementJson = element.getAsJsonObject();
            RedditImage.ImageData imageData = new RedditImage.ImageData();
            imageData.setUrl(elementJson.get("url").getAsString().replaceAll("&amp;", "&"));
            imageData.setWidth(elementJson.get("width").getAsInt());
            imageData.setHeight(elementJson.get("height").getAsInt());
            imageDataList.add(imageData);
        }

        image.setSource(source);
        image.setId(first.get("id").getAsString());
        image.setImageList(imageDataList);
        image.setEnabled(preview.get("enabled").getAsBoolean());
        return image;
    }
}
