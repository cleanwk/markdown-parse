package com.example;

import com.example.common.SyntaxTypeEnum;
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

    private List mdList = new ArrayList<String>();

    private File htmlFile;

    private List originList = new ArrayList<String>();



    @Override
    public File parseMarkDown(File document) {

        //将md文档按照行 转为string list
        mdList = FileProcessUtil.toStringList(document);
        //确定多行语句区块,进行标记

        //确定单行语句区块进行标记

        //输出为html文档

        return null;
    }
}
