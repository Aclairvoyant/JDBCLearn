package transactionNew;

import Utils.JdbcUtilsV2;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Author: 沈佳栋
 * @Description: TODO 银行业务方法，调用dao方法
 * @DateTime: 2023/5/28 13:47
 **/
public class BankServices {

    @Test
    public void testTransfer() throws SQLException, ClassNotFoundException {
        transfer("ergouzi", "lvdandan", 500);

    }
    public void transfer(String addAccount, String subAccount, int money) throws SQLException, ClassNotFoundException {
        BankDao bankDao = new BankDao();
        Connection connection = JdbcUtilsV2.connection();
        try {
            // 开启事务
            connection.setAutoCommit(false); //关闭事务提交
            // 执行数据库动作
            bankDao.add(addAccount, money);
            System.out.println("==================");
            bankDao.sub(subAccount, money);
            // 提交事务
            connection.commit();
        }catch (Exception e) {
            // 回滚事务
            connection.rollback();
            // 抛出异常
            throw e;
        }finally {
            JdbcUtilsV2.freeConnection();
        }
    }
}
