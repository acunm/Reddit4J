package masecla.reddit4j.objects;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RedditListing<T> {
    private String before;
    private String after;
    private String modhash;
    private int dist;
    private List<T> children;
    private String geo_filter;
}
