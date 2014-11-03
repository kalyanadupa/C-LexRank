/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lexrank;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lexrank.CosineSimilarity;
//import static lexrank.CosineSimilarity.similarity;

/**
 *
 * @author Kalyan
 */
public class driverProgram {
    public static double [][] similarity ;
    static HashMap<Integer,String> map = new HashMap<Integer,String>();
    static List<Integer> nodeWord = new ArrayList<Integer>();
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        CosineSimilarity cs1 = new CosineSimilarity();
        List<String> allStrings = new ArrayList<String>();
        //BufferedReader in = new BufferedReader(new FileReader("N05-1042.list"));
        PrintWriter out = new PrintWriter("String.nodes", "UTF-8");
        File[] allfiles = new File("new").listFiles();
        BufferedReader in = null;
        int idx = 0;
        String s = null;
        
        for (File f : allfiles) {
                in = new BufferedReader(new FileReader(f));
                while ((s = in.readLine()) != null) {
                    if(s.compareTo("") != 0){
                        allStrings.add(s);
                        String[] count = s.split(" ");
                        System.out.println("idx => "+ count.length);
                        map.put(idx, s);
                        nodeWord.add(count.length);
                        out.println(idx + " " + s);
                        idx++;
                    }
                }            
        }
        //map = sortHashMapByValuesD(map);
        
//        PrintWriter writer = new PrintWriter("StringNearNum.edges", "UTF-8");
//        for(int i =0;i < (allStrings.size());i++){
//            for(int j = i; j<(allStrings.size());j++){
//                double sim = cs1.CosineSimilarity_Score(allStrings.get(i), allStrings.get(j));
//                sim = 1 - sim;
//                if(sim == 1)
//                    sim = 0;
//                else if(sim == 0)
//                    sim = 0.01;
//                sim = sim*100;
//                int num = (int) sim;
//                if((i != j)&&(sim != 0)&&(!Double.isNaN(sim))&&(num != 0))
//                    writer.println(i+" "+j+" "+ num);
//                    //writer.println(i+" "+j+" "+ (float)sim);
//            }
//        }
        PrintWriter writer = new PrintWriter("StringPB20.edges", "UTF-8");        
        //PrintWriter writer2 = new PrintWriter("StringNum60.edges", "UTF-8");
        //similarity =  new double[allStrings.size()][allStrings.size()];
        similarity =  new double[allStrings.size()][allStrings.size()];
        int m = 0,n = 0;
        for(int i =0;i < (allStrings.size());i++){
            for(int j = 0; j<(allStrings.size());j++){
                double sim = cs1.CosineSimilarity_Score(allStrings.get(i), allStrings.get(j));
                //sim = sim*100;
                //int num = (int) sim;
                if((sim != 0)&&(!Double.isNaN(sim))&&(sim > 0.1)){
                    writer.println(i+" "+j+" "+sim);
//                    if(((i == 1)||(i == 4)||(i == 8))&&((j == 1)||(j == 4)||(j == 8))){
//                        if(i == 1)
//                            m = 0;
//                        if(i == 4)
//                            m = 1;
//                        if(i == 8)
//                            m = 2;
//                        if (j == 1) {
//                            n = 0;
//                        }
//                        if (j == 4) {
//                            n = 1;
//                        }
//                        if (j == 8) {
//                            n = 2;
//                        }
                        System.out.println("at "+i + "j "+j);
                        writer.println(i+" "+j+" "+sim);
                        similarity[i][j] = sim; 
                    //}    
                    //writer2.println(i+" "+j+" "+ num);
                    //writer.println(i+" "+j+" "+ (float)sim);
                }
                else{
                    //if(((i == 0)||(i == 3)||(i == 7))&&((j == 0)||(j == 3)||(j == 7))){
                        writer.println(i + " " + j + " " + 0.0);
                        similarity[i][j] = 0.00;
                    //}
                }      
            }
        }        
        writer.close();
        driverProgram dp = new driverProgram();
        dp.printRank(similarity);
        System.out.println("similarty.length = "+ similarity.length);
    }
    
    
    
    public void printRank(double similarity[][]){
        
        double t = 0.4;
        System.out.println("\n \n For t = 0.1");
        myAlgo(0.1,similarity);
        System.out.println("\n \n For t = 0.2");
        myAlgo(0.2,similarity);
        System.out.println("\n \n For t = 0.3");
        myAlgo(0.3,similarity);
        
    }
    void myAlgo(double t,double similarity[][]){
        
        List<DummyItem> items = new ArrayList<DummyItem>();
        for (int i = 0; i < similarity.length; ++i) {
            items.add(new DummyItem(i, similarity));
        }
        LexRankResults<DummyItem> results = LexRanker.rank(items, t, true);
        
        double max = results.scores.get(results.rankedResults.get(0));
        double[] sum = new double[similarity.length]; 

        
        for (int i = 0; i < similarity.length; ++i) {
            System.out.println(i+1 + " : " +
                               (results.scores.get(items.get(i)) / max));
            sum[i] = (results.scores.get(items.get(i)) / max);
        }
        System.out.println("\n ** printing scores:");
        printScore(sum);
        
        int[] degree = new int[similarity.length];
        
        for(int i = 0; i < similarity.length;i++){
            for(int j = 1;j<similarity.length;j++){
                if(similarity[i][j]> t){
                    sum[i] = similarity[i][j] + sum[i];
                    degree[i]++;
                }
            }
        }
        System.out.println("** The ranking is ** ");
        for(int i = 0; i< similarity.length;i++){
            int x = i+1;
            System.out.println("for "+ x + ": "+ sum[i]/degree[i] +"  sum : "+ sum[i]+ " degree : "+ degree[i]  );
        }
        System.out.println("\n ** printing scores of myAlgo:");
        printScore(sum);
    }
    
    
//GROUP[ 1 ][ 3 ]
//1 4 5
//GROUP[ 2 ][ 3 ]
//2 6 8
//GROUP[ 3 ][ 3 ]
//3 0 7    
    void printScore(double sum[]){
        
        if((sum[1] > sum [4]) && (sum[1] > sum[5]) ){
            System.out.println("among 1 4 5 => 1");
        }
        if((sum[4] > sum [1]) && (sum[4] > sum[5]) ){
            System.out.println("among 1 4 5 => 4");
        }
        if((sum[5] > sum [1]) && (sum[5] > sum[4]) ){
            System.out.println("among 1 4 5 => 5");
        }
        if((sum[2] > sum [6]) && (sum[2] > sum[8]) ){
            System.out.println("among 2 6 8 => 2");
        }
        if((sum[6] > sum [2]) && (sum[6] > sum[8]) ){
            System.out.println("among 2 6 8 => 6");
        }
        if((sum[8] > sum [2]) && (sum[8] > sum[6]) ){
            System.out.println("among 2 6 8 => 8");
        }
        if((sum[3] > sum [0]) && (sum[3] > sum[7]) ){
            System.out.println("among 3 0 7 => 3");
        }
        if((sum[0] > sum [3]) && (sum[0] > sum[7]) ){
            System.out.println("among 3 0 7 => 0");
        }
        if((sum[7] > sum [3]) && (sum[7] > sum[0]) ){
            System.out.println("among 3 0 7 => 7");
        }
    }
}
