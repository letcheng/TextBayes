package com.ruyuapp.eval;

import com.ruyuapp.bayes.Classifier;
import com.ruyuapp.bayes.TextModel;
import com.ruyuapp.bayes.NaiveBayesClassifier;
import com.ruyuapp.counter.Counter;
import com.ruyuapp.counter.CounterType;
import com.ruyuapp.counter.CounterFactory;
import com.ruyuapp.counter.MySQLCounter;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


/**
 * 分类器性能测试
 *
 * @author <a href="mailto:letcheng@ruyuapp.com">letcheng</a>
 * @version create at 2016年3月29日 20:04
 */
public class MySQLCrossValidationEval {

    //存储分类结果 <catagory,result>->file
    Table<String, String, String> table = HashBasedTable.create();

    Map<String, Integer> tp = new HashMap<String, Integer>();

    Counter counter = null;

    private TextModel classifierType;

    public MySQLCrossValidationEval(TextModel classifierType) {
        this.classifierType = classifierType;
    }

    /**
     * 10-折交叉验证测试
     */
    public void eval() {

        for (int j = 0; j < 10; j++) { //循环10次，1个做测试集，其他9个做训练集

            tp.clear();
//			counter = CounterFactory.getMySQLTypeInstance(1, 20 * j + 1, 20 * j + 21, -1);

            counter = CounterFactory.getInstance(CounterType.MYSQL);

            String[] catagories = counter.getCatagories();

            Classifier classifier = new NaiveBayesClassifier(counter, classifierType);

            table.clear();
            for (String category : catagories) {
                try {
                    String[] records = ((MySQLCounter) counter).listRecordsByCatagory(category);
                    for (int i = 20 * j; i < 20 * j + 20; i++) {
                        System.out.println((j + 1) + "折=>" + category + "=>" + (i - 20 * j));
                        String result = classifier.classify(records[i]);
                        if (result.equals(category)) {
                            if (tp.get(category) == null) {
                                tp.put(category, 1);
                            } else {
                                tp.put(category, tp.get(category) + 1);
                            }
                        } else {
                            table.put(records[i] + category, Integer.toString(i), result);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            outputConfuse(table, j);
            outputTp(tp, j);
        }

    }

    private void outputTp(Map<String, Integer> tp, int j) {
        String path = "E:/" + classifierType.name() + "/tp-" + (j + 1) + ".txt";

        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, true)));

            for (String key : tp.keySet()) {
                printWriter.println(key + "," + tp.get(key));
                printWriter.println();
            }
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void outputConfuse(Table<String, String, String> table, int j) {
        String path = "E:/" + classifierType.name() + "/confuse-" + (j + 1) + ".txt";

        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, true)));

            for (String row : table.rowKeySet()) {
                for (String col : table.row(row).keySet()) {
                    printWriter.println(row + "," + col + "," + table.get(row, col));
                    printWriter.println();
                }
            }
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
