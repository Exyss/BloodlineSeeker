package app.backend.scraper.results.dynasty;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import app.backend.scraper.results.ScraperResult;
import app.backend.scraper.results.member.Member;
import app.backend.scraper.results.member.Relative;
import app.backend.utils.JSONable;

/**
 * This Dynasty describes all the attributes and methods of a dynasty. Implements JSONable in order to let the instance of this class serializable in JSON.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public class Dynasty implements JSONable {
    
    /**
     * The dynasty name.
     */
    private String name;
    
    /**
     * The dynasty wikipedia link.
     */
    private String wikipediaLink;
    
    /**
     * The member of the dynasty.
     */
    private ArrayList<Member> members;

    /**
     * The member of the dynasty as ScraperResult.
     */
    private ArrayList<ScraperResult> membersResults;

    /**
     * Creates a dynasty with name and wikipedia link.
     * @param name The dynasty name.
     * @param wikipediaLink The dynasty wikipedia link.
     */
    public Dynasty(String name, String wikipediaLink) {
        this.name = name;
        this.wikipediaLink = wikipediaLink;
    }

    
    /** 
     * Recursively builds the dynasty using the given wikipedia link.
     * @param membersIDs the hashmap of currently loaded members.
     * @param name the name of the current member to be created.
     * @param wikipediaLink the link of the current member to be created.
     * @return the built Dynasty
     */
    private static Dynasty buildDynasty(HashMap<String, JSONObject> membersIDs, String name, String wikipediaLink) {
        Dynasty dynasty = new Dynasty(name, wikipediaLink);

        for (String key : membersIDs.keySet()) {
            JSONObject memberJSON = membersIDs.get(key);
            Member member = (Member) memberJSON.get("Member");
            dynasty.addMember(member);
            dynasty.addMemberResult(member);

            JSONArray parentsIDs = (JSONArray) memberJSON.get("Parents");
            JSONArray childrenIDs = (JSONArray) memberJSON.get("Children");
            JSONArray spousesIDs = (JSONArray) memberJSON.get("Spouses");

            Dynasty.completeRelatives(membersIDs, member, Relative.PARENT, parentsIDs);
            Dynasty.completeRelatives(membersIDs, member, Relative.CHILD, childrenIDs);
            Dynasty.completeRelatives(membersIDs, member, Relative.SPOUSE, spousesIDs);
         }

        return dynasty;
    }

    
    /** 
     * @param membersArray The JSONArray containing the members.
     * @return an HashMap<String, JSONObject> containing the members ID.
     */
    private static HashMap<String, JSONObject> getMembersIDs(JSONArray membersArray) {
        HashMap<String, JSONObject> membersIDs = new HashMap<String, JSONObject>();

        for (Object memberObject : membersArray) {

            JSONObject arrayItem = (JSONObject) memberObject;
            String itemID = arrayItem.keySet().iterator().next();

            JSONObject memberJSON = (JSONObject) arrayItem.get(itemID);

            String memberID = (String)memberJSON.get("ID");
            String memberName = (String)memberJSON.get("Name");
            String memberLink = (String)memberJSON.get("Wiki-link");
            boolean memberType = (boolean)memberJSON.get("isEmperor");

            Member member = new Member(memberName, memberLink, memberType);

            memberJSON.put("Member", member);

            member.setID(memberID);

            membersIDs.put(memberID, memberJSON);
        }

        return membersIDs;
    }

    
    /** 
     * Completes de-serialization by adding relatives to the given member.
     * @param membersIDs the hashmap of currently loaded members.
     * @param member the given member to be completed.
     * @param relativeType the given type of the relatives to be added.
     * @param relativesIDs the given IDs of the relatives to be added.
     */
    private static void completeRelatives(HashMap<String, JSONObject> membersIDs, Member member, Relative relativeType, JSONArray relativesIDs) {
        for (Object relativeID : relativesIDs) {
            JSONObject relativeJSON = membersIDs.get((String) relativeID);
            Member relative = (Member) relativeJSON.get("Member");
            member.addRelative(relative, relativeType);
        }
    }

    
    /** 
     * @param json the JSONObject to be converted.
     * @return the converted Dynasty.
     */
    public static Dynasty fromJSONObject(JSONObject json) {
        String name = (String)json.get("Name");
        String wikipediaLink = (String)json.get("Wiki-link");
        JSONArray membersArray = (JSONArray) json.get("Members");

        HashMap<String, JSONObject> membersIDs = Dynasty.getMembersIDs(membersArray);
        
        return buildDynasty(membersIDs, name, wikipediaLink);
    }

    
    /** 
     * @param jsonPath the path of the JSON file to be converted.
     * @return the converted Dynasty.
     * @throws IOException in case the file doesn't exists.
     */
    public static Dynasty fromJSONFile(String jsonPath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(jsonPath));
        String fileContent = reader.readLine();

        reader.close();

        return Dynasty.fromJSONObject(new JSONObject(fileContent));
    }

    
    /** 
     * Creates a JSONObject and adds to it the members of the dynasty.
     * @return the JSONObject.
     */
    @Override
    public JSONObject toJSONObject() {
        JSONObject dynastyObject = new JSONObject();

        ArrayList<JSONObject> membersJSON = new ArrayList<JSONObject>();

        for (Member member : this.members) {
            membersJSON.add(member.toJSONObject());
        }

        dynastyObject.put("Members", membersJSON);
        dynastyObject.put("Name", this.name);
        dynastyObject.put("Wiki-link", this.wikipediaLink);

        return dynastyObject;
    }
    
    
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        
        if (this.members.size() != 0) {
            for (Member member : this.members) {
                output.append("- " + member + "\n");
            }
        }
        
        return output.toString();
    }
    
    
    /** 
     * Sets the name of the dynasty.
     * @param name the String containing the dynasty name.
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /** 
     * Sets the wikipedia link of the dynasty.
     * @param wikipediaLink the string containing the wikipedia link.
     */
    public void setLink(String wikipediaLink) {
        this.wikipediaLink = wikipediaLink;
    }

    
    /** 
     * Adds the members to the dynasty.
     * @param member the member to be added.
     */
    public void addMember(Member member) {
        if (this.members == null) {
            this.members = new ArrayList<Member>();
        }
        
        if (!this.members.contains(member)) {
            this.members.add(member);
        }
    }

    
    /** 
     * Adds the given member to the current list of ScraperResults.
     * @param member the member to be added.
     */
    public void addMemberResult(Member member) {
        if (this.membersResults == null) {
            this.membersResults = new ArrayList<ScraperResult>();
        }

        ScraperResult memberResult = new ScraperResult(member, this);

        this.membersResults.add(memberResult);
    }

    
    /** 
     * @return the name of the dynasty.
     */
    public String getName() {
        return this.name;
    }

    
    /** 
     * @return the dynasty wikipedia link. 
     */
    public String getWikipediaLink() {
        return this.wikipediaLink;
    }

    
    /** 
     * @return thr ArrayList containing the dynasty members.
     */
    public ArrayList<Member> getMembers() {
        return this.members;
    }

    
    /** 
     * @return the ArrayList containing the search result members.
     */
    public ArrayList<ScraperResult> getMembersResults() {
        return this.membersResults;
    }
}