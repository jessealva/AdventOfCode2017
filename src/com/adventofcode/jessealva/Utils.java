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

/**
 * Created by jesusalva on 12/7/17.
 */
public class Utils {
    private static File RESOURCES_DIR = new File("resources");
    public static String[] readFile(String name) throws FileNotFoundException, IOException{

        File f= new File(RESOURCES_DIR, name);
        System.out.println("f: " + f.getAbsolutePath());
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(f), Charset.forName("UTF-8")));
        ArrayList<String> lines = new ArrayList();
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        if (lines != null) {
            return lines.toArray(new String[]{});
        } else {
            return new String[]{};
        }
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
