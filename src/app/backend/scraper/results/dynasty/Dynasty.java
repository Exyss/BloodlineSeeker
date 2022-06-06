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

public class Dynasty implements JSONable {
    private String name;
    private String wikipediaLink;
    private ArrayList<Member> members;
    private ArrayList<ScraperResult> membersResults;

    public Dynasty(String name, String wikipediaLink) {
        this.name = name;
        this.wikipediaLink = wikipediaLink;
    }

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

    private static void completeRelatives(HashMap<String, JSONObject> membersIDs, Member member, Relative relativeType, JSONArray relativesIDs) {
        for (Object relativeID : relativesIDs) {
            JSONObject relativeJSON = membersIDs.get((String) relativeID);
            Member relative = (Member) relativeJSON.get("Member");
            member.addRelative(relative, relativeType);
        }
    }

    public static Dynasty fromJSONObject(JSONObject json) {
        String name = (String)json.get("Name");
        String wikipediaLink = (String)json.get("Wiki-link");
        JSONArray membersArray = (JSONArray) json.get("Members");

        HashMap<String, JSONObject> membersIDs = Dynasty.getMembersIDs(membersArray);
        
        return buildDynasty(membersIDs, name, wikipediaLink);
    }

    public static Dynasty fromJSONFile(String jsonPath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(jsonPath));
        String fileContent = reader.readLine();

        reader.close();

        return Dynasty.fromJSONObject(new JSONObject(fileContent));
    }

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
    
    public void setName(String name) {
        this.name = name;
    }

    public void setLink(String wikipediaLink) {
        this.wikipediaLink = wikipediaLink;
    }

    public void addMember(Member member) {
        if (this.members == null) {
            this.members = new ArrayList<Member>();
        }
        
        if (!this.members.contains(member)) {
            this.members.add(member);
        }
    }

    public void addMemberResult(Member member) {
        if (this.membersResults == null) {
            this.membersResults = new ArrayList<ScraperResult>();
        }

        ScraperResult memberResult = new ScraperResult(member, this);

        this.membersResults.add(memberResult);
    }

    public String getName() {
        return this.name;
    }

    public String getWikipediaLink() {
        return this.wikipediaLink;
    }

    public ArrayList<Member> getMembers() {
        return this.members;
    }

    public ArrayList<ScraperResult> getMembersResults() {
        return this.membersResults;
    }
}