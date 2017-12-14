package com.adventofcode.jessealva;

/**
 * Created by jesusalva on 12/3/17.
 */
public class Day3 {
    private static final int num1 = 1;
    private static final int num2 = 12;
    private static final int num3 = 23;
    private static final int num4 = 26;
    private static final int num5 = 1024;

    private static final int finalNum = 347991;

    public static void main(String[] args) {
        System.out.println("Distance between 1 and " + num1 + " is: " + getDistanceP1(num1) + " and should be 0");
        System.out.println("Distance between 1 and " + num2 + " is: " + getDistanceP1(num2) + " and should be 3");
        System.out.println("Distance between 1 and " + num3 + " is: " + getDistanceP1(num3) + " and should be 2");
        System.out.println("Distance between 1 and " + num4 + " is: " + getDistanceP1(num4) + " and should be 5");
        System.out.println("Distance between 1 and " + num5 + " is: " + getDistanceP1(num5) + " and should be 31");
        System.out.println("Distance between 1 and " + finalNum + " is: " + getDistanceP1(finalNum));

        System.out.println();

        System.out.println("Distance between 1 and " + num1 + " is: " + getDistanceP2(num1) + " and should be 0");
        System.out.println("Distance between 1 and " + num2 + " is: " + getDistanceP2(num2) + " and should be 3");
        System.out.println("Distance between 1 and " + num3 + " is: " + getDistanceP2(num3) + " and should be 2");
        System.out.println("Distance between 1 and " + num4 + " is: " + getDistanceP2(num4) + " and should be 5");
        System.out.println("Distance between 1 and " + num5 + " is: " + getDistanceP2(num5) + " and should be 31");
        System.out.println("Distance between 1 and " + finalNum + " is: " + getDistanceP2(finalNum));
    }

    //TODO finish this.
//    public static void printMatrix(int n){
//        int md = getMatrixDimension(n);
//        int middle = md/2 +®
//        int[][] m = new int[md][md];
//        int row = md-1;
//        int column = md-1;
//        int currCol = column;
//        int currRow = row;
//        boolean updateCol = true;
//        for(int i = md * md; i >= 0; --i){
//            m[row][column] = i;
//            if (updateCol && column > 0) {
//                column--;
//            } else {
//                row--;
//            }
//
//        }
//    }

    private static int getDistanceP1(int n) {
        if (n == 1) {
            return 0;
        }
        int md = getMatrixDimension(n);
        /*
        Thoughts here are that
        1. we know the dimensions
        2. The number we're checking will always be on the outermost ring
        3. The inner ring will thus be full.
        4. Outer ring will be filled up from position dimension (x-1, y-1) and that number
           will be innerRing sideDimension^2 +1
         */
        int innerRingTotal = (int) Math.pow(md-2, 2);
        int outerRingLen = n - innerRingTotal; //This is how many positions we have to move on the outer ring to find ours.
        int offset = md -1;
        int offsetsRemovd = 0;
        while (outerRingLen > (offset)){
            outerRingLen -= offset;
            offsetsRemovd++;
        }
//        System.out.println("For n=" + n + ", i think dimension is " + md + "x" + md);
        //Find x,y coordinates where we're at.
        int row=0, column = 0;
        switch (offsetsRemovd) {
            case 0:
                //We're on the right side of the matrix. We started at x-1, y-1
                column = md -1; //we're on the right side.
                row = (md -1) - outerRingLen;
                break;
            case 1:
                //We're on the top side of the matrix
                row = 0;
                column = (md-1) - outerRingLen;
                break;
            case 2:
                //We're on the left side of the matrix
                column = 0;
                row = (md-1) - outerRingLen;
                break;
            case 3:
                //We're on the bottom
                row = md-1;
                column = outerRingLen;
        }
//        System.out.println("For n=" + n + " I think we're at x,y: (" + row + ", " + column + ")");
        int middle = md/2 ;
//        System.out.println("Middle is (" + middle + ", " + middle + ")");
        int distance = Math.abs(middle - row) + Math.abs(middle - column);
        return distance;
    }

    private static int getDistanceP2(int n) {
        if (n == 1) {
            return 0;
        }
        int md = getMatrixDimension(n);
        /*
        Thoughts here are that
        1. we know the dimensions
        2. The number we're checking will always be on the outermost ring
        3. The inner ring will thus be full.
        4. Outer ring will be filled up from position dimension (x-1, y-1) and that number
           will be innerRing sideDimension^2 +1
         */
        int innerRingTotal = (int) Math.pow(md-2, 2);
        int outerRingLen = n - innerRingTotal; //This is how many positions we have to move on the outer ring to find ours.
        int offset = md -1;
        int offsetsRemovd = 0;
        while (outerRingLen > (offset)){
            outerRingLen -= offset;
            offsetsRemovd++;
        }
//        System.out.println("For n=" + n + ", i think dimension is " + md + "x" + md);
        //Find x,y coordinates where we're at.
        int row=0, column = 0;
        switch (offsetsRemovd) {
            case 0:
                //We're on the right side of the matrix. We started at x-1, y-1
                column = md -1; //we're on the right side.
                row = (md -1) - outerRingLen;
                break;
            case 1:
                //We're on the top side of the matrix
                row = 0;
                column = (md-1) - outerRingLen;
                break;
            case 2:
                //We're on the left side of the matrix
                column = 0;
                row = (md-1) - outerRingLen;
                break;
            case 3:
                //We're on the bottom
                row = md-1;
                column = outerRingLen;
        }
//        System.out.println("For n=" + n + " I think we're at x,y: (" + row + ", " + column + ")");
        int middle = md/2 ;
//        System.out.println("Middle is (" + middle + ", " + middle + ")");
        int distance = Math.abs(middle - row) + Math.abs(middle - column);
        return distance;
    }

    /*
    Matrix dimensions should always be odd, so filling in can spiral around a center point.
    This returns one side of an square matrix, where each side MUST be odd.
     ex: This will return 3 since its a 3 x3 matrix
     5 4 3
     6 1 2
     7 8 9

     For an input of 11, the matrix it belongs too would be this, so function would return 5  for a 5x5 matrix
     17 16 15 14 13
     18  5  4  3 12
     19  6  1  2 11
     20  7  8  9 10
     21 22 23 24 25
     */
    public static int getMatrixDimension(int n){
        double sqRt = Math.sqrt(n);
        long dim = Math.round(sqRt);
        if (dim < sqRt) { dim++; }
        if (dim % 2 == 0) {
            dim++;
        }
        return (int)dim;
    }
}
