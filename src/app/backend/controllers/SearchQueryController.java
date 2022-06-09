package app.backend.controllers;

import java.util.ArrayList;
import java.util.Collections;

import app.backend.BackendManager;
import app.backend.scraper.results.ScraperResult;
import app.backend.scraper.results.dynasty.Dynasty;
import app.backend.scraper.results.member.Member;

public class SearchQueryController {
    public static ArrayList<ScraperResult> findMatchingDynastyAsResults(String query) {
        ArrayList<ScraperResult> results = new ArrayList<ScraperResult>();
        ArrayList<Dynasty> dynasties = BackendManager.getDynasties();
        
        query = filterQuery(query);
        
        for (Dynasty dynasty : dynasties) {
            String dynastyName = dynasty.getName().replaceAll("[^a-zA-Z]"," ").toLowerCase();
            
            if (dynastyName.contains(query)) {
                results = dynasty.getMembersResults();

                break;
            }
        }

        return results;
    }

    public static ArrayList<ScraperResult> findMatchingMembersAsResults(String query) {
        ArrayList<ScraperResult> results = new ArrayList<ScraperResult>();

        query = filterQuery(query);

        for (Dynasty dynasty : BackendManager.getDynasties()) {
            for (Member member : dynasty.getMembers()) {
                if (member.getName().toLowerCase().contains(query)) {
                    ScraperResult result = new ScraperResult(member, dynasty);

                    results.add(result);
                }
            }
        }

        return results;
    }
    
    private static ArrayList<String> findMatchingMemberAsNames(String query) {
        ArrayList<String> matchingMembersNames = new ArrayList<String>();

        query = filterQuery(query);
        
        for (Dynasty dynasty : BackendManager.getDynasties()) {
            for (Member member : dynasty.getMembers()) {
                if (member.getName().toLowerCase().contains(query)) {
                    String matchFullname = member.getName();

                    matchingMembersNames.add(matchFullname);
                }
            }
        }

        return matchingMembersNames;
    }

    public static String findSuggestion(String query) {
        ArrayList<String> substrings = getSortedSubstrings(query);

        ArrayList<String> matchingMemberNames;
        
        String matchingMemberName = null;

        for (String substring : substrings) {
            matchingMemberNames = findMatchingMemberAsNames(substring);

            if (matchingMemberNames.size() != 0) {
                matchingMemberName = findShortestSuggestion(matchingMemberNames);

                break;
            }
        }

        return matchingMemberName;
    }

    private static String findShortestSuggestion(ArrayList<String> possibleSuggestions) {
        String shortestName = possibleSuggestions.get(0);

        int shortestNameLength = shortestName.length();

        for (String possibleSuggestion : possibleSuggestions) {
            int possibleSuggestionLength = possibleSuggestion.length();  

            if (shortestNameLength > possibleSuggestionLength) {
                shortestName = possibleSuggestion;

                shortestNameLength = possibleSuggestionLength;
            }
        }

        return shortestName;
    }

    private static ArrayList<String> getSortedSubstrings(String query) {
        ArrayList<String> substrings = new ArrayList<String>();

        int queryLength = query.length();

        int substringLength = queryLength;

        while (substringLength != 0) {
            int startingIndex = 0;

            int numberOfSubstrings = queryLength - substringLength + 1;

            for (int i = 0; i < numberOfSubstrings; i++) {
                String substring = query.substring(startingIndex, startingIndex + substringLength);

                substrings.add(substring);

                startingIndex++;
            }

            substringLength--;
        }

        return substrings;
    }

    public static void sortResults(ArrayList<ScraperResult> results) {
        Collections.sort(results);
    }
    
    private static String filterQuery(String query) {
        return query.replaceAll("\\s+", " ").trim().toLowerCase();
    }
}