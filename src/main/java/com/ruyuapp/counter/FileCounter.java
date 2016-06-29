package com.ruyuapp.counter;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 以文件管理器的训练文本
 *
 * 结构如下所示：
 *
 * 父目录--> 子目录（标签名） --> 文本文件(utf8编码)
 * @author <a href="mailto:letcheng@ruyuapp.com">letcheng</a>
 * @version create at 2016年3月29日 20:04
 */
public class FileCounter extends AbstractCounter{

	//训练文本的目录
	private File dir;

	public FileCounter(File dir,int fromIndex1,int toIndex1,int fromIndex2,int toIndex2) {
        super(fromIndex1,toIndex1,fromIndex2,toIndex2);
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            throw new IllegalArgumentException();
        }else{
            this.dir = dir;
        }
        super.catagories = dir.list();
	}

    public FileCounter(File dir){
        super();
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            throw new IllegalArgumentException();
        }else{
            this.dir = dir;
        }
        super.catagories = dir.list();
    }

    public void processWordsCount(){
        wordsCountOfCatagory = new HashMap<String, Integer>();
        for (String category : catagories) {
            try {
                File[] files = listFilesByCatagory(category);
                Integer wordCount = 0;
                for (File file : files) {
                    String[] terms = processor.process(file);
                    for(String term : terms){
                        words.add(term);
                    }
                    wordCount += terms.length;
                }
                wordsCountOfCatagory.put(category, wordCount);
                wordsCount += wordCount;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 查找某个类标签下的文件
     * @param catagory
     * @return
     */
    public File[] listFilesByCatagory(String catagory){

        File catagoryDir = new File(dir.getPath() + File.separator+catagory);
        List<File> files = new ArrayList<File>();

        if(toIndex2==-1)
            this.toIndex2 = catagoryDir.listFiles().length;

        if(fromIndex2 > toIndex1 && fromIndex1 >=0 && toIndex2 < catagoryDir.listFiles().length){

            if(toIndex1>fromIndex1){
                files.addAll(Arrays.asList(catagoryDir.listFiles()).subList(fromIndex1-1,toIndex1-1));
            }
            if(toIndex2>fromIndex2){
                files.addAll(Arrays.asList(catagoryDir.listFiles()).subList(fromIndex2-1,toIndex2-1));
            }
            return files.toArray(new File[]{});
        }
        return catagoryDir.listFiles();

    }

    /**
     * 计算某个标签下的文档总数
     * @param catagory
     * @return
     */
	public int countDocByCatagory(String catagory) {
		File catagoryDir = new File(dir.getPath() + File.separator+ catagory);
		return catagoryDir.list().length;
	}

    /**
     * 每个标签下包含单词word的文件数
     * @param word
     * @param catagory
     * @return
     */
    public int countDocByWordAndCatagory(String word,String catagory){
        if (word == null || word.equals("")) {
            return 0;
        }
        Integer count = 0;
        File[] files = listFilesByCatagory(catagory);
        for (File file : files) {
            String[] terms = processor.process(file);
            for (String term : terms) {
                if (word.equals(term)) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    /**
     * 每个标签下单词word在各个文件中出现过的次数之和
     * @param word
     * @param category
     * @return
     */
	public int countWordsByWordAndCatagory(String word, String category) {
		if (word == null || word.equals("")) {
			return 0;
		}
		int count = 0;

		File[] files = listFilesByCatagory(category);
		for (File file : files) {
			String[] terms = processor.process(file);
			for (String term : terms) {
				if (word.equals(term)) {
					count++;
				}
			}
		}
		return count;
	}


}
