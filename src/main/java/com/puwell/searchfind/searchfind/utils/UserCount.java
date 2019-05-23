package com.puwell.searchfind.searchfind.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserCount {
    public static void main(String[] args) throws IOException {
        Map<String,Integer> map=new HashMap();
        Map<String,Integer> companyCount =new HashMap<>();
        FileReader file = new FileReader("C:\\Users\\hxl\\Desktop\\userLogin.txt");
        BufferedReader bufferedReader = new BufferedReader(file);
        String str;
        while ((str=bufferedReader.readLine())!=null){
            int i=0;
            int j=0;
            for(String s:map.keySet()){
                if(str.equals(s)){
                    map.put(str,map.get(s)+1);
                    i=1;
                }
            }
            if(i==0){
                map.put(str,1);
            }
            if(str.indexOf("#")!=-1){
               String companyName =str.substring(str.indexOf("#")+1,str.lastIndexOf("#"));
                for(String s:companyCount.keySet()){
                    if(companyName.equals(s)){
                        companyCount.put(companyName,companyCount.get(s)+1);
                        j=1;
                    }
                }
                if(j==0){
                    companyCount.put(companyName,1);
                }
            }
        }
        FileWriter fileWriter = new FileWriter("C:\\Users\\hxl\\Desktop\\UserLoginCount.txt");
        BufferedWriter bw = new BufferedWriter(fileWriter);
        for (String s:map.keySet()){
            System.out.println(s+"   "+map.get(s));
            bw.write(s+"         "+map.get(s));
            bw.newLine();
            bw.flush();
        }
        FileWriter fileWriter2 = new FileWriter("C:\\Users\\hxl\\Desktop\\companyCount.txt");
        BufferedWriter bw2 = new BufferedWriter(fileWriter2);
        for (String s:companyCount.keySet()){
            System.out.println(s+"   "+companyCount.get(s));
            bw2.write(s+"     "+companyCount.get(s));
            bw2.newLine();
            bw2.flush();
        }
        fileWriter2.close();
        bw2.close();
        bw.close();
        fileWriter.close();
        bufferedReader.close();
        file.close();
    }
}
