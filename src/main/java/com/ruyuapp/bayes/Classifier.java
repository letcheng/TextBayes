package com.ruyuapp.bayes;

/**
 * 分类器
 * @author <a href="mailto:letcheng@ruyuapp.com">letcheng</a>
 * @version create at 2016年3月29日 20:04
 */
public interface Classifier {
    String classify(String text);
}
