package com.avegarlabs.hiringservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WorkerListItem {
    private int id;
    private String primer_nombre;
    private String segundo_nombre;
    private  String apellidos;
    private String ci;
    private  String codigo_interno;
    private BigDecimal salario_escala;
    private BigDecimal salario_escala_ref;
    private BigDecimal salario_total_reforma;
    private String name;
    private int departamento_id;
    private int cargo_id;
}
