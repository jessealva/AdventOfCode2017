package com.adventofcode.jessealva;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by jesusalva on 12/7/17.
 */
public class Utils {
    private static File RESOURCES_DIR = new File("resources");
    public static String[] readFile(String name) throws FileNotFoundException{

        File f= new File(RESOURCES_DIR, name);
        System.out.println("f: " + f.getAbsolutePath());
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(f), Charset.forName("UTF-8")));
        ArrayList<String> lines = new ArrayList();
        String line;
        try{
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }catch(IOException ioe){

        } finally{
            try {
                br.close();
            } catch(IOException ioe){}
        }

        if (lines != null) {
            return lines.toArray(new String[]{});
        } else {
            return new String[]{};
        }
    }

    public static List<String[]> fileWithLinesSplit(String file) {
        List<String[]> ret = null;
        try {
            String[] lines = readFile(file);
            ret = new ArrayList<>(lines.length);
            for(int i = 0 ; i < lines.length; ++i) {
                ret.add(lines[i].split(" "));
            }
        } catch(IOException ioe) {

        }
        return ret;
    }

    public static int[] getIntArrFromStringArr(String[] a) {
        if (a == null) {
            return null;
        }
        int[] tmp = new int[a.length];
        for (int i = 0 ; i < a.length; ++i) {
            tmp[i] = Integer.parseInt(a[i]);
        }
        return tmp;
    }
}
