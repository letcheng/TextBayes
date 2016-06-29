package com.ruyuapp.bayes;

import com.ruyuapp.counter.Counter;
import com.ruyuapp.utils.TextUtils;

import java.util.*;

/**
 * 朴素贝叶斯文本分类器
 *
 * @author <a href="mailto:letcheng@ruyuapp.com">letcheng</a>
 * @version create at 2016年3月29日 20:04
 */
public class NaiveBayesClassifier implements Classifier {

    private Counter counter;
    private TextModel classifierType;

    public NaiveBayesClassifier(Counter counter, TextModel classifierType) {
        this.classifierType = classifierType;
        this.counter = counter;
        if (classifierType == TextModel.POLYNOMIAL) { // 多项式模型
            counter.processWordsCount();
        }
    }

    /**
     * 计算后验概率
     * @param terms
     * @param category
     * @return
     */
    public Result calculatePosterior(String[] terms, String category, Map<String, Double> ranks) {
        double probability = 1.0D;

        for (String keyWord : terms) {
            Double prob = ConditionProb.instance().calculate(classifierType, counter, keyWord, category);
            probability += (ranks.get(keyWord) == null ? 1 : ranks.get(keyWord)) * Math.log(prob);
        }
        probability += Math.log(PriorProb.calculate(classifierType, counter, category)); // 防止小概率连乘损失精度
        return new Result(category, probability);
    }

    public Result calculatePosterior(String[] terms, String category) {

        double probability = 1.0D;

        for (String keyWord : terms) {
            Double prob = ConditionProb.instance().calculate(classifierType, counter, keyWord, category);
            probability += Math.log(prob);
        }
        probability += Math.log(PriorProb.calculate(classifierType, counter, category));
        return new Result(category, probability);
    }

    /**
     * 取后验概率最大者
     *
     * @param text
     * @return
     */
    public String classify(String text) {
        TextUtils processor = new TextUtils();
        String[] terms = processor.process(text);
        if (terms.length <= 1) {
            return "0";
        }
        String[] classes = counter.getCatagories();

        List<Result> result = new ArrayList<Result>();
        for (String category : classes) {
            Result classified = calculatePosterior(terms, category);
            result.add(classified);
        }
        Collections.sort(result, new Comparator<Result>() {
            @Override
            public int compare(Result o1, Result o2) {
                if (o1.getProb() > o2.getProb()) {
                    return -1;
                } else if (o1.getProb() == o2.getProb()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });

        return result.get(0).getCategory();
    }

}
