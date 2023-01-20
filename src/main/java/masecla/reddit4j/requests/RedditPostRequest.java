package masecla.reddit4j.requests;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import masecla.reddit4j.client.Reddit4J;
import masecla.reddit4j.exceptions.AuthenticationException;
import masecla.reddit4j.objects.RedditData;
import masecla.reddit4j.objects.RedditListing;
import masecla.reddit4j.objects.RedditPost;
import masecla.reddit4j.objects.RedditThing;
import masecla.reddit4j.objects.subreddit.GalleryMetadata;
import org.jsoup.Connection;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RedditPostRequest extends AbstractRedditPostRequest<RedditPost, RedditPostRequest>{
    public RedditPostRequest(String subreddit, String id, Reddit4J client){
        super(subreddit, id, client, RedditPostRequest.class);
    }

    public RedditPost submit() throws AuthenticationException, IOException, InterruptedException {
        getClient().ensureConnection();
        Connection connection = createConnection();

        Connection.Response response = connection.execute();
        TypeToken<?> ttPost = TypeToken.getParameterized(RedditData.class, RedditPost.class);
        TypeToken<?> ttListing = TypeToken.getParameterized(RedditListing.class, ttPost.getType());
        TypeToken<?> ttData = TypeToken.getParameterized(RedditData.class, ttListing.getType());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(RedditPost.class, new GalleryMetadataDeserializer());

        Gson gson = gsonBuilder.create();

        JsonArray rootArray = JsonParser.parseString(response.body()).getAsJsonArray();

        JsonObject postData = rootArray.get(0).getAsJsonObject();

        RedditData<RedditListing<RedditData<RedditPost>>> postJson = gson.fromJson(postData, ttData.getType());

        RedditPost redditPost = postJson.getData()
                .getChildren()
                .get(0)
                .getData();
        redditPost.setClient(getClient());

        return redditPost;
    }

    private class GalleryMetadataDeserializer implements JsonDeserializer<RedditPost> {
        @Override
        public RedditPost deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
            final JsonObject jsonObject = json.getAsJsonObject();

            JsonObject mediaMetadata = jsonObject.getAsJsonObject("media_metadata");

            Set<Map.Entry<String, JsonElement>> keys =  mediaMetadata.entrySet();

            RedditPost post = new RedditPost();
            GalleryMetadata galleryMetadata = new GalleryMetadata();
            Gson gson = new Gson();

            for (Map.Entry<String, JsonElement> entry : keys) {
                JsonElement jsonElement  = entry.getValue();
                JsonArray p = jsonElement.getAsJsonObject().getAsJsonArray("p");
                Type pListType = new TypeToken<ArrayList<GalleryMetadata.GalleryMetadataItem>>(){}.getType();
                galleryMetadata.setGalleryMetadataItemList(gson.fromJson(p, pListType));
            }

            post.setGalleryMetadata(galleryMetadata);

            return post;
        }
    }

}
