package masecla.reddit4j.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import masecla.reddit4j.objects.RedditVideo;

import java.lang.reflect.Type;

public class RedditVideoSerializer implements JsonDeserializer<RedditVideo> {
    @Override
    public RedditVideo deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return null;
    }
}
