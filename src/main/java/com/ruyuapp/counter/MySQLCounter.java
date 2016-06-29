package com.ruyuapp.counter;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author <a href="mailto:letcheng@ruyuapp.com">letcheng</a>
 * @version create at 2016年3月29日 20:04
 */
public class MySQLCounter extends AbstractCounter {


    private Connection conn = null;

    private QueryRunner runner = null;

    public MySQLCounter(int fromIndex1,int toIndex1,int fromIndex2,int toIndex2) {
        super(fromIndex1,toIndex1,fromIndex2,toIndex2);
        try {
            DbUtils.loadDriver("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/weibo", "root", "without");
            runner = new QueryRunner();

            super.catagories = runner.query(conn, "SELECT DISTINCT t.`tag` FROM weibo.`tweet` t",
                    new ResultSetHandler<String[]>() {
                        @Override
                        public String[] handle(ResultSet rs) throws SQLException {

                            List<String> result = new ArrayList<String>();
                            while (rs.next()){
                                result.add(rs.getString("tag"));
                            }
                            return result.toArray(new String[]{});
                        }
                    });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MySQLCounter(){
        super();
        try {
            DbUtils.loadDriver("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/weibo", "root", "without");
            runner = new QueryRunner();

            super.catagories = runner.query(conn, "SELECT DISTINCT t.`tag` FROM weibo.`tweet` t",
                    new ResultSetHandler<String[]>() {
                        @Override
                        public String[] handle(ResultSet rs) throws SQLException {

                            List<String> result = new ArrayList<String>();
                            while (rs.next()){
                                result.add(rs.getString("tag"));
                            }
                            return result.toArray(new String[]{});
                        }
                    });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 每个标签下包含单词word的文件数
     * @param word
     * @param catagory
     * @return
     */
    @Override
    public int countDocByWordAndCatagory(String word, String catagory) {
        if (word == null || word.equals("")) {
            return 0;
        }
        Integer count = 0;
        String[] records = new String[0];
        try {
            records = listRecordsByCatagory(catagory);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(String record : records){
            String[] terms = processor.process(record);
            for (String term : terms) {
                if (word.equals(term)) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    /**
     * 计算某个标签下的文档总数
     * @param catagory
     * @return
     */
    @Override
    public int countDocByCatagory(String catagory) {
        try {
            return Integer.valueOf(runner.query(conn,"SELECT count(*) FROM weibo.`tweet` t WHERE t.`tag` = ?",new ScalarHandler<Long>(),catagory).toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 每个标签下单词word在各个文件中出现过的次数之和
     * @param word
     * @param catagory
     * @return
     */
    @Override
    public int countWordsByWordAndCatagory(String word, String catagory) {
        if (word == null || word.equals("")) {
            return 0;
        }
        int count = 0;
        String[] records = new String[0];
        try {
            records = listRecordsByCatagory(catagory);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(String record : records){
            String[] terms = processor.process(record);
            for (String term : terms) {
                if (word.equals(term)) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public void processWordsCount() {
        wordsCountOfCatagory = new HashMap<String, Integer>();
        for (String category : catagories) {
            try {
                String[] records = listRecordsByCatagory(category);
                Integer wordCount = 0;
                for (String record : records) {
                    String[] terms = processor.process(record);
                    for(String term : terms){
                        words.add(term);
                    }
                    wordCount += terms.length;
                }
                wordsCountOfCatagory.put(category, wordCount);
                wordsCount += wordCount;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String[] listRecordsByCatagory(String catagory) throws SQLException {

        Integer count = 0;
        try {
            count = Integer.valueOf(runner.query(conn,"SELECT count(*) FROM weibo.`tweet` t WHERE t.`tag` = ?",
                    new ScalarHandler<Long>(),catagory).toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(toIndex2==-1)
            this.toIndex2 = count;

        if(fromIndex2 > toIndex1 && fromIndex1 >=0 && toIndex2 < count){

            if(toIndex1>fromIndex1){
                return runner.query(conn, "SELECT t.`content` FROM weibo.`tweet` t WHERE t.`tag` = ? LIMIT ?,?",
                        new ResultSetHandler<String[]>() {
                            @Override
                            public String[] handle(ResultSet rs) throws SQLException {
                                List<String> result = new ArrayList<String>();
                                while (rs.next()){
                                    result.add(rs.getString("content"));
                                }
                                return result.toArray(new String[]{});
                            }
                        },catagory, fromIndex1 - 1, toIndex1 - 1);

            }
            if(toIndex2>fromIndex2){
                return runner.query(conn, "SELECT t.`content` FROM weibo.`tweet` t WHERE t.`tag` = ? LIMIT ?,?",
                        new ResultSetHandler<String[]>() {
                            @Override
                            public String[] handle(ResultSet rs) throws SQLException {
                                List<String> result = new ArrayList<String>();
                                while (rs.next()){
                                    result.add(rs.getString("content"));
                                }
                                return result.toArray(new String[]{});
                            }
                        },catagory,fromIndex2-1,toIndex2-1);
            }
        }
        return runner.query(conn,"SELECT t.`content` FROM weibo.`tweet` t WHERE t.`tag` = ?",
                new ResultSetHandler<String[]>() {
                    @Override
                    public String[] handle(ResultSet rs) throws SQLException {
                        List<String> result = new ArrayList<String>();
                        while (rs.next()){
                            result.add(rs.getString("content"));
                        }
                        return result.toArray(new String[]{});
                    }
                },
                catagory);
    }

}
