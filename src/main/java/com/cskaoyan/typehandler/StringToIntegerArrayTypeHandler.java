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

@MappedTypes(Integer[].class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class StringToIntegerArrayTypeHandler implements TypeHandler {
    ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, Object o, JdbcType jdbcType) throws SQLException {
        String string = objectMapper.writeValueAsString(o);
        preparedStatement.setString(i, string);
    }

    @Override
    public Integer[] getResult(ResultSet resultSet, String s) throws SQLException {
        String string = resultSet.getString(s);
        return transfer(string);
    }

    @Override
    public Integer[] getResult(ResultSet resultSet, int i) throws SQLException {
        String string = resultSet.getString(i);
        return transfer(string);
    }

    @Override
    public Integer[] getResult(CallableStatement callableStatement, int i) throws SQLException {
        String string = callableStatement.getString(i);
        return transfer(string);
    }

    private Integer[] transfer(String result) {
        if (result == null || "".equals(result)) {
            return new Integer[0];
        }
        Integer[] integers = new Integer[0];
        try {
            integers = objectMapper.readValue(result, Integer[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return integers;
    }
}
