package com.vivavot.basis.persistence.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @Author Denzel Lin
 * @Date 2026/6/2 15:26
 * @Version 1.0
 */
@MappedTypes(List.class)
@MappedJdbcTypes(JdbcType.OTHER)
public class PgVectorTypeHandler
        extends BaseTypeHandler<List<Double>> {

    @Override
    public void setNonNullParameter(
            PreparedStatement ps,
            int i,
            List<Double> parameter,
            JdbcType jdbcType)
            throws SQLException {

        String vector =
                parameter.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(",", "[", "]"));

        ps.setObject(i, vector, Types.OTHER);
    }

    @Override
    public List<Double> getNullableResult(
            ResultSet rs,
            String columnName)
            throws SQLException {

        return parseVector(rs.getString(columnName));
    }

    @Override
    public List<Double> getNullableResult(
            ResultSet rs,
            int columnIndex)
            throws SQLException {

        return parseVector(rs.getString(columnIndex));
    }

    @Override
    public List<Double> getNullableResult(
            CallableStatement cs,
            int columnIndex)
            throws SQLException {

        return parseVector(cs.getString(columnIndex));
    }

    private List<Double> parseVector(String text) {

        if (text == null) {
            return null;
        }

        text = text.replace("[", "")
                .replace("]", "");

        return Arrays.stream(text.split(","))
                .map(Double::parseDouble)
                .toList();
    }
}
