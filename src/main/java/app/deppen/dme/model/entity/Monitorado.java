package app.deppen.dme.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@ToString
//@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Monitorado {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "matricula", unique = true)
    private  String matrícula;
    @Column(name = "nome")
    private String nome;
    @Column(name = "rg",unique = true)
    private  String RG;
    @Column(name = "cpf", unique = true)
    private String CPF;
    @Column(name = "sexo")
    private  String sexo;
    @Column(name = "data_nascimento")
    private String Data_de_nascimento;
    @Column(name = "mae")
    private String nome_da_mae;
    @Column(name = "endereco")
    private String endereco_de_residencia;
    @Column(name = "bairro")
    private String bairro_de_residencia;
    @Column(name = "cidade")
    private String cidade_de_residencia;
    @Column(name = "telefone_contato")
    private String telefone_de_contato;
    @Column(name = "telefone_SMS")
    private String telefone_SMS;
    @Column(name = "processo")
    private String processos;
    @Column(name = "mandado")
    private String mandado;
    @Column(name = "origem")
    private String origem;
    @Column(name = "perfil")
    private String perfil_resumido;
    @Column(name = "termino")
    private String termino_previsto_do_monitoramento;
    @Column(name = "data_cadastro")
    private String data_do_cadastro;




}
