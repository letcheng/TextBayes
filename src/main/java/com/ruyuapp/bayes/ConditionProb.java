package com.ruyuapp.bayes;

import com.ruyuapp.counter.Counter;

/**
 * 类条件概率
 *
 * @author <a href="mailto:letcheng@ruyuapp.com">letcheng</a>
 * @version create at 2016年3月29日 20:04
 */
public class ConditionProb {

    private static ConditionProb instance = null;

    ConditionProbCache probCache = new ConditionProbCache();

    private ConditionProb() {
    }

    public static ConditionProb instance() {
        if (instance == null) {
            instance = new ConditionProb();
        }
        return instance;
    }

    public double calculate(TextModel classifierType, Counter counter, String keyWord, String catagory) {

        if (probCache.contains(keyWord, catagory)) {
            return probCache.get(keyWord, catagory);
        }
        Double prob = 1.0d;
        switch (classifierType) {
            case BERNOULLI:
                prob = 1.0d * (1 + counter.countDocByWordAndCatagory(keyWord, catagory)) / (counter.countDocByCatagory(catagory) + 2);
                break;
            case POLYNOMIAL:
                prob = 1.0d * (1 + counter.countWordsByWordAndCatagory(keyWord, catagory)) / (counter.countWords() + counter.countWordsType());
        }
        probCache.add(keyWord, catagory, prob);
        return prob;
    }

}
