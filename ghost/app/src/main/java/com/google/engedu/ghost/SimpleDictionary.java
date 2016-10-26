package com.google.engedu.ghost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        //str1.compareTo(str2);
        //If str1 is lexicographically less than str2,
        // a negative number will be returned,
        // 0 if equal or a positive number if str1 is greater
        int l=0,r=words.size(),m;
        while(l<r){
            m=(l+r)/2;
            if(words.get(m).startsWith(prefix)){
                    return words.get(m);
            }
            else if(prefix.compareTo(words.get(m))<0){
                r=m-1;
            }
            else if(prefix.compareTo(words.get(m))>0){
                l=m+1;
            }
        }
        return "";
        /*for(String x:words){
            if(x.startsWith(prefix)){
                return x;
            }
        }*/
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        String selected = null;
        return selected;
    }
}
