package com.avegarlabs.utils;

import com.avegarlabs.dto.NomQueryItem;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
public class NomQueryItemRowMapper implements RowMapper<NomQueryItem> {
    @Override
    public NomQueryItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        return NomQueryItem.builder()
                .ci(rs.getString("numident"))
                .codigo(rs.getString("codigo"))
                .totalCobrar(rs.getBigDecimal("totalCobrar"))
                .totalTiempoEntrado(rs.getBigDecimal("totalTiempoEntrado"))
                .totalBonificaciones(rs.getBigDecimal("totalBonificaciones"))
                .totalPenalizaciones(rs.getBigDecimal("totalPenalizaciones"))
                .build();
    }
}
