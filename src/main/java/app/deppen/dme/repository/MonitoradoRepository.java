package app.deppen.dme.repository;

import app.deppen.dme.model.entity.Monitorado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MonitoradoRepository extends JpaRepository<Monitorado, UUID> {

    @Query( value = "SELECT * FROM monitorado m WHERE m.matricula = ?1", nativeQuery = true)
    public Monitorado findByMatricula(String st);

}
