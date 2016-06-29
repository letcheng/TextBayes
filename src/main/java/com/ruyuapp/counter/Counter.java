package com.ruyuapp.counter;

/**
 * 文本统计模型
 * @author <a href="mailto:letcheng@ruyuapp.com">letcheng</a>
 * @version create at 2016年3月29日 20:04
 */
public interface Counter {

    /**
     * 获取分类标签集合
     * @return
     */
    String[] getCatagories();

    /**
     * 每个标签下包含单词words的文件数
     * @param word
     * @param catagory
     * @return
     */
    int countDocByWordAndCatagory(String word,String catagory);

    /**
     * 计算文档的总数
     * @return
     */
    int countDoc();

    /**
     * 计算某个标签下的文档总数
     * @param catagory
     * @return
     */
    int countDocByCatagory(String catagory);


    /**
     * 每个标签下单词word在各个文件中出现过的次数之和
     * @param word
     * @param category
     * @return
     */
    int countWordsByWordAndCatagory(String word, String category);

    /**
     * 统计单词的种类
     * @return
     */
    int countWordsType();


    /**
     * 统计单词总数
     * @return
     */
    int countWords();


    /**
     * 统计每个标签下的单词总数
     * @param category
     * @return
     */
    int countWordsOfCatagory(String category);


    /**
     *
     * 处理词频统计类的工作，适应于多项式模型
     *
     */
    void processWordsCount();

}
