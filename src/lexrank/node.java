/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cosinesimilarity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Kalyan
 */
public class node {
    int nNode;
    int nWords;
    String value;
    List<int []> resultModules = new ArrayList<int[]>();
    static HashMap<Integer,String> map = new HashMap<Integer,String>();
    static List<Integer> nodeWord = new ArrayList<Integer>();
    
    public void readData() throws FileNotFoundException, IOException{
        File[] allfiles = new File("D1401_TRAIN").listFiles();
        BufferedReader in = null;
        int idx = 0;
        String s = null;
        
        for (File f : allfiles) {
                in = new BufferedReader(new FileReader(f));
                while ((s = in.readLine()) != null) {
                    if(s.compareTo("") != 0){
                        String[] count = s.split(" ");
                        map.put(idx, s);
                        nodeWord.add(count.length);
                        idx++;
                    }
                }            
        }        
    }
    
    
    public void readtp() throws FileNotFoundException, IOException{
        BufferedReader in = null;
        in = new BufferedReader(new FileReader("tp"));
        String s = null;
        while ((s = in.readLine()) != null) {
            if (s.compareTo("") != 0) {
                String[] bits = s.split(" ");
                if(bits[0].compareTo("#module") != 0){
                    int[] nodeArray = new int[bits.length];
                    int i = 0;
                    for(String x : bits){
                        nodeArray[i] = Integer.valueOf(bits[i]);
                        i++;
                    }
                    resultModules.add(nodeArray);
                }
                //map.put(count.length, s);
            }
        }        
        
    }
    
    public void writetp(int limit){
        if (limit < resultModules.size()) {
            for (int i = 0; i < limit; i++) {
                int p = 0, index = 0, j = 0;
                for (int[] xArray : resultModules) {
                    if (xArray.length > p) {
                        p = xArray.length;
                        index = j;
                    }
                    j++;
                }
                int[] selectedArray = resultModules.get(index);
                p = 0;
                j = 0;
                index = 0;
                for (int xNode : selectedArray) {
                    if (nodeWord.get(xNode) > p) {
                        p = nodeWord.get(xNode);
                        index = xNode;
                    }
                }
                System.out.println(map.get(index));
                resultModules.remove(selectedArray);
            }
        }
    }
    public static void main(String[] args) throws IOException{
        System.out.println("please enter the limt");
        Scanner in1 = new Scanner(System.in); 
        int limit = in1.nextInt();
        node nd = new node();
        nd.readData();
        nd.readtp();
        nd.writetp(limit);
        
        
    }
}
