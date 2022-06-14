package app.backend.controllers;

import java.util.ArrayList;
import java.util.Collections;

import app.backend.BackendManager;
import app.backend.scraper.results.ScraperResult;
import app.backend.scraper.results.dynasty.Dynasty;
import app.backend.scraper.results.member.Member;

/**
 * This SearchQueryController manages the dynasty/member search by the use of a query.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class SearchQueryController {
    
    /** 
     * Searches the queried dynasty and returns it's members as ScraperResults.
     * @param query the dynasty to search.
     * @return the ArrayList<ScraperResult> containing the results.
     */
    public static ArrayList<ScraperResult> findMatchingDynastyAsResults(String query) {
        ArrayList<ScraperResult> results = new ArrayList<ScraperResult>();

        // Get currently loaded dynasties
        ArrayList<Dynasty> dynasties = BackendManager.getDynasties();
        
        query = query.toLowerCase();

        for (Dynasty dynasty : dynasties) {
            // Filter and lowercase dynasty name
            String dynastyName = dynasty.getName().replaceAll("[^a-zA-Z]"," ").toLowerCase();
            
            if (dynastyName.contains(query)) {
                // Get every member in the found dynasty
                results = dynasty.getMembersResults();
                break;
            }
        }

        return results;
    }

    
    /** 
     * Searches the queried members and returns them as ScraperResults.
     * @param query the member to search.
     * @return the ArrayList<ScraperResult> containing the results.
     */
    public static ArrayList<ScraperResult> findMatchingMembersAsResults(String query) {
        ArrayList<ScraperResult> results = new ArrayList<ScraperResult>();

        // Get currently loaded dynasties
        ArrayList<Dynasty> dynasties = BackendManager.getDynasties();

        query = query.toLowerCase();

        for (Dynasty dynasty : dynasties) {
            for (Member member : dynasty.getMembers()) {

                // Add each matching member as ScraperResult
                if (member.getName().toLowerCase().contains(query)) {
                    ScraperResult result = new ScraperResult(member, dynasty);

                    results.add(result);
                }
            }
        }

        return results;
    }
    
    
    /** 
      * Searches the queried members and returns their names as Strings.
     * @param query the member to search.
     * @return the ArrayList<String> containing the results.
     */
    private static ArrayList<String> findMatchingMemberAsNames(String query) {
        ArrayList<String> matchingMembersNames = new ArrayList<String>();

        query = query.toLowerCase();
        
        for (Dynasty dynasty : BackendManager.getDynasties()) {
            for (Member member : dynasty.getMembers()) {

                // Add each matching member's name
                if (member.getName().toLowerCase().contains(query)) {
                    String matchFullname = member.getName();

                    matchingMembersNames.add(matchFullname);
                }
            }
        }

        return matchingMembersNames;
    }

    
    /** 
     * Find a suggestion if the search doesn't give any result.
     * @param query the member to search.
     * @return the String containing the suggestion.
     */
    public static String findSuggestion(String query) {

        //Get sorted queries
        ArrayList<String> substrings = getSortedSubstrings(query);

        ArrayList<String> matchingMemberNames;
        
        String matchingMemberName = null;

        for (String substring : substrings) {

            // Find matching members
            matchingMemberNames = findMatchingMemberAsNames(substring);
            
            // Find a suggestion in case there are no matching results
            if (matchingMemberNames.size() != 0) {
                matchingMemberName = findShortestSuggestion(matchingMemberNames);

                break;
            }
        }

        return matchingMemberName;
    }

    
    /** 
     * Finds the shortest suggestion from the given as parameters.
     * @param possibleSuggestions an ArrayList of tstring containings the possible suggestion.
     * @return the shortest String.
     */
    private static String findShortestSuggestion(ArrayList<String> possibleSuggestions) {
        String shortestName = possibleSuggestions.get(0);

        int shortestNameLength = shortestName.length();

        for (String possibleSuggestion : possibleSuggestions) {
            int possibleSuggestionLength = possibleSuggestion.length();  
            
            // Always keep the shortest suggestion
            if (shortestNameLength > possibleSuggestionLength) {
                shortestName = possibleSuggestion;

                shortestNameLength = possibleSuggestionLength;
            }
        }

        return shortestName;
    }

    
    /** 
     * Sorts the substring of the parameter by the shortest to the largest.
     * @param query the member to search.
     * @return the sorted ArrayList<String> containing the substrings.
     */
    private static ArrayList<String> getSortedSubstrings(String query) {
        ArrayList<String> substrings = new ArrayList<String>();

        int queryLength = query.length();
        int minSubstringLength = queryLength * 2 / 5;
        int substringLength = queryLength;

        while (substringLength != minSubstringLength) {
            int startingIndex = 0;

            int numberOfSubstrings = queryLength - substringLength + 1;

            // Get every possible substring by increasing the end index on each iteration
            for (int i = 0; i < numberOfSubstrings; i++) {
                String substring = query.substring(startingIndex, startingIndex + substringLength);

                substrings.add(substring);

                startingIndex++;
            }

            substringLength--;
        }

        return substrings;
    }

    
    /** 
     * Sorts a given ArrayList by the ScrapingResult algorithm.
     * @param results the ArrayList to be sorted.
     */
    public static void sortResults(ArrayList<ScraperResult> results) {
        Collections.sort(results);
    }
    
}