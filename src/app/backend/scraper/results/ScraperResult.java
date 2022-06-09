package app.backend.scraper.results;

import app.backend.scraper.results.dynasty.Dynasty;
import app.backend.scraper.results.member.Member;

public final class ScraperResult implements Comparable<ScraperResult> {
    private final Member member;
    private final Dynasty dynasty;

    public ScraperResult(Member member, Dynasty dynasty) {
        this.member = member;
        this.dynasty = dynasty;
    }

    public Member getMember() {
        return this.member;
    }

    public Dynasty getDynasty() {
        return this.dynasty;
    }

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

    @Override
    public int compareTo(ScraperResult other) {
        return compareRankAndStatus(other);
    }
}