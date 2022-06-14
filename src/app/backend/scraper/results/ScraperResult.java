package app.backend.scraper.results;

import app.backend.scraper.results.dynasty.Dynasty;
import app.backend.scraper.results.member.Member;

/**
 * This ScraperResult rapresent a touple (Dynasty, Member) and implements Comparable in order to compare each instance of this class.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
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
     * Compare the given results choosing the the one which has more relations.
     * @param other the ScraperResult that should be compared.
     * @return int the comparation rank.
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
     * Compare the given results choosing the the one which has highest status and relations.
     * @param other the ScraperResult that should be compared
     * @return int the comparation rank.
     */
    private int compareRankAndStatus(ScraperResult other) {
        boolean thisStatus = this.member.isEmperor();
        boolean otherStatus  = other.member.isEmperor();

        // Emperors have the highest priority - The number of connections is the secondary priority
        if (!(thisStatus || otherStatus) || (thisStatus && otherStatus)) {
            return compareRank(other);
        }else if (thisStatus) {
            return -1;
        }else {
            return 1;
        }
    }

    
    /** 
     * @param other the ScraperResult that should be compared.
     * @return int the comparation rank.
     */
    @Override
    public int compareTo(ScraperResult other) {
        return compareRankAndStatus(other);
    }
}