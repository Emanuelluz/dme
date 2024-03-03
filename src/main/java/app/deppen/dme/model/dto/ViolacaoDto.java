package app.deppen.dme.model.dto;

import app.deppen.dme.model.entity.Monitorado;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;


@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ViolacaoDto {

    private UUID id;
    private String id_monitorado;
    private String estabelecimento;
    private String perfil;
    private String artigos;
    private String alarme;
    private LocalDateTime data_inicio;
    private LocalDateTime data_violacao;
    private LocalDateTime data_finalizacao;
    private LocalTime duracao;
    private LocalTime duracao_com_alarme;
    private Monitorado monitorado;



}
