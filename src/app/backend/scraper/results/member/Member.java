package app.backend.scraper.results.member;

import java.util.ArrayList;

import org.json.JSONObject;

import app.backend.utils.JSONable;

public class Member implements JSONable {
    private static int ID_counter = 0;
    
    /**
     * A String used as member ID.
     */
    private String ID;
    
    /**
     * A String used as member name.
     */
    private String name;
    
    /**
     * A String used as wikipedia link.
     */
    private String wikipediaLink;
    
    /**
     * A boolean which represent if the Member was an emperor.
     */
    private boolean isEmperor;

    /**
     * An ArrayList<Member> which contains the parents of the member.
     */
    private ArrayList<Member> parents;
    
    /**
     * An ArrayList<Member> which contains the children of the member.
     */
    private ArrayList<Member> children;
    
    /**
     * An ArrayList<Member> which contains the spouses of the member.
     */
    private ArrayList<Member> spouses;
    
    /**
     * Creates a member with name and wikipedia link, increase by one the ID_counter and sets the ID, the isEmperor as False and initialize  the parents, children and spouses ArrayLists.
     * @param name The name of the member.
     * @param wikipediaLink The wikipedia link of the member.
     */
    public Member(String name, String wikipediaLink) {
        Member.ID_counter += 1;
        this.ID = Integer.toString(Member.ID_counter);
        
        this.name = name;
        this.wikipediaLink = wikipediaLink;
        this.isEmperor = false;

        this.parents = new ArrayList<Member>();
        this.children = new ArrayList<Member>();
        this.spouses = new ArrayList<Member>();
    }

    /**
     * Creates a member with name and wikipedia link, increase by one the ID_counter and sets the ID, the isEmperor as the parameter and initialize  the parents, children and spouses ArrayLists.
     * @param name The name of the member.
     * @param wikipediaLink The wikipedia link of the member.
     * @param isEmperor The new isEmperor boolean value.
     */
    public Member(String name, String wikipediaLink, boolean isEmperor) {
        this(name, wikipediaLink);
        this.isEmperor = isEmperor;
    }

    
    /** 
     * @param relative
     * @return ArrayList<Member>
     */
    public ArrayList<Member> getRelatives(Relative relative) {
        ArrayList<Member> relatives;

        switch (relative) {
            case PARENT:
                relatives = this.parents;
                break;

            case CHILD:
                relatives =  this.children;
                break;

            case SPOUSE:
                relatives =  this.spouses;
                break;

            default:
                relatives = new ArrayList<Member>();
        }

        return relatives;
    }

    
    /** 
     * @param relative
     * @return ArrayList<String>
     */
    private ArrayList<String> getRelativesIDs(Relative relative) {
        ArrayList<String> IDs = new ArrayList<String>();

        switch (relative) {
            case PARENT:
                for (Member parent : this.parents) {
                    IDs.add(parent.getID());
                }
                break;

            case CHILD:
                for (Member child : this.children) {
                    IDs.add(child.getID());
                }
                break;

            case SPOUSE:
                for (Member spouse : this.spouses) {
                    IDs.add(spouse.getID());
                }
                break;

            default:
                break;
        }

        return IDs;
    }

    
    /** 
     * @return ArrayList<Member>
     */
    public ArrayList<Member> getAllRelatives() {
        ArrayList<Member> relatives = new ArrayList<Member>();
        relatives.addAll(this.parents);
        relatives.addAll(this.children);
        relatives.addAll(this.spouses);
        return relatives;
    }

    
    /** 
     * Adds a specified member to the correct relative ArrayList.
     * @param member The member which will be added.
     * @param relative 
     */
    public void addRelative(Member member, Relative relative) {
        switch (relative) {
            case PARENT:                
                if (!this.parents.contains(member)) {
                    this.parents.add(member);
                    member.addRelative(this, Relative.CHILD);
                }
                break;

            case CHILD:
                if (!this.children.contains(member)) {
                    this.children.add(member);
                    member.addRelative(this, Relative.PARENT);
                }
                break;
                
            case SPOUSE:
                if (!this.spouses.contains(member)) {
                    this.spouses.add(member);
                    member.addRelative(this, Relative.SPOUSE);
                }
                break;

            default:
                break;
        }
    }

    
    /** 
     * @return A JSONObject of the member.
     */
    @Override
    public JSONObject toJSONObject() {
        // TODo: move to javadoc
        /* json structure:

            ID : {
                ID : "ID",
                NAME : "name",
                WIKI-LINK : "wikipediaLink",
                ISEMPEROR : "isEmperor",
                PARENTS : [...],
                CHILDREN : [...],
                SPOUSES : [...],
            }
        */
        JSONObject json = new JSONObject();
        JSONObject memberData = new JSONObject();

        memberData.put("ID", this.getID());
        memberData.put("Name", this.name);
        memberData.put("Wiki-link", this.wikipediaLink);
        memberData.put("isEmperor", this.isEmperor);
        memberData.put("Parents", this.getRelativesIDs(Relative.PARENT));
        memberData.put("Children", this.getRelativesIDs(Relative.CHILD));
        memberData.put("Spouses", this.getRelativesIDs(Relative.SPOUSE));

        json.put(this.getID(), memberData);

        return json;
    }

    @Override
    public String toString() {
        return "[" + this.getName() + "; isEmperor: "+ this.isEmperor() + "; " + this.getWikipediaLink() + "]";
    }

    
    /** 
     * Sets the ID of the Member.
     * @param a String which identify the new ID
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    
    /** 
     * Sets the isEmperor variable to the parameter value.
     * @param isEmperor true if the member was an emperor, false if he wasn't.
     */
    public void setEmperorStatus(boolean isEmperor) {
        this.isEmperor = isEmperor;
    }

    
    /** 
     * Sets the wikipedia link of the member.
     * @param wikipediaLink the string which contains the member wikipedia link.
     */
    public void setWikipediaLink(String wikipediaLink) {
        this.wikipediaLink = wikipediaLink;
    }

    
    /** 
     * @return the member ID.
     */
    public String getID() {
        return ID;
    }
    
    
    /** 
     * @return the member name.
     */
    public String getName() {
        return name;
    }

    
    /** 
     * @return the member wikipedia link.
     */
    public String getWikipediaLink() {
        return this.wikipediaLink;
    }

    
    /** 
     * @return true if the member was an emperor, false if he wasn't.
     */
    public boolean isEmperor() {
        return this.isEmperor;
    }
}