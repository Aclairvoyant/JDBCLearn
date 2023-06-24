package com.jdbc.api.statement.StatementQueryPart;

import java.sql.*;
import java.util.Scanner;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/5/27 23:55
 **/
public class PSUserLoginPart {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String account = scanner.nextLine();
        System.out.println("请输入密码：");
        String password = scanner.nextLine();

        String classFullPath = "com.mysql.cj.jdbc.Driver";
        Class<?> cls = Class.forName(classFullPath);

        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "shenjiadong1010");
        //Statement statement = connection.createStatement();

        //创建preparedStatement
        //TODO 需要传入SQL语句结构
        //TODO 要的是SQL语句结构，动态值的部分使用 ? , 占位符！
        //TODO ?  不能加 '?'  ? 只能替代值，不能替代关键字和容器名

        String sql = "select * from t_user where account = ? and PASSWORD = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1, account);
        preparedStatement.setObject(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) System.out.println("true");
        else System.out.println("false");

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }
}
