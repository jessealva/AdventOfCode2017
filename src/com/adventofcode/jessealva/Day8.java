package com.adventofcode.jessealva;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jesusalva on 12/19/17.
 */
public class Day8 {
    private static final String[] TEST_P1;
    private static final String[] FINAL_TEST_P1;
    private static Map<String, Integer> registers;
    static {
        String[] lines = null;
        try {
            lines = Utils.readFile("Day8test.txt");
        } catch (IOException ioe) { System.exit(1); }
        TEST_P1 = lines;

        try {
            lines = Utils.readFile("Day8Input.txt");
        } catch (IOException ioe) { System.exit(1); }
        FINAL_TEST_P1 = lines;
    }

    public static void main(String[] args) {
        System.out.println("Test TEST_P1 should have a max size of " + getHighestValueRegisterP1(TEST_P1) + " but should be 1.");
        System.out.println("Test FINAL_TEST_P1 should have a max size of " + getHighestValueRegisterP1(FINAL_TEST_P1));
        System.out.println("alternate: " + Collections.max(registers.values()));
    }

    public static int getHighestValueRegisterP1(String[] instructions) {
        registers = new HashMap<>(instructions.length);
        Instruction[] instrs = new Instruction[instructions.length];
        for(int i = 0 ; i < instructions.length; ++i) {
            if(instructions[i].length() == 0) {
                continue;
            }
            instrs[i] = new Instruction(instructions[i]);
            //registers.put(instrs[i].registerName, 0);
        }
        System.out.println(instrs.length);
        int count = 0;
        for(int i = 0 ; i < instrs.length; ++i) {
//            System.out.println(instrs[i]);
            if(instrs[i].perform(registers)) {
                count++;
            }
        }

        System.out.println("Actually performed " + count+ " operations.");
        int maxValue = Integer.MIN_VALUE;
        for(String regName : registers.keySet()) {
            if(registers.get(regName) > maxValue) {
                maxValue = registers.get(regName);
            }
        }
        return maxValue;
    }

    private enum IOP {
        inc, dec
    }
    static class Instruction {
        private String registerName;
        private IOP operation;
        private int changeValue;
        private Condition c;

        Instruction(String s) {
            s = s.trim();
            String[] objs = s.split("if");
            assert(objs.length == 2);
            String[] parts = objs[0].split(" ");
            assert(parts.length == 3);
            registerName = parts[0];
            String decStr = IOP.dec.toString();
            operation = parts[1].equals(IOP.dec.toString())  ? IOP.dec : IOP.inc;
            changeValue = Integer.parseInt(parts[2]);
            c = new Condition(objs[1]);
        }

        public String toString() {
            return "{registerName: " +registerName+ ", operation: " + operation.toString() + ", changeValue: " + changeValue +
                    ", c: " + c.toString() + "}";
        }

        public boolean perform(Map<String, Integer> registers) {
            registers.putIfAbsent(registerName,0);
            if(c.check(registers)) {

                int newValue = operation == IOP.inc ?
                        registers.get(registerName) + changeValue :
                            registers.get(registerName) - changeValue;
                registers.put(registerName, newValue);
                System.out.println(registers);
                return true;
            }
            return false;
        }
    }

    private enum EOP {
        GT, LT, GTE, LTE, E, NE
    }
    static class Condition {
        private String registerName;
        private EOP op;
        private int test;

        Condition(String s) {
            s = s.trim();
            String[] parts = s.split(" ");
            assert(parts.length == 3);
            registerName = parts[0];
            test = Integer.parseInt(parts[2]);
            switch(parts[1]) {
                case ">": op = EOP.GT; break;
                case "<": op = EOP.LT; break;
                case ">=": op = EOP.GTE; break;
                case "<=": op = EOP.LTE; break;
                case "==": op = EOP.E; break;
                case "!=": op = EOP.NE; break;
            }
        }

        public String toString() {
            return "{registerName: " + registerName + ", op: " + op.toString() + ", test: " + test + "}";
        }

        public boolean check(Map<String, Integer> registers) {
            int value = registers.getOrDefault(registerName, 0);
            switch(op) {
                case GT: return value > test;
                case LT: return value < test;
                case GTE: return value >= test;
                case LTE: return value <= test;
                case NE: return value != test;
                default: return value == test;

            }
        }
    }
}
