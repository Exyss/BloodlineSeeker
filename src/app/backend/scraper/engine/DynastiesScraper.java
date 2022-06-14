package app.backend.scraper.engine;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import app.backend.scraper.results.dynasty.Dynasty;
import app.backend.scraper.results.member.Member;
import app.backend.scraper.results.member.Relative;

/**
 * This DynastiesScraper extends WikipediaScraper in order to get the specific dynasties information from the wikipedia page.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class DynastiesScraper extends WikipediaScraper {

    /**
     * The domain of the main emperor page.
     */
    private static final String MAIN_EMPEROR_PAGE = WIKI_DOMAIN + "/wiki/Imperatori_romani";

    /**
     * The relative fields.
     */
    private static final String[] RELATIVE_FIELDS = {"Consort", "Madre", "Padre", "Coniug", "Figli"};

    /**
     * The String used as filter for the valid names.
     */
    private static final String VALID_NAME_FILTER = "(^Da [\\w\\s]+:)|( e )|Adottivi|equestre|due figli|presunto";

    /**
     * The String used as filter for the trailing invalid chars to be removed.
     */
    private static final String TRAILING_INVALID_CHARS = "[^a-zA-Z\']+$";

    /**
     * The String used as filter for the trailing invalid chars to be removed.
     */
    private static final String TRAILING_SINGLE_CHAR = "\\s[a-zA-Z]?$";

    /**
     * The String used as filter for the invalid chars to be removed.
     */
    private static final String INVALID_CHARS = "\"";

    
    /** 
     * Creates the ArrayList<Dynasty> containing all the scraped dinasties.
     * @return  the ArrayList<Dynasty>.
     * @throws FileNotFoundException in case the scraping doesn't work.
     */
    public ArrayList<Dynasty> scrapeDynasties() throws FileNotFoundException {

        HashMap<String, Member> existingMembers = new HashMap<String, Member>();

        ArrayList<Dynasty> dynasties = new ArrayList<Dynasty>();

        // Retrieve the list of emperor dynasties
        HashMap<Dynasty, HashMap<String, String>> emperorDynasties = this.getEmperorDynasties();
        
        // For each found dynasty
        for (Dynasty dynasty : emperorDynasties.keySet()) {

            // Add dynasty reference to list of scraped dynasties
            dynasties.add(dynasty);
            
            HashMap<String, String> emperors = emperorDynasties.get(dynasty);
            
            // For each emperor in the found dynasty
            for (String emperorLink : emperors.keySet()) {
                String emperorName = emperors.get(emperorLink);

                // Create the emperor
                createDynastyMember(existingMembers, dynasty, emperorName, emperorLink, true);
            }
        }
        
        return dynasties;
    }

    
    /**
     * Retrieves the list of emperor dynasties.
     * @return the HashMap<Dynasty, HashMap<String, String>> conyaining the emperor dinasties list.
     * @throws FileNotFoundException in case the scraping doesn't work.
     */
    private HashMap<Dynasty, HashMap<String, String>> getEmperorDynasties() throws FileNotFoundException {
        this.setStatus("Initializing Emperors links scraping...");
        this.setStatus("");

        HashMap<Dynasty, HashMap<String, String>> dynasties = new HashMap<Dynasty, HashMap<String, String>>();
        
        // Retrieve the main page containing the list of emperor dynasties
        String htmlEmperorsPage = this.getPageHTML(MAIN_EMPEROR_PAGE);

        // Get the main div containing all the dynasty-related tables
        Element tablesDiv = Jsoup.parse(htmlEmperorsPage).getElementsByClass("mw-parser-output").get(0);
        
        Elements contents = tablesDiv.children();
        
        // Check every child found in the div
        for (int i = 0; i < contents.size() - 1; i++) {
            Element elem = contents.get(i);

            // If the child is a valid emperor table
            if (isValidTitle(elem)) {
                Element emperorsTable = contents.get(i + 1);

                if (emperorsTable.className().contains("wikitable")) {

                    // Get the dynasty name and link
                    Element dynastyAnchor = elem.getElementsByTag("a").get(0);
                    String dynastyName = DynastiesScraper.parseParenthesis(dynastyAnchor.attr("title"));
                    String dynastyLink = WIKI_DOMAIN + dynastyAnchor.attr("href");

                    // Create the dynasty object
                    Dynasty dynasty = new Dynasty(dynastyName, dynastyLink);

                    this.setStatus("Found dynasty: " + dynastyLink);

                    // Parse the emperors table
                    HashMap<String, String> emperors = this.parseEmperorsTable(emperorsTable);
                    
                    dynasties.put(dynasty, emperors);
                }
            }
        }

        return dynasties;
    }
    
    
    /** 
     * Parses the emperors table and creates an HashMap made by name of the emperor and his wikipedia web page.
     * @param wikipediaTable the emperors table.
     * @return the new HashMap<String, String>.
     */
    private HashMap<String, String> parseEmperorsTable(Element wikipediaTable) {
        // Get table rows
        Elements emperorTable = wikipediaTable.getElementsByTag("tbody").get(0).getElementsByTag("tr");
        
        // Remove table headers
        emperorTable.remove(0);
        emperorTable.remove(0);
        
        HashMap<String, String> emperors = new HashMap<String, String>();
        
        for (Element emperor : emperorTable) {
            // Get all emperor datafields
            Elements empDataFields = emperor.getElementsByTag("td");
            
            // If there are no data fields
            if (empDataFields.size() == 0) {
                continue;
            }
            
            for (Element emperorData : empDataFields.get(1).getElementsByTag("b")) {
                // use Title attribute as name and HREF as page link
                Element emperorAnchor = emperorData.getElementsByTag("a").get(0);
                String emperorName = parseParenthesis(emperorAnchor.attr("title"));
                String emperorLink = WIKI_DOMAIN + emperorAnchor.attr("href");
                
                this.setStatus("Found emperor: "+ emperorLink);

                emperors.put(emperorLink, emperorName);
            }
        }
        
        return emperors;
    }
    
    
    /** 
     * Creates a dynasty member with all his connected relatives.
     * @param existingMembers the Hashmap which contains the already created members.
     * @param dynasty the dynasty of the member.
     * @param name the name of the member.
     * @param link the wikipedia web page link of the member.
     * @param isEmperor true if the member was an emperor, false if he wasn't.
     * @return the new Member.
     * @throws FileNotFoundException in case the scraping doesn't work.
     */
    private Member createDynastyMember(
            HashMap<String, Member> existingMembers, Dynasty dynasty,
            String name, String link, boolean isEmperor
        ) throws FileNotFoundException {

        //Avoid different links pointing to the same wikipage
        String hashmapLink = link.toLowerCase();

        Member member;

        //get existing member
        if (existingMembers.containsKey(hashmapLink)) {
            member = (Member) existingMembers.get(hashmapLink);

            if (!member.isEmperor() && isEmperor) {
                member.setEmperorStatus(isEmperor);
            }
        //create new member
        } else {
            member = createNewMember(existingMembers, dynasty, name, link, isEmperor);
        }

        dynasty.addMember(member);
        dynasty.addMemberResult(member);

        return member;
    }

    
    /** 
     * Creates a single member and recursively creates his relatives.
     * @param existingMembers the Hashmap which contains the already created members.
     * @param dynasty the dynasty of the member.
     * @param name the name of the member.
     * @param link the wikipedia web page link of the member.
     * @param isEmperor true if the member was an emperor, false if he wasn't.
     * @return the new Member.
     * @throws FileNotFoundException in case the scraping doesn't work.
     */
    private Member createNewMember(
            HashMap<String, Member> existingMembers,
            Dynasty dynasty, String name,
            String link, boolean isEmperor
        ) throws FileNotFoundException {

        HashMap<String, HashMap<String, String>> summary = this.getWikipediaSummary(link);
        
        // Create the new member
        Member member = new Member(name, link, isEmperor);

        // Update status with created member
        this.setStatus(dynasty.getName() + ": " + name);

        if (!link.equals("")) {
            existingMembers.put(link.toLowerCase(), member);
        }

        // For every valid relative field recursively add every found relative
        for (String relativeKey : summary.keySet()) {
            if (this.isValidField(relativeKey)) {
                addRelativesToMember(existingMembers, summary, dynasty, member, relativeKey);
            }
        }

        return member;
    }

    
    /** 
     * Uses the given summary in order to create and add relatives to the given member.
     * @param existingMembers the Hashmap which contains the already created members.
     * @param summary The summary containing the relatives.
     * @param dynasty The dynasty of the member.
     * @param member The member.
     * @param relativeKey The type of relatives to create.
     * @throws FileNotFoundException
     */
    private void addRelativesToMember(
            HashMap<String, Member> existingMembers,
            HashMap<String, HashMap<String, String>> summary,
            Dynasty dynasty, Member member, String relativeKey
        ) throws FileNotFoundException
    {
        // Get the potential new relative type
        HashMap<String, String> tdElement = summary.get(relativeKey);

        // Scan every element in the summary
        for (String unfilteredName : tdElement.keySet()) {

            //Find the type of relative to be added
            Relative matchingRelative = Relative.matchRelative(relativeKey);

            String filteredName = filterName(unfilteredName);
            String memberLink = tdElement.get(unfilteredName);

            // if it's a valid new member
            if (isValidMemberData(filteredName, memberLink)) {

                // Recursively create the new relative as a new member or get the already existing one
                Member relative = createDynastyMember(existingMembers, dynasty, filteredName, memberLink, false);

                member.addRelative(relative, matchingRelative);
            }
        }
    }

    
    /** 
     * @param name the name to be fitered.
     * @return a String containing the filtered text.
     */
    private static String filterName(String name) {
        return name
            .replaceAll(TRAILING_INVALID_CHARS, "")
            .replaceAll(TRAILING_SINGLE_CHAR, "")
            .replaceAll(INVALID_CHARS, "")
            .trim();
    }
    
    
    /** 
     * @param elem the element to be validated as a title.
     * @return true if valid, false if not.
     */
    private static boolean isValidTitle(Element elem) {
        String elemText = elem.text().toLowerCase();

        if (elem.tagName().contains("h") && (elemText.contains("dinastia") || elemText.contains("adottivi"))) {
            return true;
        }

        return false;
    }

    
    /** 
     * @param name the name to be validated.
     * @return true if valid, false if not.
     */
    private static boolean isValidName(String name) {
        Pattern pattern = Pattern.compile(VALID_NAME_FILTER, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);

        return !matcher.find();
    }

    
    /** 
     * @param link the link to be validated.
     * @return  true if valid, false if not.
     */
    private static boolean isValidLink(String link) {
        return !link.equals("");
    }

    
    /** 
     * @param name the member name to be validated.
     * @param link the member link to be validated.
     * @return  true if valid, false if not.
     */
    private static boolean isValidMemberData(String name, String link) {
        return isValidName(name) && isValidLink(link);
    }

    
    /** 
     * @param field the field to be validated as a relative.
     * @return  true if valid, false if not.
     */
    private boolean isValidField(String field) {
        for (String validField : RELATIVE_FIELDS) {
            if (field.contains(validField)) {
                return true;
            }
        }

        return false;
    }
}