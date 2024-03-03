package app.deppen.dme.model.dto;


import lombok.*;

import java.util.UUID;


@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonitoradoDto {

    private UUID id;
    private String matrícula;
    private String nome;
    private String RG;
    private String CPF;
    private String sexo;
    private String Data_de_nascimento;
    private String nome_da_mae;
    private String endereco_de_residencia;
    private String bairro_de_residencia;
    private String cidade_de_residencia;
    private String telefone_de_contato;
    private String telefone_SMS;
    private String processos;
    private String mandado;
    private String origem;
    private String perfil_resumido;
    private String termino_previsto_do_monitoramento;
    private String data_do_cadastro;


}
