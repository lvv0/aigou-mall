package com.cskaoyan.typehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(String[].class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class StringArrayTypeHandler implements TypeHandler<String[]> {
    //Jackson ObjectMapper
    ObjectMapper objectMapper = new ObjectMapper();
    //select id,username,password from cskaoyan_user where username = ? and password = ?
    //index：？对应参数的序号
    //第三个参数：输入映射传入的值
    @SneakyThrows
    @Override
    public void setParameter(PreparedStatement preparedStatement, int index, String[] parameter, JdbcType jdbcType) throws SQLException {
        //使用jackson将Object转换为字符串
        String value = objectMapper.writeValueAsString(parameter);
        preparedStatement.setString(index,value);
    }

    /**
     * 输出映射：查询结果集的封装 👉 JavaBean中的set方法 → setGallery(String[])
     * 而我们获得的结果集中的gallery对应的字符串 : String 👉 String[]
     * getString：获得结果集中的结果
     */
    @Override
    public String[] getResult(ResultSet resultSet, String columnName) throws SQLException {
        String result = resultSet.getString(columnName);
        return transfer(result);
    }

    @Override
    public String[] getResult(ResultSet resultSet, int index) throws SQLException {
        String result = resultSet.getString(index);
        return transfer(result);
    }

    @Override
    public String[] getResult(CallableStatement callableStatement, int i) throws SQLException {
        String result = callableStatement.getString(i);
        return transfer(result);
    }

    private String[] transfer(String result) {
        if (result == null || "".equals(result)) {
            return new String[0];
        }
        String[] strings = new String[0];
        try {
            strings = objectMapper.readValue(result, String[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return strings;
    }
}
