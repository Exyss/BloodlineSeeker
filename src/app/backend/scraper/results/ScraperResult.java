package app.backend.scraper.results;

import app.backend.scraper.results.dynasty.Dynasty;
import app.backend.scraper.results.member.Member;

public final class ScraperResult implements Comparable<ScraperResult> {
	
	/**
	 * A member object.
	 */
    private final Member member;
    
    /**
     * A dynasty object.
     */
    private final Dynasty dynasty;

    /**
     * Creates a ScraperResults with member and dynasty.
     * @param member The new member value.
     * @param dynasty The new dynasty value.
     */
    public ScraperResult(Member member, Dynasty dynasty) {
        this.member = member;
        this.dynasty = dynasty;
    }

    
    /** 
     * @return The member of the ScraperResult.
     */
    public Member getMember() {
        return this.member;
    }

    
    /** 
     * @return The dynasty of the ScraperResult.
     */
    public Dynasty getDynasty() {
        return this.dynasty;
    }

    
    /** 
     * @param other
     * @return int
     */
    private int compareRank(ScraperResult other) {
        int thisRank = this.member.getAllRelatives().size();
        int otherRank = other.member.getAllRelatives().size();

        int result = thisRank - otherRank;

        if (result > 0) {
            return -1;
        } else if (result < 0) {
            return 1;
        } else {
            return result;
        }
    }

    
    /** 
     * @param other
     * @return int
     */
    private int compareRankAndStatus(ScraperResult other) {
        boolean thisStatus = this.member.isEmperor();
        boolean otherStatus  = other.member.isEmperor();
        if (!(thisStatus || otherStatus) || (thisStatus && otherStatus)) {
            return compareRank(other);
        }else if (thisStatus) {
            return -1;
        }else {
            return 1;
        }
    }

    
    /** 
     * @param other
     * @return int
     */
    @Override
    public int compareTo(ScraperResult other) {
        return compareRankAndStatus(other);
    }
}