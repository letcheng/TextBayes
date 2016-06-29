package com.ruyuapp.utils;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.BaseAnalysis;
import org.ansj.splitWord.analysis.NlpAnalysis;

import java.io.File;
import java.util.*;

/**
 * 文本处理
 * 中文分词的包装
 * 其中封装了一个缓存器，防止重复的分词
 *
 * @author <a href="mailto:letcheng@ruyuapp.com">letcheng</a>
 * @version create at 2016年3月29日 20:04
 */
public class TextUtils {

    private Map<File, String[]> cache = new HashMap<File, String[]>();

    public TextUtils() {
    }

    public String[] process(String text) {

        //去掉链接
        text = text.replaceAll("<a.*?>", "");
        text = text.replaceAll("http://.*?</a>", "");
        text = text.replaceAll("</a>", "");

        List<Term> terms = BaseAnalysis.parse(text);

        List<String> words = new ArrayList<String>();
        for (Term t : terms) {
            words.add(t.getName());
        }
        return words.toArray(new String[]{});
    }

    public String[] process(File file) {
        if (cache.containsKey(file)) {
            return cache.get(file);
        }
        String[] terms = this.process(FileUtils.getFileText(file));
        cache.put(file, terms);
        return terms;
    }
}
