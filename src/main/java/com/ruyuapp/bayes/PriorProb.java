package com.ruyuapp.bayes;

import com.ruyuapp.counter.Counter;

/**
 * 计算先验概率
 * @author <a href="mailto:letcheng@ruyuapp.com">letcheng</a>
 * @version create at 2016年3月29日 20:04
 */
public class PriorProb {

    public static Double calculate(TextModel classifierType, Counter counter, String catagory) {
        switch (classifierType) {
            case BERNOULLI:
                return 1.0d * counter.countDocByCatagory(catagory) / counter.countDoc();
            case POLYNOMIAL:
                return 1.0d * counter.countWordsOfCatagory(catagory) / counter.countWords();
        }
        return 1.0d;
    }
}
