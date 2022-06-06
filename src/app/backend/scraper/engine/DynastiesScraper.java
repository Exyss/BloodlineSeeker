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

public class DynastiesScraper extends WikipediaScraper {
    private static final String MAIN_EMPEROR_PAGE = WIKI_DOMAIN + "/wiki/Imperatori_romani";
    private static final String[] RELATIVE_FIELDS = {"Consorte", "Madre", "Padre", "Coniuge", "Figli"};

    private static final String VALID_NAME_FILTER = "(^Da [\\w\\s]+:)|( e )|Adottivi|equestre|due figli";
    private static final String TRAILING_INVALID_CHARS = "[^a-zA-Z\']+$";
    private static final String TRAILING_SINGLE_CHAR = "\\s[a-zA-Z]?$";
    private static final String INVALID_CHARS = "\"";

    public ArrayList<Dynasty> scrapeDynasties() throws FileNotFoundException {
        HashMap<Dynasty, HashMap<String, String>> emperorDynasties = this.getEmperorDynasties();

        HashMap<String, Member> existingMembers = new HashMap<String, Member>();

        ArrayList<Dynasty> dynasties = new ArrayList<Dynasty>();
        
        for (Dynasty dynasty : emperorDynasties.keySet()) {
            dynasties.add(dynasty);
            
            HashMap<String, String> emperors = emperorDynasties.get(dynasty);
            
            for (String emperorLink : emperors.keySet()) {
                String emperorName = emperors.get(emperorLink);

                createDynastyMember(existingMembers, dynasty, emperorName, emperorLink, true);
            }
        }
        
        return dynasties;
    }

    private HashMap<Dynasty, HashMap<String, String>> getEmperorDynasties() throws FileNotFoundException {
        this.setStatus("Initializing Emperors links scraping...");
        this.setStatus("");

        HashMap<Dynasty, HashMap<String, String>> dynasties = new HashMap<Dynasty, HashMap<String, String>>();

        String htmlEmperorsPage = this.getPageHTML(MAIN_EMPEROR_PAGE);

        Element tablesDiv = Jsoup.parse(htmlEmperorsPage).getElementsByClass("mw-parser-output").get(0);
        
        Elements contents = tablesDiv.children();
        
        for (int i = 0; i < contents.size() - 1; i++) {
            Element elem = contents.get(i);

            if (isValidTitle(elem)) {
                Element emperorsTable = contents.get(i + 1);
                
                if (emperorsTable.className().contains("wikitable")) {
                    Element dynastyAnchor = elem.getElementsByTag("a").get(0);
                    String dynastyName = DynastiesScraper.parseParenthesis(dynastyAnchor.attr("title"));
                    String dynastyLink = WIKI_DOMAIN + dynastyAnchor.attr("href");

                    Dynasty dynasty = new Dynasty(dynastyName, dynastyLink);

                    this.setStatus("Found dynasty: " + dynastyLink);

                    HashMap<String, String> emperors = this.parseEmperorsTable(emperorsTable);
                    
                    dynasties.put(dynasty, emperors);
                }
            }
        }

        return dynasties;
    }
    
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

    private Member createNewMember(
            HashMap<String, Member> existingMembers,
            Dynasty dynasty, String name,
            String link, boolean isEmperor
        ) throws FileNotFoundException {

        HashMap<String, HashMap<String, String>> summary = this.getWikipediaSummary(link);
        
        Member member = new Member(name, link, isEmperor);

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

    private void addRelativesToMember(
            HashMap<String, Member> existingMembers,
            HashMap<String, HashMap<String, String>> summary,
            Dynasty dynasty, Member member, String relativeKey
        ) throws FileNotFoundException
    {
        HashMap<String, String> tdElement = summary.get(relativeKey);

        for (String unfilteredName : tdElement.keySet()) {
            Relative matchingRelative = Relative.matchRelative(relativeKey);

            String filteredName = filterName(unfilteredName);
            String memberLink = tdElement.get(unfilteredName);

            if (isValidMemberData(filteredName, memberLink)) {
                Member relative = createDynastyMember(existingMembers, dynasty, filteredName, memberLink, false);

                member.addRelative(relative, matchingRelative);
            }
        }
    }

    private static String filterName(String name) {
        return name
            .replaceAll(TRAILING_INVALID_CHARS, "")
            .replaceAll(TRAILING_SINGLE_CHAR, "")
            .replaceAll(INVALID_CHARS, "")
            .trim();
    }
    
    private static boolean isValidTitle(Element elem) {
        String elemText = elem.text().toLowerCase();

        if (elem.tagName().contains("h") && (elemText.contains("dinastia") || elemText.contains("adottivi"))) {
            return true;
        }

        return false;
    }

    private static boolean isValidName(String name) {
        Pattern pattern = Pattern.compile(VALID_NAME_FILTER, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);

        return !matcher.find();
    }

    private static boolean isValidLink(String link) {
        return !link.equals("");
    }

    private static boolean isValidMemberData(String name, String link) {
        return isValidName(name) && isValidLink(link);
    }

    private boolean isValidField(String field) {
        for (String validField : RELATIVE_FIELDS) {
            if (field.contains(validField)) {
                return true;
            }
        }

        return false;
    }
}