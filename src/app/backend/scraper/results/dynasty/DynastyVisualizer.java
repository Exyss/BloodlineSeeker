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
import guru.nidi.graphviz.attribute.Attributes;
import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.GraphvizException;
import guru.nidi.graphviz.engine.Renderer;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;

public final class DynastyVisualizer {
    //Colors are in hexadecimal RGB
    private static final String CENTER_MEMBER_PRIMARY_COLOR = "#12664B";
    private static final String CENTER_MEMBER_SECONDARY_COLOR = "#FFFFFF";
    private static final String EMPEROR_MEMBER_PRIMARY_COLOR = "#7E2526";
    private static final String EMPEROR_MEMBER_SECONDARY_COLOR = "#D4AF37";
    private static final String NODE_FONTSIZE = "20";

    //Measures are in inches
    private static final int SUBGRAPH_RANK_SEPARATION = 2;
    private static final double SUBGRAPH_NODE_SEPARATION = 1.5;
    private static final double SUBGRAPH_PADDING = 3.5;

    private static final int MAX_DEPTH = 3;

    private static Member centerMember;

    //Iteratively generate whole dynasty with no central member
    public static MutableGraph toGraph(Dynasty dynasty) {
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
    
    //Recursively generate limited dynasty graph using a central member 
    public static MutableGraph toGraph(Member member) {
        DynastyVisualizer.centerMember = member;

        ArrayList<String> existingIDs = new ArrayList<String>();
        MutableGraph familyGraph = createFamily(existingIDs, member, MAX_DEPTH);

        return familyGraph;
    }

    private static void linkToRelativesIterative(MutableNode memberNode, Member member, Relative relative) {
        for (Member memberRelative : member.getRelatives(relative)) {
            linkToRelativeNode(memberNode, memberRelative, relative);
        }
    }

    private static void linkToRelativesRecursive(ArrayList<String> existingIDs, MutableGraph familyGraph, Relative relative, Member member, MutableNode memberNode, int depth) {
        for (Member memberRelative : member.getRelatives(relative)) {

            //Create links only if the relative is a child or a spouse
            if (!relative.equals(Relative.PARENT)) {
                linkToRelativeNode(memberNode, memberRelative, relative);
            }

            if (!existingIDs.contains(memberRelative.getID())) {
                MutableGraph memberRelativeFamily = createFamily(existingIDs, memberRelative, depth - 1);

                if (memberRelativeFamily != null) {
                    familyGraph.add(memberRelativeFamily);
                }
            }
        }
    }
    
    private static void linkToRelativeNode(MutableNode memberNode, Member memberRelative, Relative relative) {
        switch (relative) {
            case CHILD:
                MutableNode childNode = createMemberNode(memberRelative);
                memberNode.addLink(to(childNode).with(Style.BOLD));
                break;
                
            case SPOUSE:
                MutableNode spouseNode = createMemberNode(memberRelative);
                memberNode.addLink(to(spouseNode).with(Style.DASHED, Color.RED));
                break;

            default:
                break;
        }
    }

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

    private static MutableNode createMemberNode(Member member) {
        MutableNode memberNode = mutNode(member.getID()).add(Shape.RECTANGLE);

        String nodePrimaryColor;
        String nodeSecondaryColor;

        if (centerMember != null && member.getID().equals(centerMember.getID())) {
            nodePrimaryColor = CENTER_MEMBER_PRIMARY_COLOR;
            nodeSecondaryColor = CENTER_MEMBER_SECONDARY_COLOR;
        } else if (member.isEmperor()) {
            nodePrimaryColor = EMPEROR_MEMBER_PRIMARY_COLOR;
            nodeSecondaryColor = EMPEROR_MEMBER_SECONDARY_COLOR;
        }
        else{
            return memberNode.add(Label.lines(member.getName()));
        }

        memberNode.add(
            Style.FILLED,
            Label.lines(member.getName()),
            Attributes.attrs(
                Attributes.attr("fillcolor", nodePrimaryColor),        //background color
                Attributes.attr("fontcolor", nodeSecondaryColor),    //font color
                Attributes.attr("color", nodeSecondaryColor),        //border color
                Attributes.attr("fontsize", NODE_FONTSIZE)            //font size
            )
        );

        return memberNode;
    }

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

    private static MutableGraph generateBlankSubGraph(String subGraphName) {
        return mutGraph(subGraphName).setDirected(true);
    }

    private static Renderer renderGraph(MutableGraph graph) throws FileNotFoundException {
        try {
            return Graphviz.fromGraph(graph).render(Format.PNG);
        } catch (GraphvizException ge) {
            throw new FileNotFoundException("GraphViz executable not found. Please go to https://graphviz.org/download/");
        }
    }

    public static Renderer toRenderedGraph(Dynasty dynasty) throws FileNotFoundException {
        return renderGraph(toGraph(dynasty));
    }

    public static Renderer toRenderedGraph(Member member) throws FileNotFoundException {
        return renderGraph(toGraph(member));
    }

    public static BufferedImage toBufferedImage(Dynasty dynasty) throws FileNotFoundException {
        return toRenderedGraph(dynasty).toImage();
    }

    public static BufferedImage toBufferedImage(Member member) throws FileNotFoundException {
        return toRenderedGraph(member).toImage();
    }

    public static void toImageFile(Dynasty dynasty, String outputPath) throws IOException {
        toRenderedGraph(dynasty).toFile(new File(outputPath));
    }

    public static void toImageFile(Member member, String outputPath) throws IOException {
        toRenderedGraph(member).toFile(new File(outputPath));
    }
}