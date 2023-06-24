package druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @Author: 沈佳栋
 * @Description: TODO druid连接池使用
 * @DateTime: 2023/5/29 19:33
 **/
public class DruidUsePart {
    /**
     * 1.创建一个druid连接池对象
     * 2.设置连接池参数（必要、非必要）
     * 3.获取连接
     * 4.回收连接
     */

    @Test
    public void druidHard() throws SQLException {

        DruidDataSource dataSource = new DruidDataSource();

        //设置四个必须参数
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("shenjiadong1010");
        dataSource.setUrl("jdbc:mysql:///day01");

        dataSource.setInitialSize(5); //初始化连接数量
        dataSource.setMaxActive(10); //最大连接数量

        //获取连接
        Connection connection = dataSource.getConnection();


        //回收连接
        connection.close();
    }

    @Test
    public void testSoft() throws Exception {
        //1.读取外部文件
        Properties properties = new Properties();

        //使用类加载器
        InputStream resourceAsStream = DruidUsePart.class.getClassLoader().getResourceAsStream("druid.properties");

        properties.load(resourceAsStream);

        //2.使用连接池的工厂模式创建连接池
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        Connection connection = dataSource.getConnection();

        //回收连接
        connection.close();
    }

}
