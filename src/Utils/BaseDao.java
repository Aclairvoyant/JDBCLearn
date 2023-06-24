package Utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 沈佳栋
 * @Description: TODO 封装dao 数据库重复代码
 *                    封装两个方法，简化DQL与非DQL
 * @DateTime: 2023/6/3 19:06
 **/
public abstract class BaseDao {

    /**
     * 封装简化非DQL语句
     * @param sql 带占位符的sql语句
     * @param params 占位符的值
     * @return 影响的行数
     */
    public int executeUpdate(String sql, Object... params) throws SQLException {
        //获取连接
        Connection connection = JdbcUtilsV2.connection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //占位符赋值
        for (int i = 1; i <= params.length; i++) {
            preparedStatement.setObject(i, params[i - 1]);
        }

        //sql语句
        int rows = preparedStatement.executeUpdate();

        preparedStatement.close();
        if (!connection.getAutoCommit()) {
            //没有开启事务
            //则回收连接
            JdbcUtilsV2.freeConnection();
        }
        return rows;
    }

    /**
     * 将查询结果封装到一个实体类集合
     * @param tClass
     * @param sql
     * @param params
     * @return
     * @param <T> 声明的结果的类型
     */
    public <T> List<T> executeQuery(Class<T> tClass, String sql, Object... params) throws SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {

        Connection connection = JdbcUtilsV2.connection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        if(params == null && params.length != 0) {
            for (int i = 1; i <= params.length; i++) {
                preparedStatement.setObject(i, params[i - 1]);
            }
        }

        ResultSet resultSet = preparedStatement.executeQuery();

        List<T> list = new ArrayList<>();

        //获取列的信息
        ResultSetMetaData metaData = resultSet.getMetaData();
        //获取列数
        int columnCount = metaData.getColumnCount();

        while (resultSet.next()) {

            T t = tClass.getDeclaredConstructor().newInstance();

            for (int i = 1; i <= columnCount; i++) {
                //获取列值
                Object value = resultSet.getObject(i);
                //获取列名
                String propertyName = metaData.getColumnLabel(i);

                //反射，给对象的属性赋值
                Field declaredField = tClass.getDeclaredField(propertyName);
                declaredField.setAccessible(true); //属性可以设置，打破private
                declaredField.set(t, value);
            }
            list.add(t);
        }

        preparedStatement.close();
        if (!connection.getAutoCommit()) {
            //没有开启事务
            //则回收连接
            JdbcUtilsV2.freeConnection();
        }
        return list;
    }
}
