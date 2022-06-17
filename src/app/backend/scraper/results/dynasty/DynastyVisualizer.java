package app.backend.scraper.results.dynasty;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;
import static guru.nidi.graphviz.model.Factory.to;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import app.backend.scraper.results.member.Member;
import app.backend.scraper.results.member.Relative;
import app.frontend.utils.QuattrocentoFont;
import guru.nidi.graphviz.attribute.Attributes;
import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.GraphvizException;
import guru.nidi.graphviz.engine.Renderer;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;

/**
 * This DynastyVisualizer generates a graph from aspecific dynasty or member.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public final class DynastyVisualizer {
    //Colors are in hexadecimal RGB
    
    /**
     * The primary color of the center member.
     */
    private static final String CENTER_MEMBER_PRIMARY_COLOR = "#12664B";

    /**
     * The secondary color of the center member.
     */
    private static final String CENTER_MEMBER_SECONDARY_COLOR = "#FFFFFF";

    /**
     * The primary color of the emperor members.
     */
    private static final String EMPEROR_MEMBER_PRIMARY_COLOR = "#7E2526";
    /**
     * The secondary color of the emperor members.
     */
    private static final String EMPEROR_MEMBER_SECONDARY_COLOR = "#D4AF37";

    /**
     * The font size of the node.
     */
    private static final String NODE_FONTSIZE = "20";

    /**
     * The font used for the graphs.
     */
    private static final String GRAPH_FONT = QuattrocentoFont.PLAIN_20.get().getName();

    //Measures are in inches
    
    /**
     * The gap between the various ranks in the graph.
     */
    private static final int SUBGRAPH_RANK_SEPARATION = 2;

    /**
     * The gap between the nodes which are in the same rank in the graph.
     */
    private static final double SUBGRAPH_NODE_SEPARATION = 1.5;

    /**
     * The gap between the margin of the graph and the nodes.
     */
    private static final double SUBGRAPH_PADDING = 3.5;

    /**
     * The maximum number of ranks in the graph.
     */
    private static final int MAX_DEPTH = 3;

    /**
     * The centered node.
     */
    private static Member centerMember;

    
    /** 
     * Converts a whole Dynasty object to a graph.
     * @param dynasty the Dynasty object to be converted.
     * @return the converted MutableGraph.
     */
    public static MutableGraph toGraph(Dynasty dynasty) {
        //Iteratively generate whole dynasty with no central member
        DynastyVisualizer.centerMember = null;

        MutableGraph dynastyGraph = generateBlankGraph(dynasty.getName());

        for (Member member : dynasty.getMembers()) {
            MutableNode memberNode = createMemberNode(member);
            
            linkToRelativesIterative(memberNode, member, Relative.CHILD);
            linkToRelativesIterative(memberNode, member, Relative.SPOUSE);

            dynastyGraph.add(memberNode);
        }

        return dynastyGraph;
    }
    
    
    /**
     * @param member the Member to use as center of the graph.
     * @return the generated MutableGraph containing the closest relatives of the given Member.
     */
    public static MutableGraph toGraph(Member member) {
        //Recursively generate limited dynasty graph using a central member 
        DynastyVisualizer.centerMember = member;

        ArrayList<String> existingIDs = new ArrayList<String>();
        MutableGraph familyGraph = createFamily(existingIDs, member, MAX_DEPTH);

        return familyGraph;
    }

    
    /**
     * Iteratively link members to it's relatives of the given type.
     * @param member the member to be linked.
     * @param memberNode the given member's associated MutableNode.
     * @param relative the member's type of relatives to be linked.
     */
    private static void linkToRelativesIterative(MutableNode memberNode, Member member, Relative relative) {
        for (Member memberRelative : member.getRelatives(relative)) {
            linkToRelativeNode(memberNode, memberRelative, relative);
        }
    }

    
    /** 
     * Recursively links members to the given relative type. 
     * @param existingIDs the ArrayList of already loaded links.
     * @param familyGraph the graph which the new nodes should be added.
     * @param relative the type of relatives to be linked.
     * @param member the member to be linked.
     * @param memberNode the given member's associated MutableNode.
     * @param depth the maximum recursion depth
     */
    private static void linkToRelativesRecursive(ArrayList<String> existingIDs, MutableGraph familyGraph, Relative relative, Member member, MutableNode memberNode, int depth) {
        for (Member memberRelative : member.getRelatives(relative)) {

            if (!relative.equals(Relative.PARENT)) {
                linkToRelativeNode(memberNode, memberRelative, relative);
            }

            //Recursively create links only if the relative is a child or a spouse
            if (!existingIDs.contains(memberRelative.getID())) {
                MutableGraph memberRelativeFamily = createFamily(existingIDs, memberRelative, depth - 1);

                if (memberRelativeFamily != null) {
                    familyGraph.add(memberRelativeFamily);
                }
            }
        }
    }
    
    
    /** 
     * Links the given nodes with the given type of relation.
     * @param memberNode the given member node to be linked.
     * @param memberRelative the given relative node to be linked.
     * @param relative the type of relation link.
     */
    private static void linkToRelativeNode(MutableNode memberNode, Member memberRelative, Relative relative) {
        switch (relative) {
            case CHILD:
                MutableNode childNode = createMemberNode(memberRelative);
                memberNode.addLink(to(childNode).with(Style.BOLD, Font.name(GRAPH_FONT)));
                break;
                
            case SPOUSE:
                MutableNode spouseNode = createMemberNode(memberRelative);
                memberNode.addLink(to(spouseNode).with(Style.DASHED, Color.RED, Font.name(GRAPH_FONT)));
                break;

            default:
                break;
        }
    }

    
    /** 
     * Recursively generates a graph containing the nearest relatives of the given member.
     * @param existingIDs the ArrayList of already loaded links.
     * @param member the member to be linked.
     * @param depth the maximum recursion depth
     * @return the generated graph.
     */
    private static MutableGraph createFamily(ArrayList<String> existingIDs, Member member, int depth) {
        if (depth == 0) {
            return null;
        }
        
        MutableGraph familyGraph;

        if (depth == MAX_DEPTH) {
            familyGraph = generateBlankGraph(member.getName());
        } else {
            familyGraph = generateBlankSubGraph(member.getName());
        }
        
        MutableNode memberNode = createMemberNode(member);
        existingIDs.add(member.getID());

        linkToRelativesRecursive(existingIDs, familyGraph, Relative.PARENT, member, memberNode, depth);
        linkToRelativesRecursive(existingIDs, familyGraph, Relative.CHILD, member, memberNode, depth);
        linkToRelativesRecursive(existingIDs, familyGraph, Relative.SPOUSE, member, memberNode, depth);

        familyGraph.add(memberNode);

        return familyGraph;
    }

    
    /** 
     * Creates a MemberNode for the given member or returns the already existing one
     * @param member the member which has to be coverted in MutableNode.
     * @return the generated MutableNode.
     */
    private static MutableNode createMemberNode(Member member) {
        // In case the MutableNodes already exists, it gets automatically returned
        MutableNode memberNode = mutNode(member.getID()).add(Shape.RECTANGLE);

        String nodePrimaryColor;
        String nodeSecondaryColor;

        // Set node styles based on it's type:

        // If it's a central graph node
        if (centerMember != null && member.getID().equals(centerMember.getID())) {
            nodePrimaryColor = CENTER_MEMBER_PRIMARY_COLOR;
            nodeSecondaryColor = CENTER_MEMBER_SECONDARY_COLOR;
        
        // If it's an emperor
        } else if (member.isEmperor()) {
            nodePrimaryColor = EMPEROR_MEMBER_PRIMARY_COLOR;
            nodeSecondaryColor = EMPEROR_MEMBER_SECONDARY_COLOR;
        }

        // If it's a common member
        else{
            return memberNode.add(
                Label.lines(member.getName()),
                Font.name(GRAPH_FONT)
            );
        }

        // Create the node
        memberNode.add(
            Style.FILLED,
            Label.lines(member.getName()),
            Attributes.attrs(
                Attributes.attr("fillcolor", nodePrimaryColor),        //background color
                Attributes.attr("fontcolor", nodeSecondaryColor),    //font color
                Attributes.attr("color", nodeSecondaryColor),        //border color
                Attributes.attr("fontsize", NODE_FONTSIZE),            //font size
                Attributes.attr("fontname", GRAPH_FONT)                 //graph font
            )
        );

        return memberNode;
    }

    
    /** 
     * Generates a new blank graph with no linked nodes
     * @param subGraphName the name of the graph.
     * @return the generated MutableGraph.
     */
    private static MutableGraph generateBlankGraph(String subGraphName) {
        return mutGraph(subGraphName)
            .setDirected(true)
            .graphAttrs()
            .add(
                Attributes.attr("ranksep", SUBGRAPH_RANK_SEPARATION),
                Attributes.attr("nodesep", SUBGRAPH_NODE_SEPARATION),
                Attributes.attr("pad", SUBGRAPH_PADDING)
            );
    }

    
    /** 
     * Generates a new blank subgraph with no linked nodes.
     * @param subGraphName the name of the subgraph.
     * @return the generated MutableGraph
     */
    private static MutableGraph generateBlankSubGraph(String subGraphName) {
        return mutGraph(subGraphName).setDirected(true);
    }

    
    /** 
     * @param graph the graph to render.
     * @return the rendered graph as Render. 
     * @throws FileNotFoundException in absence of the GraphViz executable file.
     */
    private static Renderer renderGraph(MutableGraph graph) throws FileNotFoundException {
        try {
            return Graphviz.fromGraph(graph).render(Format.PNG);
        } catch (GraphvizException ge) {
            throw new FileNotFoundException("GraphViz executable not found. Please go to https://graphviz.org/download/");
        }
    }

    
    /** 
     * @param dynasty the dynasty to render.
     * @return the rendered graph as Render. 
     * @throws FileNotFoundException in absence of the GraphViz executable file.
     */
    public static Renderer toRenderedGraph(Dynasty dynasty) throws FileNotFoundException {
        return renderGraph(toGraph(dynasty));
    }

    
    /** 
     * @param member the dynasty to render.
     * @return the rendered graph as Render. 
     * @throws FileNotFoundException in absence of the GraphViz executable file.
     */
    public static Renderer toRenderedGraph(Member member) throws FileNotFoundException {
        return renderGraph(toGraph(member));
    }

    
    /** 
     * @param dynasty dynasty the dynasty to render.
     * @return BufferedImage
     * @throws FileNotFoundException in absence of the GraphViz executable file.
     */
    public static BufferedImage toBufferedImage(Dynasty dynasty) throws FileNotFoundException {
        return toRenderedGraph(dynasty).toImage();
    }

    
    /** 
     * @param member the member to render.
     * @return the rendered graph as BufferedImage. 
     * @throws FileNotFoundException in absence of the GraphViz executable file.
     */
    public static BufferedImage toBufferedImage(Member member) throws FileNotFoundException {
        return toRenderedGraph(member).toImage();
    }

    
    /** 
     * @param dynasty the dynasty to render.
     * @param outputPath the output path.
     * @throws IOException in absence of the GraphViz executable file.
     */
    public static void toImageFile(Dynasty dynasty, String outputPath) throws IOException {
        toRenderedGraph(dynasty).toFile(new File(outputPath));
    }

    
    /** 
     * @param member the member to render.
     * @param outputPath the output path.
     * @throws IOException in absence of the GraphViz executable file.
     */
    public static void toImageFile(Member member, String outputPath) throws IOException {
        toRenderedGraph(member).toFile(new File(outputPath));
    }
}