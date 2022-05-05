package cn.ms22.mybatis;

import org.apache.ibatis.jdbc.SQL;

/**
 * @author baopz
 */
public class SqlBuilder {
    public static void main(String[] args) {
        String sql = new SQL().SELECT("").toString();
    }
}
