package helper;

import java.util.List;

public class SearchResult {

    private List topHits;
    private int totalHitCount;
    private long searchDuration;
    private String originalQuery;
    private String suggestedQuery;
	
    public SearchResult(List topHits, int totalHitCount, long searchDuration,
			String originalQuery, String suggestedQuery) {
		super();
		this.topHits = topHits;
		this.totalHitCount = totalHitCount;
		this.searchDuration = searchDuration;
		this.originalQuery = originalQuery;
		this.suggestedQuery = suggestedQuery;
	}

	public SearchResult(List topHits, int totalHitCount, long searchDuration,
			String originalQuery) {
		super();
		this.topHits = topHits;
		this.totalHitCount = totalHitCount;
		this.searchDuration = searchDuration;
		this.originalQuery = originalQuery;
	}

	public List getTopHits() {
		return topHits;
	}

	public void setTopHits(List topHits) {
		this.topHits = topHits;
	}

	public int getTotalHitCount() {
		return totalHitCount;
	}

	public void setTotalHitCount(int totalHitCount) {
		this.totalHitCount = totalHitCount;
	}

	public long getSearchDuration() {
		return searchDuration;
	}

	public void setSearchDuration(long searchDuration) {
		this.searchDuration = searchDuration;
	}

	public String getOriginalQuery() {
		return originalQuery;
	}

	public void setOriginalQuery(String originalQuery) {
		this.originalQuery = originalQuery;
	}

	public String getSuggestedQuery() {
		return suggestedQuery;
	}

	public void setSuggestedQuery(String suggestedQuery) {
		this.suggestedQuery = suggestedQuery;
	}


}
