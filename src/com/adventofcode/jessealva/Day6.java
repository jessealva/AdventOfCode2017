package com.adventofcode.jessealva;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jesusalva on 12/8/17.
 */
public class Day6 {
    private static final int[] TEST_P1_1 = new int[]{0,2,7,0};
    private static final int[] TEST_P2_1 = new int[]{0,2,7,0};
    private static final String INPUT_FILE = "Day6Input.txt";
    private static final int[] TEST_FINAL_P1;
    private static final int[] TEST_FINAL_P2;

    static{
        String[] lines = null;
        try{
            lines = Utils.readFile(INPUT_FILE);
        }catch (Exception ex) {
            System.out.println("Couldn't read " + INPUT_FILE + ": " + ex.getMessage());
            ex.printStackTrace();
            System.exit(1);
        }
        String[] tmp = lines[0].split("\\s");
        TEST_FINAL_P1 = Utils.getIntArrFromStringArr(tmp);
        TEST_FINAL_P2 = Utils.getIntArrFromStringArr(tmp);
    }

    public static void main(String[] args) {
        System.out.println("Redistributions of TEST_P1_1 before cycle is " + redistributionsBeforeCycleP1(TEST_P1_1) + " but should be 5.");
        System.out.println("Redistributions of TEST_FINAL_P1 before cycle is " + redistributionsBeforeCycleP1(TEST_FINAL_P1));

        System.out.println("Redistributions of TEST_P1_1 before cycle is " + redistributionsBeforeCycleP2(TEST_P2_1) + " but should be 4.");
        System.out.println("Redistributions of TEST_FINAL_P1 before cycle is " + redistributionsBeforeCycleP2(TEST_FINAL_P2));
    }

    public static int redistributionsBeforeCycleP1(int[] memBanks) {
        int redistributions = 0;
        Map<String, Boolean> seen = new HashMap<>(memBanks.length<<2);
        boolean beenSeenBefore = false;

        while (!beenSeenBefore) {
            String keyForBank = getKeyForBank(memBanks);
            if (seen.containsKey(keyForBank)) {
                beenSeenBefore = true;
            } else {
                seen.put(getKeyForBank(memBanks), true);
                int lBank = getLargestBankIndex(memBanks);
                int val = memBanks[lBank];
                memBanks[lBank] = 0;
                //Redistribute
                for (int i = (lBank + 1) % memBanks.length; val > 0 ; --val) {
                    memBanks[i]++;
                    i = (i + 1) % memBanks.length;
                }
                redistributions++;
            }
        }

        return redistributions;
    }

    public static int redistributionsBeforeCycleP2(int[] memBanks) {
        int redistributions = 0;
        Map<String, Integer> seen = new HashMap<>(memBanks.length<<2);
        boolean beenSeenBefore = false;
        String seenKey = "";

        while (!beenSeenBefore) {
            String keyForBank = getKeyForBank(memBanks);
            if (seen.containsKey(keyForBank)) {
                beenSeenBefore = true;
                seenKey = keyForBank;
            } else {
                seen.put(getKeyForBank(memBanks), redistributions);
                int lBank = getLargestBankIndex(memBanks);
                int val = memBanks[lBank];
                memBanks[lBank] = 0;
                //Redistribute
                for (int i = (lBank + 1) % memBanks.length; val > 0 ; --val) {
                    memBanks[i]++;
                    i = (i + 1) % memBanks.length;
                }
                redistributions++;
            }
        }

        return redistributions - seen.get(seenKey);
    }

    public static int getLargestBankIndex(int[] memBanks) {
        int idx = -1;
        int largestVal = Integer.MIN_VALUE;
        if (memBanks != null) {
            for(int i = 0 ; i < memBanks.length; ++i) {
                if (idx == -1 || memBanks[i] > largestVal) {
                    idx = i;
                    largestVal = memBanks[i];
                }
            }
        }

        return idx;
    }

    private static String getKeyForBank(int[] memBank) {
        StringBuffer bf = new StringBuffer();
        if (memBank != null) {
            for(int i = 0 ; i < memBank.length ; ++i) {
                bf.append(memBank[i]);
                bf.append("_");
            }
        }

        return bf.toString();
    }
}
