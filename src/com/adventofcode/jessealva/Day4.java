package com.adventofcode.jessealva;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jesusalva on 12/7/17.
 */
public class Day4 {
    private static String INPUT_FILE = "Day4Input.txt";
    private static final String PHRASE1 = "aa bb cc dd ee";
    private static final String PHRASE2 = "aa bb cc dd aa";
    private static final String PHRASE3 = "aa bb cc dd aaa";

    public static void main(String[] args){
        String[] lines = null;
        try{
            lines = Utils.readFile(INPUT_FILE);
        }catch (Exception ex) {
            System.out.println("Couldn't read " + INPUT_FILE + ": " + ex.getMessage());
            ex.printStackTrace();
            System.exit(1);
        }

        System.out.println("Phrase1 should be valid but code says: " + isValidPassphraseP1(PHRASE1));
        System.out.println("Phrase2 should be invalid but code says: " + isValidPassphraseP1(PHRASE2));
        System.out.println("Phrase3 should be valid but code says: " + isValidPassphraseP1(PHRASE3));
        int validPassphrases = 0;
        for(String line : lines) {
            //System.out.println(line);
            if(isValidPassphraseP1(line)){
                validPassphrases++;
            }
        }
        System.out.println("Total valid passphrases P1 is: " + validPassphrases + " and should be 383");
        validPassphrases = 0;
        for(String line : lines) {
            if(isValidPassphraseP2(line)){
                validPassphrases++;
            }
        }
        System.out.println("Total valid passphrases P2 is: " + validPassphrases + " and should be 265");
    }

    public static boolean isValidPassphraseP1(String phrase) {
        //Each phrase is a space separated list of words
        String[] words = phrase.split(" ");
//        int mask = 0;
        Map<String, Boolean> seen = new HashMap<>();
        for (String word : words) {
            if (seen.containsKey(word)) {
                return false;
            }
            seen.put(word, true);
        }
        return true;
    }

    public static boolean isValidPassphraseP2(String phrase) {
        String[] words = phrase.split(" ");
        String sep = "________";
        Map<String, Boolean> seen = new HashMap<>();
        for (int i = 0 ; i < words.length; ++i) {
            for (int j = 0; j < words.length; ++j) {
                String key = words[i] + sep + words[j];
                if (i == j || seen.containsKey(key)) {
                    continue;
                }
                if(isAnagram(words[i], words[j])){
                    return false;
                }
                seen.put(key, true);
            }
        }
        return true;
    }

    /*
    Learning to use bits. Theory here is if the two strings have the same bit mask, then XORing them
    together will result in 0. If the strings are different, then the result will be non-0 and they're
    not anagrams.

    Returns true if it is an anagram.
     */
    public static boolean isAnagram(String s1, String s2){
        byte[] s1b = s1.getBytes();
        byte[] s2b = s2.getBytes();
        int s1mask = 0, s2mask = 0;
        for(int i: s1b) { s1mask = setBit(s1mask, i); }
        for(int i: s2b) { s2mask = setBit(s2mask, i); }
        return (s1mask ^ s2mask) == 0;
    }

    public static int setBit(int mask, int bit) {
        return mask | (1<< bit);
    }

    public static boolean getBit(int mask, int bit) {
        return    (mask & (1<<bit)) != 0;
    }
}
