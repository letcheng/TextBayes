package com.ruyuapp.counter;

import com.ruyuapp.utils.TextUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:letcheng@ruyuapp.com">letcheng</a>
 * @version create at 2016年3月29日 20:04
 */
public abstract class AbstractCounter implements Counter {

    protected TextUtils processor = null;

    protected int fromIndex1 = -1;
    protected int toIndex1 = -1;

    protected int fromIndex2 = -1;
    protected int toIndex2 = -1;

    //标签，即训练文本的子目录名
    protected String[] catagories;

    // 单词总数
    protected int wordsCount = 0;

    //用来计算words的种数
    protected Set<String> words = new HashSet<String>();

    // 每个标签下单词总数
    protected Map<String, Integer> wordsCountOfCatagory = null;

    public AbstractCounter(int fromIndex1,int toIndex1,int fromIndex2,int toIndex2){
        this();
        this.fromIndex1 = fromIndex1;
        this.toIndex1 = toIndex1;
        this.fromIndex2 = fromIndex2;
        this.toIndex2 = toIndex2;
    }

    public AbstractCounter(){
        this.processor = new TextUtils();
    }

    public String[] getCatagories() {
        return catagories;
    }

    /**
     * 计算文档的总数
     * @return
     */
    public int countDoc() {
        int total = 0;
        for (String catagory : catagories) {
            total += countDocByCatagory(catagory);
        }
        return total;
    }

    /**
     * 统计单词总数
     * @return
     */
    public int countWords() {
        return wordsCount;
    }

    /**
     * 统计单词的种类
     * @return
     */
    public int countWordsType(){
        return words.size();
    }

    /**
     * 统计每个标签下的单词总数
     * @param category
     * @return
     */
    public int countWordsOfCatagory(String category) {
        return wordsCountOfCatagory.get(category);
    }


}
