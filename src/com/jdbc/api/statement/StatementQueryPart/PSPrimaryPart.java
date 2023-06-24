package com.jdbc.api.statement.StatementQueryPart;

import com.mysql.jdbc.Driver;
import org.junit.Test;

import java.sql.*;

/**
 * @Author: 沈佳栋
 * @Description: 获取主键
 * @DateTime: 2023/5/28 13:02
 **/
public class PSPrimaryPart {
    @Test
    public void returnPrimaryKey() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "shenjiadong1010");
        String sql = "insert into t_user(account, PASSWORD, nickname) values (?, ?, ?);";

        /**
         * TODO: 第二个参数填入 1 | Statement.RETURN_GENERATED_KEYS
         *       告诉statement携带回数据库生成的主键！
         */

        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setObject(1, "test1");
        preparedStatement.setObject(2, "123456");
        preparedStatement.setObject(3, "lili");
        int i = preparedStatement.executeUpdate();
        if(i > 0) {
            System.out.println("success");
            //获取回显的主键
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next(); //移动光标
            int anInt = generatedKeys.getInt(1);
            System.out.println("id = " + anInt);
        }else {
            System.out.println("fail");
        }

        preparedStatement.close();
        connection.close();
    }

    // TODO: 2023/5/28 单次插入 1434ms
    @Test
    public void testInsert() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "shenjiadong1010");
        String sql = "insert into t_user(account, PASSWORD, nickname) values (?, ?, ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        long start = System.currentTimeMillis();

        for (int i = 1; i <= 10000; i++) {
            preparedStatement.setObject(1, "test2" + i);
            preparedStatement.setObject(2, "123456" + i);
            preparedStatement.setObject(3, "lili" + i);

            preparedStatement.executeUpdate();
        }

        long end = System.currentTimeMillis();

        System.out.println(end - start);

        preparedStatement.close();
        connection.close();
    }

    // TODO: 2023/5/28 批量插入 127ms
    @Test
    public void testBatchInsert() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        //设置允许批量插入
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu?rewriteBatchedStatements=true", "root", "shenjiadong1010");
        //values, 结尾不能加分号
        String sql = "insert into t_user(account, PASSWORD, nickname) values (?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        long start = System.currentTimeMillis();

        for (int i = 1; i <= 10000; i++) {
            preparedStatement.setObject(1, "test3" + i);
            preparedStatement.setObject(2, "12345" + i);
            preparedStatement.setObject(3, "lili2" + i);

            preparedStatement.addBatch(); //先不执行，追加到values中

        }

        preparedStatement.executeBatch(); //统一执行

        long end = System.currentTimeMillis();

        System.out.println(end - start);

        preparedStatement.close();
        connection.close();
    }
}
