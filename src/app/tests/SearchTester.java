package app.tests;

import java.util.ArrayList;

import app.backend.BackendManager;
import app.backend.controllers.SearchQueryController;
import app.backend.scraper.results.ScraperResult;
import app.backend.scraper.results.dynasty.Dynasty;
import app.backend.scraper.results.member.Member;
import app.backend.scraper.results.member.Relative;

public class SearchTester {
    private static final int TESTS_NUMBER = 2;

    /** 
     * @return int
     */
    public static int getTotalTests() {
        return TESTS_NUMBER;
    }
    
    /** 
     * @return int
     */
    public static int runTests() {
        
        int passedTests = 0;

        loadTestDynasty();

        System.out.print("Testing Member search... ");

        if (testMemberSearch()) {
            System.out.println("SUCCESS");

            passedTests++;
        } else {
            System.out.println("FAILED");
        }
        
        System.out.print("Testing Search suggestion... ");

        if (testSearchSuggestion()) {
            System.out.println("SUCCESS");

            passedTests++;
        } else {
            System.out.println("FAILED");
        }

        return passedTests;
    }
    
    /** 
     * @return Dynasty
     */
    private static void loadTestDynasty() {
        Dynasty dynasty = new Dynasty("Test Dynasty", "https://example.com");
        Member member1 = new Member("Romolo", "", false);
        Member member2 = new Member("Remo", "https://example.com", true);
        Member member3 = new Member("Francesco Totti", "", true);

        member1.addRelative(member2, Relative.PARENT);
        member2.addRelative(member3, Relative.CHILD);
        member3.addRelative(member1, Relative.SPOUSE);

        dynasty.addMember(member1);
        dynasty.addMember(member2);
        dynasty.addMember(member3);

        // load test dynasty
        BackendManager.getDynasties().add(dynasty);
    }

    private static boolean testMemberSearch(){

        ArrayList<ScraperResult> foundMembers = SearchQueryController.findMatchingMembersAsResults("Franc");

        String expectedName = "Francesco Totti";

        boolean hasBeenFound = false;
        for(ScraperResult result : foundMembers){
            String resultName = result.getMember().getName();
            if(expectedName.equals(resultName)){
                hasBeenFound = true;
            }
        }

        if(!hasBeenFound){
            return false;
        }

        foundMembers = SearchQueryController.findMatchingMembersAsResults("R");

        int foundExpecteds = 0;
        String expectedName1 = "Romolo";
        String expectedName2 = "Remo";

        for(ScraperResult result : foundMembers){
            String resultName = result.getMember().getName();
            if(expectedName1.equals(resultName) || expectedName2.equals(resultName)){
                foundExpecteds++;
            }
        }

        if(foundExpecteds != 2){
            return false;
        }

        return true;
    }

    private static boolean testSearchSuggestion(){

        String suggestion = SearchQueryController.findSuggestion("Fran");
        String expectedSuggestion = "Francesco Totti";

        // The suggestion shouild be equal
        if (!expectedSuggestion.equals(suggestion)){
            return false;
        }

        suggestion = SearchQueryController.findSuggestion("Rom");
        expectedSuggestion = "Romolo";

        // The suggestion should be equal
        if (!expectedSuggestion.equals(suggestion)){
            return false;
        }

        suggestion = SearchQueryController.findSuggestion("Marco Aurelio");
        expectedSuggestion = "Marco Aurelio";

        // The suggestion should NOT be equal
        if (expectedSuggestion.equals(suggestion)){
            return false;
        }

        return true;
    }
}
