package masecla.reddit4j.requests;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import masecla.reddit4j.client.Reddit4J;
import masecla.reddit4j.exceptions.AuthenticationException;
import masecla.reddit4j.objects.RedditData;
import masecla.reddit4j.objects.RedditListing;
import masecla.reddit4j.objects.RedditPost;
import masecla.reddit4j.objects.subreddit.GalleryData;
import masecla.reddit4j.objects.subreddit.GalleryMetadata;
import org.jsoup.Connection;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        gsonBuilder.registerTypeAdapter(GalleryMetadata.class, new GalleryMetadataDeserializer());
        gsonBuilder.registerTypeAdapter(GalleryData.class, new GalleryDataDeserializer());

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

    private static class GalleryMetadataDeserializer implements JsonDeserializer<GalleryMetadata> {
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

    private static class GalleryDataDeserializer implements JsonDeserializer<GalleryData>{
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

}
