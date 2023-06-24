package Utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.lang.*;

/**
 * @Author: 沈佳栋
 * @Description: TODO 工具类内部包含一个连接池对象
 *                    对外提供连接和回收的方法
 * @DateTime: 2023/5/29 20:03
 **/
public class JdbcUtilsV2 {
    private static DataSource dataSource = null;

    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    static {
        //初始化连接池对象
        Properties properties = new Properties();
        InputStream resourceAsStream = JdbcUtilsV2.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对外提供连接的方法
     * @return
     */
    public static Connection connection() throws SQLException {
        //检查是否已经存在
        Connection connection = threadLocal.get();

        if(connection == null) {
            //没有就获取
            connection = dataSource.getConnection();
            threadLocal.set(connection);
        }
        return connection;
    }

    public static void freeConnection() throws SQLException {
        Connection connection = threadLocal.get();
        if(connection != null) {
            threadLocal.remove(); //清空
            connection.setAutoCommit(true);
            connection.close();
        }
    }
}
