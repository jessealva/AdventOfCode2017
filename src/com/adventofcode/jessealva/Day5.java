package com.adventofcode.jessealva;

/**
 * Created by jesusalva on 12/8/17.
 */
public class Day5 {
    private static final String INPUT_FILE = "Day5Input.txt";
    private static final int [] TEST_P1_1 = new int[]{0,3,0,1,-3};
    private static final int[] FINAL_INPUT;
    private static final int[] FINAL_INPUT_COPY;

    static{
        String[] lines = null;
        try{
            lines = Utils.readFile(INPUT_FILE);
        }catch (Exception ex) {
            System.out.println("Couldn't read " + INPUT_FILE + ": " + ex.getMessage());
            ex.printStackTrace();
            System.exit(1);
        }
        FINAL_INPUT = new int[lines.length];
        FINAL_INPUT_COPY = new int[lines.length];
        for(int i = 0 ; i < lines.length ; ++i) {
            FINAL_INPUT[i] = Integer.parseInt(lines[i]);
            FINAL_INPUT_COPY[i] = Integer.parseInt(lines[i]);
        }
    }

    public static void main(String[] args) {

        System.out.println("Steps to exit TEST_P1_1 are " + countStepsToExitP1(TEST_P1_1) + " but should have been 5.");
        System.out.println("Steps to exit FINAL_INPUT are " + countStepsToExitP1(FINAL_INPUT) + " but should be 326618.");

        System.out.println("Steps to exit FINAL_INPUT are " + countStepsToExitP2(FINAL_INPUT_COPY)  + " but should be 21841249.");
    }

    public static int countStepsToExitP1(int[] instructions) {
        int stepsToLeaveEndOfArray = 0;
        if (instructions == null) { return stepsToLeaveEndOfArray; }

        for(int i = 0 ; i < instructions.length; ) {
            int increment = instructions[i] == 0 ? 0 : instructions[i];
            instructions[i]++;
            i += increment;
            stepsToLeaveEndOfArray++;
        }

        return stepsToLeaveEndOfArray;
    }

    public static int countStepsToExitP2(int[] instructions) {
        int stepsToLeaveEndOfArray = 0;
        if (instructions == null) { return stepsToLeaveEndOfArray; }

        for(int i = 0 ; i < instructions.length; ) {
            int increment = instructions[i] == 0 ? 0 : instructions[i];
            //instructions[i]++;
            instructions[i] += increment >= 3 ? -1 : 1;
            i += increment;
            stepsToLeaveEndOfArray++;
        }

        return stepsToLeaveEndOfArray;
    }
}
