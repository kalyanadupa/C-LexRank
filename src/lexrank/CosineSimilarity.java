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
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Kalyan
 */
public class CosineSimilarity {
    
    public class values {

        int val1;
        int val2;

        values(int v1, int v2) {
            this.val1 = v1;
            this.val2 = v2;
        }

        public void Update_VAl(int v1, int v2) {
            this.val1 = v1;
            this.val2 = v2;
        }
    }

    public double CosineSimilarity_Score(String Text1, String Text2) {
        double sim_score = 0.0000000;
        
        String[] word_seq_text1 = Text1.split(" ");
        String[] word_seq_text2 = Text2.split(" ");
        Hashtable<String, values> word_freq_vector = new Hashtable<String, CosineSimilarity.values>();
        LinkedList<String> Distinct_words_text_1_2 = new LinkedList<String>();

        for (int i = 0; i < word_seq_text1.length; i++) {
            String tmp_wd = word_seq_text1[i].trim();
            if (tmp_wd.length() > 0) {
                if (word_freq_vector.containsKey(tmp_wd)) {
                    values vals1 = word_freq_vector.get(tmp_wd);
                    int freq1 = vals1.val1 + 1;
                    int freq2 = vals1.val2;
                    vals1.Update_VAl(freq1, freq2);
                    word_freq_vector.put(tmp_wd, vals1);
                } else {
                    values vals1 = new values(1, 0);
                    word_freq_vector.put(tmp_wd, vals1);
                    Distinct_words_text_1_2.add(tmp_wd);
                }
            }
        }

        
        for (int i = 0; i < word_seq_text2.length; i++) {
            String tmp_wd = word_seq_text2[i].trim();
            if (tmp_wd.length() > 0) {
                if (word_freq_vector.containsKey(tmp_wd)) {
                    values vals1 = word_freq_vector.get(tmp_wd);
                    int freq1 = vals1.val1;
                    int freq2 = vals1.val2 + 1;
                    vals1.Update_VAl(freq1, freq2);
                    word_freq_vector.put(tmp_wd, vals1);
                } else {
                    values vals1 = new values(0, 1);
                    word_freq_vector.put(tmp_wd, vals1);
                    Distinct_words_text_1_2.add(tmp_wd);
                }
            }
        }

        
        double VectAB = 0.0000000;
        double VectA_Sq = 0.0000000;
        double VectB_Sq = 0.0000000;

        for (int i = 0; i < Distinct_words_text_1_2.size(); i++) {
            values vals12 = word_freq_vector.get(Distinct_words_text_1_2.get(i));

            double freq1 = (double) vals12.val1;
            double freq2 = (double) vals12.val2;
            

            VectAB = VectAB + (freq1 * freq2);

            VectA_Sq = VectA_Sq + freq1 * freq1;
            VectB_Sq = VectB_Sq + freq2 * freq2;
        }
        
        sim_score = ((VectAB) / (Math.sqrt(VectA_Sq) * Math.sqrt(VectB_Sq)));

        return (sim_score);
    }
    
    public static LinkedHashMap sortHashMapByValuesD(HashMap passedMap) {
        List mapKeys = new ArrayList(passedMap.keySet());
        List mapValues = new ArrayList(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap sortedMap = new LinkedHashMap();

        Iterator valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Object val = valueIt.next();
            Iterator keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Object key = keyIt.next();
                String comp1 = passedMap.get(key).toString();
                String comp2 = val.toString();

                if (comp1.equals(comp2)) {
                    passedMap.remove(key);
                    mapKeys.remove(key);
                    sortedMap.put((String) key, (Double) val);
                    break;
                }
            }

        }
        return sortedMap;
    }    

}
