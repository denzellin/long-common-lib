package com.vivavot.basis.persistence.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 *
 * </p>
 *
 * @Author Denzel Lin
 * @Date 2026/6/2 15:34
 * @Version 1.0
 */
public class JsonbTypeHandler
        extends BaseTypeHandler<Object> {

    private static final ObjectMapper MAPPER =
            new ObjectMapper();

    @Override
    public void setNonNullParameter(
            PreparedStatement ps,
            int i,
            Object parameter,
            JdbcType jdbcType)
            throws SQLException {

        PGobject jsonObject = new PGobject();

        jsonObject.setType("jsonb");

        try {
            jsonObject.setValue(
                    MAPPER.writeValueAsString(parameter)
            );
        } catch (JsonProcessingException e) {
            throw new SQLException(e);
        }

        ps.setObject(i, jsonObject);
    }

    @Override
    public Object getNullableResult(
            ResultSet rs,
            String columnName)
            throws SQLException {

        return rs.getString(columnName);
    }

    @Override
    public Object getNullableResult(
            ResultSet rs,
            int columnIndex)
            throws SQLException {

        return rs.getString(columnIndex);
    }

    @Override
    public Object getNullableResult(
            CallableStatement cs,
            int columnIndex)
            throws SQLException {

        return cs.getString(columnIndex);
    }
}
