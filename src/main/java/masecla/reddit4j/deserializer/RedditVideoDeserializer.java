package masecla.reddit4j.deserializer;

import com.google.gson.*;
import masecla.reddit4j.objects.RedditPost;
import masecla.reddit4j.objects.RedditVideo;

import java.lang.reflect.Type;

public class RedditVideoDeserializer implements JsonDeserializer<RedditPost> {

    @Override
    public RedditPost deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        RedditPost redditPost = gson.fromJson(jsonElement, RedditPost.class);

        JsonObject redditVideoJson = jsonElement.getAsJsonObject().getAsJsonObject("media").getAsJsonObject("reddit_video");
        redditPost.setMedia(gson.fromJson(redditVideoJson, RedditVideo.class));

        return redditPost;
    }
}
