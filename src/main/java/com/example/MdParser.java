package com.example;

import com.example.common.SyntaxType;
import com.example.utils.FileProcessUtil;
import lombok.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
@Data
public class MdParser implements Parse{

    public MdParser(File markDownFile) {
        this.markDownFile = markDownFile;
    }

    private File markDownFile;

    private List<String> mdList = new ArrayList();

    private File htmlFile;

    private List<String> mdListType = new ArrayList();

    @Override
    public File parseMarkDown(File document) {

        //将md文档按照行 转为string list
        mdList = FileProcessUtil.toStringList(document);
        //确定多行语句区块,进行标记
        ArrayList<String> tempList = new ArrayList();
        ArrayList<String> typeList = new ArrayList();
        typeList.add("OTHER");
        tempList.add(" ");

        boolean codeBegin = false,codeEnd = false;
        for (int i = 1; i < mdList.size() -1; i++) {
            String line = mdList.get(i);
            if (line.length()>2 && "```".equals(line.substring(0,3))){
                //进入代码区
                if(!codeBegin && !codeEnd){
                    typeList.add(SyntaxType.CODE_BEGIN);
                    tempList.add(" ");
                    codeBegin = true;
                //离开代码区
                }else if (codeBegin && !codeEnd){
                    typeList.add(SyntaxType.CODE_END);
                    tempList.add(" ");
                    codeBegin = false;
                    codeEnd = false;
                }else{
                    tempList.add(line);
                    typeList.add(SyntaxType.OTHER);
                }
            }else {
                typeList.add(SyntaxType.OTHER);
                tempList.add(line);
            }
        }

        //第一遍扫描，标记代码块区域完成

        //
        typeList.add(SyntaxType.OTHER);
        tempList.add(" ");
        mdList = (ArrayList<String>)tempList.clone();
        mdListType = (ArrayList<String>)typeList.clone();

        tempList.clear();
        typeList.clear();

        //定位其他区域
        boolean isCodeArea = false;
        //确定单行语句区块进行标记
        tempList.add(" ");
        typeList.add(SyntaxType.OTHER);

        for (int i = 1; i < mdList.size() -1; i++) {
            String line = mdList.get(i);
            String lastLine = mdList.get(i - 1);
            String nextLine = mdList.get(i + 1);

            if(SyntaxType.CODE_BEGIN.equals(mdListType.get(i))) {
                isCodeArea = true;
                tempList.add(line);
                typeList.add("CODE_BEGIN");
                continue;
            }

            if(SyntaxType.CODE_END.equals(mdListType.get(i))) {
                isCodeArea = false;
                tempList.add(line);
                typeList.add(SyntaxType.CODE_END);
                continue;
            }
            if (!isCodeArea){
                //进入引用区条件
                if(line.length() > 2 && line.charAt(0) == '>' && lastLine.charAt(0) != '>' && nextLine.charAt(0) == '>') {
                    tempList.add(" ");
                    tempList.add(line);
                    typeList.add(SyntaxType.QUOTE_END);
                    typeList.add(SyntaxType.OTHER);
                }
                // 离开引用区
                else if(line.length() > 2 && line.charAt(0) == '>' && lastLine.charAt(0) == '>' && nextLine.charAt(0) != '>') {
                    tempList.add(line);
                    tempList.add(" ");
                    typeList.add(SyntaxType.OTHER);
                    typeList.add(SyntaxType.QUOTE_END);
                }

            }

        }
        //输出为html文档

        return null;
    }
}











































