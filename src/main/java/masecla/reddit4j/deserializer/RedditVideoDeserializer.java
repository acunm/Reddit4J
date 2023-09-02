package masecla.reddit4j.deserializer;

import com.google.gson.*;
import masecla.reddit4j.objects.RedditVideo;

import java.lang.reflect.Type;

public class RedditVideoDeserializer implements JsonDeserializer<RedditVideo> {

    @Override
    public RedditVideo deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject preview = jsonElement.getAsJsonObject();

        RedditVideo redditVideo = new RedditVideo();
        redditVideo.setMedia(null);

        return redditVideo;
    }
}
