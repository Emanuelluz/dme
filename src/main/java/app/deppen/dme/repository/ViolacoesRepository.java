package app.deppen.dme.repository;

import app.deppen.dme.model.entity.Monitorado;
import app.deppen.dme.model.entity.Violacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ViolacoesRepository extends JpaRepository <Violacao, UUID> {

    @Query(value ="insert into violacao (alarme, artigos, data_finalizacao, data_inicio, data_violacao, duracao, duracao_com_alarme, estabelecimento, perfil_atual, monitorado_id)"+
            "values"+
            "(:violacao.alarme,:violacao.artigos,:violacao.data_finalizacao,:violacao.data_inicio,:violacao.data_violacao,:violacao.duracao,:violacao.duracao_com_alarme,:violacao.estabelecimento:violacao.perfil_atual,:id)", nativeQuery = true)
    public void saveViolacao(Violacao violacao, UUID id);
}
