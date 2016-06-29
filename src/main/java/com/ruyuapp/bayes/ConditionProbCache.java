package com.ruyuapp.bayes;

import java.util.HashMap;
import java.util.Map;

/**
 * 类条件概率
 *
 * @author <a href="mailto:letcheng@ruyuapp.com">letcheng</a>
 * @version create at 2016年3月29日 20:04
 */
public class ConditionProbCache {

    Map<String, Map<String, Double>> keyWordProbMap = new HashMap<String, Map<String, Double>>();

    public void add(String keyWord, String category, Double prob) {
        Map<String, Double> probMap;
        if (!keyWordProbMap.containsKey(keyWord)) {
            probMap = new HashMap<String, Double>();
            keyWordProbMap.put(keyWord, probMap);
        } else {
            probMap = keyWordProbMap.get(keyWord);
        }
        probMap.put(category, prob);
    }

    public Double get(String keyWord, String category) {
        if (this.contains(keyWord, category)) {
            return keyWordProbMap.get(category).get(keyWord);
        }
        return null;
    }

    public boolean contains(String keyWord, String category) {
        Map<String, Double> probMap = keyWordProbMap.get(category);
        if (probMap != null && probMap.get(keyWord) != null) {
            return true;
        }
        return false;
    }
}
