package com.ruyuapp.bayes;

/**
 *
 * 分类结果的表示，某个实例，属于某个分类的概率，
 * 实现Comparable接口，方便排序计算出最可能的分类
 *
 * @author <a href="mailto:letcheng@ruyuapp.com">letcheng</a>
 * @version create at 2016年3月29日 20:04
 */
public class Result implements Comparable<Result> {

	public double prob;
	public String category;

	public Result(String category, Double probability) {
		this.prob = probability;
		this.category = category;
	}

    public double getProb() {
        return prob;
    }

    public void setProb(double prob) {
        this.prob = prob;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int compareTo(Result o) {
        return this.prob > o.prob ? 1 : -1;
    }

    @Override
	public String toString() {
		return "category : " + category + " >>> with prob " + prob;
	}
}
