package com.jdbc.api.statement.StatementQueryPart;

import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.Scanner;

/**
 * @Author: 沈佳栋
 * @Description: 模拟用户登陆
 * @DateTime: 2023/5/27 21:49
 **/
public class StatementUserLoginPart {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String account = scanner.nextLine();
        System.out.println("请输入密码：");
        String password = scanner.nextLine();

        String classFullPath = "com.mysql.cj.jdbc.Driver";
        Class<?> cls = Class.forName(classFullPath);

        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "shenjiadong1010");
        Statement statement = connection.createStatement();
        String sql = "select * from t_user where account = '" + account +"' and PASSWORD = '" + password + "';";
        ResultSet resultSet = statement.executeQuery(sql);

//        while(resultSet.next()) {
//            int id = resultSet.getInt("id");
//            String account1 = resultSet.getString("account");
//            String password1 = resultSet.getString("PASSWORD");
//            System.out.println(id + " " + account1 + " " + password1);
//        }

        if(resultSet.next()) System.out.println("true");
        else System.out.println("false");

        resultSet.close();
        statement.close();
        connection.close();
    }
}
