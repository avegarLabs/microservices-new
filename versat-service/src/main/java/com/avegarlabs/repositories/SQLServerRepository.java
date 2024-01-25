package com.avegarlabs.repositories;

import com.avegarlabs.dto.NomQueryItem;
import com.avegarlabs.utils.NomQueryItemRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SQLServerRepository {

    @Autowired
    @Qualifier("sqlServerJdbcTemplate")
    private JdbcTemplate sqlServerJdbcTemplate;


    public List<NomQueryItem> readNomData() {
        String sql = "SELECT \n" +
                "  gen.numident, \n" +
                "  gen.codigo, \n" +
                "  SUM(a.n_Cobrar) AS totalCobrar, \n" +
                "  SUM(a.n_tiempoentrado) AS totalTiempoEntrado, \n" +
                "  SUM(a.n_TotalBonificaciones) AS totalBonificaciones, \n" +
                "  SUM(a.n_TotalPenalizaciones) AS totalPenalizaciones\n" +
                "FROM \n" +
                "  nom_documento_detalle_pago a \n" +
                "INNER JOIN (\n" +
                "  SELECT nom2.idtrabajador, MAX(nom2.iddocumento) lId \n" +
                "  FROM nom_documento_detalle_pago nom2 \n" +
                "  WHERE nom2.idconcepto = 1 AND nom2.n_Cobrar != 0.0 \n" +
                "  GROUP BY idtrabajador\n" +
                ") b \n" +
                "ON a.idtrabajador = b.idtrabajador and a.iddocumento = b.lId \n" +
                "INNER JOIN gen_trabajador gen ON b.idtrabajador = gen.idtrabajador \n" +
                "GROUP BY gen.numident, gen.codigo;\n";
        return sqlServerJdbcTemplate.query(sql, new NomQueryItemRowMapper());
    }



}
