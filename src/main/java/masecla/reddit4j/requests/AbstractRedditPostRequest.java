package masecla.reddit4j.requests;

import masecla.reddit4j.client.Reddit4J;
import masecla.reddit4j.objects.RedditThing;
import org.jsoup.Connection;

public class AbstractRedditPostRequest<T extends RedditThing, S extends AbstractRedditPostRequest<?, ?>> {
    private String id;
    private String subreddit;
    private Reddit4J client;

//    private final Class<T> clazz;
    private final S self;
    private int count = 0;
    private int limit = 25;
    private boolean show = false;
    private String before = null;
    private String after = null;
    public AbstractRedditPostRequest(String subreddit, String id, Reddit4J client, Class<S> clazz){
        this.subreddit = subreddit;
        this.id = id + ".json";
        this.client = client;
        this.self = clazz.cast(this);
    }

    public S after(T after) {
        this.after = (after == null) ? null : after.getId();
        return self;
    }

    public S after(String after) {
        this.after = after;
        return self;
    }

    public S before(T before) {
        this.before = (before == null) ? null : before.getId();
        return self;
    }

    public S before(String before) {
        this.before = before;
        return self;
    }

    public S count(int count) {
        this.count = count;
        return self;
    }

    public S limit(int limit) {
        if (limit < 25 || limit > 100)
            throw new IllegalArgumentException("Limit must be between 25 and 100");
        this.limit = limit;
        return self;
    }

    public S show(boolean show) {
        this.show = show;
        return self;
    }

    protected Connection createConnection() {
        Connection conn = client.useEndpoint(subreddit, id);

        if (after != null)
            conn.data("after", after);
        if (before != null)
            conn.data("before", before);
        if (count != 0)
            conn.data("count", count + "");

        conn.data("limit", limit + "");

        if (show)
            conn.data("show", "all");

        return conn;
    }

    public Reddit4J getClient() {
        return client;
    }
}
