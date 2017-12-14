package com.adventofcode.jessealva;

import java.util.Collection;
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
        System.out.println("Bottom program for input TEST_P1_1 is: '" + getParentP1(TEST_P1_1) + "' but should be 'tknk'");
        System.out.println("Bottom program for input TEST_FINAL_P1 is: '" + getParentP1(TEST_FINAL_P1) + "'");
    }

    /*
    The bottom most program must have children...only the top most dont have children, so we can eliminate
    candidates that way.  For the remaining parents, they can be eliminated if they exist as a child of one
    of the other parents.  So we can create a list of all parents, and remove them as we find then while we're
    processing all the children from each prospective parent.
     */
    public static String getParentP1(Program[] apps) {
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
        String parent = null;
        for(Program p : parents.keySet()) {
            if (!children.contains(p.name)) {
                parent = p.name;
            }
        }
        return parent;
    }

    public static int getWeightAdjustmentNeededP2(Program[] apps) {
        int adjustment = 0;
        Map<String, Program> lookup = createLookupTable(apps);
        String parent = getParentP1(apps);
        //Get the bottom most program
        Program p = lookup.get(parent);
        //Starting from there, lets get the total weight for him and all his children, which should walk down
        //The entire tree.
        getTotalWeight(p, lookup);
        //We now have the parent, and each nodes total weight.  Lets figure out which disk is unbalanced.
        Program unbalancedWeight = findUnbalanced(p, lookup);

        return adjustment;
    }

    private static Program findUnbalanced(Program start, Map<String, Program> lookup) {
        Program unbalanced = null;
        //int[] childTotalsWeights = new int[start.childIds.length];
        /*
        Need to figure out which one is different from the rest.  If there's only two children off the bottom node, then
        we need to check each of them more cause we really don't know which one is wrong.
         */
        //int tot = lookup.get(start.childIds[0]).totalWeight;
        Map<Integer, Integer> map = new HashMap<>(2);
        //map.put(tot, 0);
        int unique_one = 0;
        for(int i = 0 ; i < start.childIds.length; ++i) {
            int tot = lookup.get(start.childIds[i]).totalWeight;
            if(map.containsKey(tot)) {
                map.put(tot, map.get(tot) + i);
            } else {
                map.put(tot, i);
            }
        }
        Collection<Integer> vals = map.values();
        int uniqIdx = -1;
        for (Integer v : vals) {
            if (v < start.childIds.length) {
                uniqIdx = v;
                break;
            }
        }
        if (uniqIdx != -1) {
            Program badChild = lookup.get(start.childIds[uniqIdx]);
        }

        return unbalanced;
    }

    private static Map<String, Program> createLookupTable(Program[] programs) {
        Map<String, Program> lookup = new HashMap<>(programs.length);
        for(Program p: programs) {
            lookup.put(p.name, p);
        }
        return lookup;
    }

    private static int getTotalWeight(Program p, Map<String, Program> programs) {
        int total = p.weight;
        if(p.childIds != null) {
            for(String i : p.childIds) {
                total += getTotalWeight(programs.get(i), programs);
            }
        }
        p.totalWeight = total;
        return total;
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
    }

}
