package com.adventofcode.jessealva;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by jesusalva on 12/11/17.
 */
public class Day7 {
    private static final String TEST_FILE_NAME = "Day7test.txt";
    private static final String INPUT_FILE = "Day7Input.txt";
    public static final Program[] TEST_P1_1;
    public static final Program[] TEST_FINAL_P1;

    public static void main(String[] args) {
        System.out.println("Bottom program for input TEST_P1_1 is: '" + getParentP1(TEST_P1_1).name + "' but should be 'tknk'");
        System.out.println("Bottom program for input TEST_FINAL_P1 is: '" + getParentP1(TEST_FINAL_P1).name + "'");

        System.out.println("Offset required to balance plates for TEST_P2_1 is: " + getWeightAdjustmentNeededP2(TEST_P1_1) + " and should be 60.");
        System.out.println("Offset required to balance plates for TEST_FINAL_P2 is: " + getWeightAdjustmentNeededP2(TEST_FINAL_P1) + " and should be 529.");
    }

    /*
    The bottom most program must have children...only the top most dont have children, so we can eliminate
    candidates that way.  For the remaining parents, they can be eliminated if they exist as a child of one
    of the other parents.  So we can create a list of all parents, and remove them as we find then while we're
    processing all the children from each prospective parent.
     */
    public static Program getParentP1(Program[] apps) {
        Map<Program, Boolean> parents = new HashMap<>(apps.length);
        Set<String> children = new HashSet<>(apps.length);
        for(Program p : apps) {
            if (p.childIds != null && p.childIds.length > 0) {
                parents.put(p, true);
                for(String c : p.childIds) {
                    children.add(c);
                }
            }
        }
        Program parent = null;
        for(Program p : parents.keySet()) {
            if (!children.contains(p.name)) {
                parent = p;
            }
        }
        return parent;
    }

    public static int getWeightAdjustmentNeededP2(Program[] apps) {
        Map<String, Program> lookup = createLookupTable(apps);
        Program parent = getParentP1(apps);
        //We now have the parent, and each nodes total weight.  Lets figure out which disk is unbalanced.
        Node root = buildTree(parent, lookup);

        return getAdjustmentToBalanceDisks(root);
    }

    private static int getAdjustmentToBalanceDisks(Node start) {
        //The only one that needs to check are parents.  Children are by default ok as they have nothing to check.
        //Parents on the other hand have to check that all their children have the same weight, so that they are balanced.
        if(start.children == null || start.children.length == 0) {
            return 0;
        }
        Map<Integer, Integer> weightToIndex = new HashMap<>();
        Map<Integer, Integer> weightToCount = new HashMap<>();
        int adjustedWeight = 0;
        //Builds the lists of weight (they keys) to how many were seen and weight (key) to the index in the
        //child array. The thought is that if the disk is balanced, both maps will contain a single key. If the
        //maps contain 2 keys, then we
        for (int i = 0 ; i < start.children.length ; ++i) {
            adjustedWeight = getAdjustmentToBalanceDisks(start.children[i]);
            if (adjustedWeight > 0) {
                break;
            }
            int key = start.children[i].p.totalWeight;
            weightToIndex.put(key, i);
            if(weightToCount.containsKey(key)) {
                weightToCount.put(key, weightToCount.get(key)+1);
            } else {
                weightToCount.put(key, 1);
            }
        }
        if(adjustedWeight > 0) {
            return adjustedWeight;
        }
        int shouldBeWeight = 0;
        int badTotalWeight = 0;
        int badWeight = 0;
        for(int key : weightToCount.keySet()) {
            if(weightToCount.get(key) == 1) {
                badTotalWeight = start.children[weightToIndex.get(key)].p.totalWeight;
                badWeight = start.children[weightToIndex.get(key)].p.weight;
            } else {
                shouldBeWeight = start.children[weightToIndex.get(key)].p.totalWeight;
            }
        }

        if (badWeight > 0) {
            boolean subtract = badTotalWeight > shouldBeWeight;
            int offset = subtract ? badTotalWeight - shouldBeWeight : shouldBeWeight - badTotalWeight;
            return subtract ? badWeight - offset : badWeight + offset;
        }

        return 0;
    }

    private static Node buildTree(Program parent, Map<String, Program> lookup) {
        Node[] children = null;
        parent.totalWeight = parent.weight;
        if(parent.childIds != null) {
            children = new Node[parent.childIds.length];
            for(int i = 0 ; i < parent.childIds.length ; ++i ){
                children[i] = buildTree(lookup.get(parent.childIds[i]), lookup);
                parent.totalWeight += children[i].p.totalWeight;
            }
        }

        return new Node(parent, children);
    }

    private static Map<String, Program> LOOKUP;
    private static Map<String, Program> createLookupTable(Program[] programs) {
        if (LOOKUP == null) {
            LOOKUP = new HashMap<>(programs.length);
            for(Program p: programs) {
                LOOKUP.put(p.name, p);
            }
        }

        return LOOKUP;
    }

    static {
        String[] lines = null;
        try{
            lines = Utils.readFile(TEST_FILE_NAME);
        } catch(Exception ex) {
            System.out.println("Couldn't read " + TEST_FILE_NAME + ": " + ex.getMessage());
            ex.printStackTrace();
            System.exit(1);
        }
        TEST_P1_1 = parseInput(lines);

        try{
            lines = Utils.readFile(INPUT_FILE);
        } catch(Exception ex) {
            System.out.println("Couldn't read " + TEST_FILE_NAME + ": " + ex.getMessage());
            ex.printStackTrace();
            System.exit(1);
        }
        TEST_FINAL_P1 = parseInput(lines);
    }

    public static Program[] parseInput(String[] lines){
        if (lines == null) {
            return null;
        }
        Program[] programs = new Program[lines.length];
        for (int i = 0 ; i < lines.length ; ++i) {
            programs[i] = new Program(getName(lines[i]), getWeight(lines[i]), getChildIds(lines[i]));
        }
        return programs;
    }

    private static String[] getChildIds(String s) {
        if (s.indexOf("->") == -1) {
            return new String[]{};
        }
        String list = s.split(" -> ")[1];
        return list.split(", ");
    }

    private static String getName(String s) {
        return s.substring(0,s.indexOf(" "));
    }

    private static int getWeight(String s) {
        int start = s.indexOf("(")+1;
        int end = s.indexOf(")");
        return Integer.parseInt(s.substring(start,end));
    }

    static class Program {
        private String name;
        private int weight;
        private String[] childIds;
        private int totalWeight;

        public Program(String name, int weight, String[] children) {
            this.name = name;
            this.weight = weight;
            this.childIds = children;
        }
        public String toString() {
            String childIds = "";
            for(int i = 0 ; i < this.childIds.length ; ++i) {
                childIds += this.childIds[i] + ", ";
            }
            return "{Name: " + name + ", weight: " + weight + ", childIds: [" + childIds + "], totalWeight: " + totalWeight + "}";
        }
    }

    static class Node{
        private Program p;
        private Node[] children;

        public Node(Program p, Node[] children) {
            this.p = p;
            this.children = children;
        }
    }
}
