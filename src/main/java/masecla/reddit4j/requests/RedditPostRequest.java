package masecla.reddit4j.requests;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import masecla.reddit4j.client.Reddit4J;
import masecla.reddit4j.deserializer.GalleryDataDeserializer;
import masecla.reddit4j.deserializer.GalleryMetadataDeserializer;
import masecla.reddit4j.deserializer.RedditImageDataDeserializer;
import masecla.reddit4j.deserializer.RedditVideoDeserializer;
import masecla.reddit4j.exceptions.AuthenticationException;
import masecla.reddit4j.objects.*;
import masecla.reddit4j.objects.subreddit.GalleryData;
import masecla.reddit4j.objects.subreddit.GalleryMetadata;
import org.jsoup.Connection;

import java.io.IOException;

public class RedditPostRequest extends AbstractRedditPostRequest<RedditPost, RedditPostRequest>{

    private final String postType;
    public RedditPostRequest(String subreddit, String id, Reddit4J client){
        this(subreddit, id, client, null);
    }

    public RedditPostRequest(String subreddit, String id, Reddit4J client, String postType){
        super(subreddit, id, client, RedditPostRequest.class);
        this.postType = postType;
    }

    public RedditPost submit() throws AuthenticationException, IOException, InterruptedException {
        getClient().ensureConnection();
        Connection connection = createConnection();

        Connection.Response response = connection.execute();
        TypeToken<?> ttPost = TypeToken.getParameterized(RedditData.class, RedditPost.class);
        TypeToken<?> ttListing = TypeToken.getParameterized(RedditListing.class, ttPost.getType());
        TypeToken<?> ttData = TypeToken.getParameterized(RedditData.class, ttListing.getType());
        Gson gson = getGson();

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

    private Gson getGson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        if("gallery".equals(postType)){
            gsonBuilder.registerTypeAdapter(GalleryMetadata.class, new GalleryMetadataDeserializer());
            gsonBuilder.registerTypeAdapter(GalleryData.class, new GalleryDataDeserializer());
        }
        if("image".equals(postType)){
            gsonBuilder.registerTypeAdapter(RedditImage.class, new RedditImageDataDeserializer());
        }
        if("video".equals(postType)){
            gsonBuilder.registerTypeAdapter(RedditVideo.class, new RedditVideoDeserializer());
        }

        return gsonBuilder.create();
    }
}
