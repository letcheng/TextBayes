package com.ruyuapp.utils;

import java.io.*;

/**
 * @author <a href="mailto:letcheng@ruyuapp.com">letcheng</a>
 * @version create at 2016年3月29日 20:04
 */
public class FileUtils {

    /**
     * 获取文本的内容
     * @param file
     * @return
     * @throws IOException
     */
    public static String getFileText(File file) {
        String line;
        StringBuilder sb = new StringBuilder();
        try {
            InputStreamReader in = new InputStreamReader(new FileInputStream(file),"UTF-8");
            BufferedReader reader = new BufferedReader(in);
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            in.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
