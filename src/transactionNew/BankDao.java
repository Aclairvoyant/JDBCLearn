package transactionNew;

import Utils.JdbcUtils;
import Utils.JdbcUtilsV2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author: 沈佳栋
 * @Description: TODO bank表的数据库操作方法类
 * @DateTime: 2023/5/28 13:45
 **/
public class BankDao {
    public void add(String account, int money) throws ClassNotFoundException, SQLException {
        Connection connection = JdbcUtilsV2.connection();
        String sql = "update t_bank set money = money + ? where account = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1, money);
        preparedStatement.setObject(2, account);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        //connection.close();
        System.out.println("转账成功");
    }

    public void sub(String account, int money) throws ClassNotFoundException, SQLException {
        Connection connection = JdbcUtilsV2.connection();
        String sql = "update t_bank set money = money - ? where account = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1, money);
        preparedStatement.setObject(2, account);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        //connection.close();
        System.out.println("消费成功");
    }
}
