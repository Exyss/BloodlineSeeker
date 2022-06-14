package app.tests;

import java.io.File;
import java.io.IOException;

import app.backend.scraper.results.dynasty.Dynasty;
import app.backend.scraper.results.member.Member;
import app.backend.scraper.results.member.Relative;

/**
 * This DynystyTester is used to test the Dynasty class.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class DynastyTester {
    private static final int TESTS_NUMBER = 2;

    private static final String JSON_FILE = "test.json";

    
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
        Dynasty createdDynasty = createTestDynasty();

        System.out.print("Testing Dynasty Serialization... ");

        int passedTests = 0;

        if (testDynastySerialization(createdDynasty)) {
            System.out.println("SUCCESS");

            passedTests++;
        } else {
            System.out.println("FAILED");
        }

        System.out.print("Testing Dynasty De-Serialization... ");

        if (testDynastyDeserialization(createdDynasty)) {
            System.out.println("SUCCESS");

            passedTests++;
        } else {
            System.out.println("FAILED");
        }

        File testFile = new File(JSON_FILE);

        if (testFile.isFile()) {
            testFile.delete();
        }

        return passedTests;
    }

    
    /** 
     * @return Dynasty
     */
    private static Dynasty createTestDynasty() {
        Dynasty dynasty = new Dynasty("Test Dynasty", "https://example.com");
        Member member1 = new Member("Member 1", "", false);
        Member member2 = new Member("Member 2", "https://example.com", true);
        Member member3 = new Member("Member 3", "", true);

        member1.addRelative(member2, Relative.PARENT);
        member2.addRelative(member3, Relative.CHILD);
        member3.addRelative(member1, Relative.SPOUSE);

        dynasty.addMember(member1);
        dynasty.addMember(member2);
        dynasty.addMember(member3);

        return dynasty;
    }

    
    /** 
     * @param createdDynasty
     * @return boolean
     */
    private static boolean testDynastySerialization(Dynasty createdDynasty) {
        try {
            createdDynasty.toJSONFile(JSON_FILE);
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }

        return true;
    }

    
    /** 
     * @param createdDynasty
     * @return boolean
     */
    private static boolean testDynastyDeserialization(Dynasty createdDynasty) {
        Dynasty loadedDynasty;

        try {
            loadedDynasty = Dynasty.fromJSONFile(JSON_FILE);
        } catch (IOException e) {
            System.err.println(e);
            return false;
        }

        String loadedString = loadedDynasty.toJSONObject().toString();
        String expectedString = createdDynasty.toJSONObject().toString();

        return expectedString.equals(loadedString);
    }
}