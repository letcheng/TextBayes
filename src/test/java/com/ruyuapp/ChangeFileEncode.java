package com.ruyuapp;

import java.io.*;


/**
 * 批量处理文件编码
 * 搜狗实验室提供数据集是GBK编码
 * 这里我统一使用UTF-8编码
 *
 * @author <a href="mailto:letcheng@ruyuapp.com">letcheng</a>
 * @version create at 2016年3月29日 20:04
 */
public class ChangeFileEncode {

    private String read(File file, String encoding) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), encoding));

            String string = "";
            String str = "";
            while ((str = in.readLine()) != null) {
                string += str + "\n";
            }
            in.close();
            return string;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    private void write(File file, String encoding, String str) {
        try {
            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), encoding));
            out.write(str);
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void main(String args[]) throws IOException {

        ChangeFileEncode changeFileEncode = new ChangeFileEncode();


        File root = new File(ChangeFileEncode.class.getResource("/Sogou").getPath());

        for (File file : root.listFiles()) {
            int size = 0;
            File new_file = new File("E:" + File.separator + "weibotopic" + File.separator + file.getName());
            if (!new_file.exists()) new_file.mkdir();

            for (File f : file.listFiles()) {
                System.out.println(file.getName() + ":" + size++);
                new_file = new File("E:" + File.separator + "weibotopic" + File.separator + file.getName() + File.separator + size + ".utf8.txt");
                if (!new_file.exists()) new_file.createNewFile();
                changeFileEncode.write(new_file, "utf-8", changeFileEncode.read(f, "GBK"));
            }
        }

    }


}
