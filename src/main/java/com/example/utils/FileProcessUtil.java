package com.example.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileProcessUtil {
    /**
     * 将文本解析为List<String>
     * @param file
     * @return
     */
    public static List<String> toStringList(File file){
        List<String> mdList = new ArrayList();
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;


        while (true){
            try {
                reader = new InputStreamReader(new FileInputStream(file));
                bufferedReader = new BufferedReader(reader);
                String line = "";
                while (!((line = bufferedReader.readLine()) != null)) break;{
                    mdList.add(line);
                }
            } catch (IOException e) {
                System.out.println("------>>>解析markdown 文本出错");
                e.printStackTrace();
            }finally {
                if (reader!=null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        System.out.println("------>>> reader close failed！");
                        e.printStackTrace();
                    }
                }

                if (bufferedReader!=null){
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        System.out.println("------>>> bufferedReader close failed！");
                        e.printStackTrace();
                    }
                }

                return mdList;
            }

        }


    }

    /**
     * 将标记好的字符串list转换成html文件输出
     * @param list
     * @return
     */
    public static File toStringList(List list){

        return null;
    }
}
