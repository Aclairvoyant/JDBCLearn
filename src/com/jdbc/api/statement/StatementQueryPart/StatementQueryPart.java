package com.jdbc.api.statement.StatementQueryPart;

import com.mysql.cj.jdbc.Driver;

import java.sql.*;

public class StatementQueryPart {
    public static void main(String[] args) throws SQLException {
        //注册驱动
        DriverManager.registerDriver(new Driver());

        //获取连接,接口等于实现类
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/atguigu", "root", "shenjiadong1010");

        //创建statement
        Statement statement = connection.createStatement();

        //发送sql语句，并获取返回结果
        String sql = "select * from t_user;";
        ResultSet resultSet = statement.executeQuery(sql);

        //进行结果分析
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String account = resultSet.getString("account");
            String password = resultSet.getString("password");
            String nickname = resultSet.getString("nickname");
            System.out.println(id + " " + account + " " + password + " " + nickname);
        }

        //关闭连接
        resultSet.close();
        statement.close();
        connection.close();

    }
}
