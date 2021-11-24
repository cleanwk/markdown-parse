package com.example.temp;

import java.util.ArrayList;

public class defineAreaType {

    /**
     * 判断每一段 markdown 语法对应的 html 类型
     * @param 空
     * @return 空
     */
    private void defineAreaType() {
        // 定位代码区
        ArrayList<String> tempList = new ArrayList();
        ArrayList<String> tempType = new ArrayList();
        tempType.add("OTHER");
        tempList.add(" ");
        boolean codeBegin = false, codeEnd = false;
        for(int i = 1; i < mdList.size() - 1; i++){
            String line = mdList.get(i);
            if(line.length() > 2 && line.charAt(0) == '`' && line.charAt(1) == '`' && line.charAt(2) == '`') {
                // 进入代码区
                if(!codeBegin && !codeEnd) {
                    tempType.add("CODE_BEGIN");
                    tempList.add(" ");
                    codeBegin = true;
                }
                // 离开代码区
                else if(codeBegin && !codeEnd) {
                    tempType.add("CODE_END");
                    tempList.add(" ");
                    codeBegin = false;
                    codeEnd = false;
                }
                else {
                    tempType.add("OTHER");
                    tempList.add(line);
                }
            }
            else {
                tempType.add("OTHER");
                tempList.add(line);
            }
        }
        tempType.add("OTHER");
        tempList.add(" ");

        mdList = (ArrayList<String>)tempList.clone();
        mdListType = (ArrayList<String>)tempType.clone();
        tempList.clear();
        tempType.clear();

        // 定位其他区，注意代码区内无其他格式
        boolean isCodeArea = false;
        tempList.add(" ");
        tempType.add("OTHER");
        for(int i = 1; i < mdList.size() - 1; i++){
            String line = mdList.get(i);
            String lastLine = mdList.get(i - 1);
            String nextLine = mdList.get(i + 1);

            if(mdListType.get(i) == "CODE_BEGIN") {
                isCodeArea = true;
                tempList.add(line);
                tempType.add("CODE_BEGIN");
                continue;
            }
            if(mdListType.get(i) == "CODE_END") {
                isCodeArea = false;
                tempList.add(line);
                tempType.add("CODE_END");
                continue;
            }

            // 代码区不含其他格式
            if(!isCodeArea) {
                // 进入引用区
                if(line.length() > 2 && line.charAt(0) == '>' && lastLine.charAt(0) != '>' && nextLine.charAt(0) == '>') {
                    tempList.add(" ");
                    tempList.add(line);
                    tempType.add("QUOTE_BEGIN");
                    tempType.add("OTHER");
                }
                // 离开引用区
                else if(line.length() > 2 && line.charAt(0) == '>' && lastLine.charAt(0) == '>' && nextLine.charAt(0) != '>') {
                    tempList.add(line);
                    tempList.add(" ");
                    tempType.add("OTHER");
                    tempType.add("QUOTE_END");

                }
                // 单行引用区
                else if(line.length() > 2 && line.charAt(0) == '>' && lastLine.charAt(0) != '>' && nextLine.charAt(0) != '>') {
                    tempList.add(" ");
                    tempList.add(line);
                    tempList.add(" ");
                    tempType.add("QUOTE_BEGIN");
                    tempType.add("OTHER");
                    tempType.add("QUOTE_END");

                }
                // 进入无序列表区
                else if((line.charAt(0) == '-' && lastLine.charAt(0) != '-' && nextLine.charAt(0) == '-') ||
                        (line.charAt(0) == '+' && lastLine.charAt(0) != '+' && nextLine.charAt(0) == '+') ||
                        (line.charAt(0) == '*' && lastLine.charAt(0) != '*' && nextLine.charAt(0) == '*')){
                    tempList.add(" ");
                    tempList.add(line);
                    tempType.add("UNORDER_BEGIN");
                    tempType.add("OTHER");
                }
                // 离开无序列表区
                else if((line.charAt(0) == '-' && lastLine.charAt(0) == '-' && nextLine.charAt(0) != '-') ||
                        (line.charAt(0) == '+' && lastLine.charAt(0) == '+' && nextLine.charAt(0) != '+') ||
                        (line.charAt(0) == '*' && lastLine.charAt(0) == '*' && nextLine.charAt(0) != '*')){
                    tempList.add(line);
                    tempList.add(" ");
                    tempType.add("OTHER");
                    tempType.add("UNORDER_END");
                }
                // 单行无序列表区
                else if((line.charAt(0) == '-' && lastLine.charAt(0) != '-' && nextLine.charAt(0) != '-') ||
                        (line.charAt(0) == '+' && lastLine.charAt(0) != '+' && nextLine.charAt(0) != '+') ||
                        (line.charAt(0) == '*' && lastLine.charAt(0) != '*' && nextLine.charAt(0) != '*')){
                    tempList.add(" ");
                    tempList.add(line);
                    tempList.add(" ");
                    tempType.add("UNORDER_BEGIN");
                    tempType.add("OTHER");
                    tempType.add("UNORDER_END");
                }
                // 进入有序列表区
                else if((line.length() > 1 && (line.charAt(0) >= '1' || line.charAt(0) <= '9')  && (line.charAt(1) == '.')) &&
                        !(lastLine.length() > 1 && (lastLine.charAt(0) >= '1' || line.charAt(0) <= '9')  && (lastLine.charAt(1) == '.')) &&
                        (nextLine.length() > 1 && (nextLine.charAt(0) >= '1' || line.charAt(0) <= '9')  && (nextLine.charAt(1) == '.'))){
                    tempList.add(" ");
                    tempList.add(line);
                    tempType.add("ORDER_BEGIN");
                    tempType.add("OTHER");
                }
                // 离开有序列表区
                else if((line.length() > 1 && (line.charAt(0) >= '1' || line.charAt(0) <= '9')  && (line.charAt(1) == '.')) &&
                        (lastLine.length() > 1 && (lastLine.charAt(0) >= '1' || line.charAt(0) <= '9')  && (lastLine.charAt(1) == '.')) &&
                        !(nextLine.length() > 1 && (nextLine.charAt(0) >= '1' || line.charAt(0) <= '9')  && (nextLine.charAt(1) == '.'))){
                    tempList.add(line);
                    tempList.add(" ");
                    tempType.add("OTHER");
                    tempType.add("ORDER_END");
                }
                // 单行有序列表区
                else if((line.length() > 1 && (line.charAt(0) >= '1' || line.charAt(0) <= '9')  && (line.charAt(1) == '.')) &&
                        !(lastLine.length() > 1 && (lastLine.charAt(0) >= '1' || line.charAt(0) <= '9')  && (lastLine.charAt(1) == '.')) &&
                        !(nextLine.length() > 1 && (nextLine.charAt(0) >= '1' || line.charAt(0) <= '9')  && (nextLine.charAt(1) == '.'))){
                    tempList.add(" ");
                    tempList.add(line);
                    tempList.add(" ");
                    tempType.add("ORDER_BEGIN");
                    tempType.add("OTHER");
                    tempType.add("ORDER_END");
                }
                // 其他
                else {
                    tempList.add(line);
                    tempType.add("OTHER");
                }
            }
            else {
                tempList.add(line);
                tempType.add("OTHER");
            }
        }
        tempList.add(" ");
        tempType.add("OTHER");

        mdList = (ArrayList<String>)tempList.clone();
        mdListType = (ArrayList<String>)tempType.clone();
        tempList.clear();
        tempType.clear();
    }
}
