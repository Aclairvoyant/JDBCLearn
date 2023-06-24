package Utils;

import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/5/28 00:07
 **/
public class PSCURDPart extends BaseDao{
    /**
     * 插入一条用户数据!
     * 账号: test
     * 密码: test
     * 昵称: 测试
     */
    @Test
    public void testInsert() throws Exception{

        //注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        //获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "shenjiadong1010");

        String sql = "insert into t_user(account,password,nickname) values (?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //占位符赋值
        preparedStatement.setString(1, "test");
        preparedStatement.setString(2, "test");
        preparedStatement.setString(3, "测试");

        //发送SQL语句
        int rows = preparedStatement.executeUpdate();

        //输出结果
        System.out.println(rows);

        //关闭资源close
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testUpdate() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "shenjiadong1010");
        String sql = "update t_user set nickname = ? where id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1, "测试");
        preparedStatement.setObject(2, "3");

        int rows = preparedStatement.executeUpdate();
        System.out.println(rows);

        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testUpdate2() throws ClassNotFoundException, SQLException {
        String sql = "update t_user set nickname = ? where id = ?;";
        int rows = executeUpdate(sql, "测试33333", "122", "2123123");
        System.out.println(rows);
    }

    @Test
    public void testSelect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "shenjiadong1010");
        String sql = "select * from t_user";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Map> list = new ArrayList<>();

        //获取列的信息
        ResultSetMetaData metaData = resultSet.getMetaData();
        //获取列数
        int columnCount = metaData.getColumnCount();

        while (resultSet.next()) {
            Map map = new HashMap();

            for (int i = 1; i <= columnCount; i++) {
                //获取列值
                Object value = resultSet.getObject(i);
                //获取列名
                String columnLabel = metaData.getColumnLabel(i);
                map.put(columnLabel, value);
            }
            list.add(map);
        }

        System.out.println(list);

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }
}
