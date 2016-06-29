package com.ruyuapp;

import com.ruyuapp.bayes.TextModel;
import com.ruyuapp.eval.MySQLCrossValidationEval;
import org.junit.Test;

/**
 * @author <a href="mailto:letcheng@ruyuapp.com">letcheng</a>
 * @version create at 2016年3月29日 20:04
 */
public class ClassifierEvalTest {

    @Test
    public void test(){

        MySQLCrossValidationEval crossValidationEval = new MySQLCrossValidationEval(TextModel.POLYNOMIAL);

        crossValidationEval.eval();
    }
}
