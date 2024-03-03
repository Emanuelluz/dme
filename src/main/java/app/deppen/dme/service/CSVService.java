package app.deppen.dme.service;

import app.deppen.dme.model.dto.ViolacaoDto;
import app.deppen.dme.model.entity.Monitorado;
import app.deppen.dme.model.entity.Violacao;
import app.deppen.dme.repository.MonitoradoRepository;
import app.deppen.dme.repository.ViolacoesRepository;
import app.deppen.dme.utils.CSVHelper;
import jakarta.transaction.Transactional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
public class CSVService {

    final
    MonitoradoRepository monitoradoRepository;

    final
    ViolacoesRepository violacoesRepository;

    CSVHelper csvHelper = new CSVHelper();

    public CSVService(MonitoradoRepository monitoradoRepository, ViolacoesRepository violacoesRepository) {
        this.monitoradoRepository = monitoradoRepository;
        this.violacoesRepository = violacoesRepository;
    }

    public void saveMonitorado(MultipartFile file) {
        try {
            List<Monitorado> monitorados = csvHelper.csvToMonitorado(file.getInputStream());
            monitoradoRepository.saveAll(monitorados);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }
    public void saveViolacoes(MultipartFile file) {
        try (InputStreamReader reader = new InputStreamReader(file.getInputStream());
             CSVParser csvParser = CSVFormat.RFC4180.withDelimiter(',').withFirstRecordAsHeader().withTrim().parse(reader)){

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            System.out.println(csvRecords.toString());


            for (CSVRecord csvRecord : csvRecords) {
                System.out.println(csvRecord.get(1));
                String mat = csvRecord.get(1);

                String str = csvRecord.get(9);
                str = str.replace("(", "");
                str = str.replace(")", "");
                String[] arrOfStr = str.split(" ");
                ViolacaoDto violacaoDto = ViolacaoDto.builder()
                        .id_monitorado(csvRecord.get(0))
                        .estabelecimento(csvRecord.get(2))
                        .perfil(csvRecord.get(3))
                        .artigos(csvRecord.get(4))
                        .alarme(csvRecord.get(5))
                        .data_inicio(LocalDateTime.parse(csvRecord.get(6), formatter))
                        .data_violacao(LocalDateTime.parse(csvRecord.get(7), formatter))
                        .data_finalizacao(LocalDateTime.parse(csvRecord.get(8), formatter))
                        .duracao(LocalTime.parse(arrOfStr[0]))
                        .duracao_com_alarme(LocalTime.parse(arrOfStr[1]))
                        .build();


                Violacao violacao = CSVHelper.convertToEntityViolacao(violacaoDto);
                violacao.setMonitorado(monitoradoRepository.findByMatricula(mat));
                System.out.println(violacao.toString());
                violacoesRepository.save(violacao);


            }
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }


    public List<Monitorado> getAllTutorials() {
        return monitoradoRepository.findAll();
    }

    public Monitorado findMonitorado(String mat){
        return monitoradoRepository.findByMatricula(mat);
    }
}
