package it.eforhum;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CatFactsData {
    @JsonProperty("current_page")
    public int currentPage;
    
    public List<CatFact> data;
    
    @JsonProperty("first_page_url")
    public String firstPageUrl;
    
    public int from;
    
    @JsonProperty("last_page")
    public int lastPage;
    
    @JsonProperty("last_page_url")
    public String lastPageUrl;
    
    public List<Link> links;
    
    @JsonProperty("next_page_url")
    public String nextPageUrl;
    
    public String path;
    
    @JsonProperty("per_page")
    public int perPage;
    
    @JsonProperty("prev_page_url")
    public String prevPageUrl;
    
    public int to;
    
    public int total;

    public static class Link {
        public String url;
        public String label;
        public boolean active;
    }
}
