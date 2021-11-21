package com.example;

import java.io.File;

public interface Parse {
    /**
     * 传入markdown文档，解析为html页面
     * @param document
     * @return
     */
    File parseMarkDown(File document);

}
