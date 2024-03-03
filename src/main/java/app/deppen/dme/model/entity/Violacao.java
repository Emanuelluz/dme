package app.deppen.dme.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Violacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private String estabelecimento;
    @Column(name = "perfil_atual")
    private String perfil;
    @Column
    private String artigos;
    @Column
    private String alarme;
    @Column
    private LocalDateTime data_inicio;
    @Column
    private LocalDateTime data_violacao;
    @Column
    private LocalDateTime data_finalizacao;
    @Column
    private LocalTime duracao;
    @Column
    private LocalTime duracao_com_alarme;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "monitorado_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Monitorado monitorado;




}
