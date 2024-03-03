package app.deppen.dme;

import app.deppen.dme.exception.ResourceNotFoundException;
import app.deppen.dme.model.dto.ViolacaoDto;
import app.deppen.dme.model.entity.Monitorado;
import app.deppen.dme.model.entity.Violacao;
import app.deppen.dme.repository.MonitoradoRepository;
import app.deppen.dme.repository.ViolacoesRepository;
import app.deppen.dme.service.CSVService;
import app.deppen.dme.utils.CSVHelper;
import jakarta.transaction.Transactional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class DmeApplicationTests {

	@Autowired
	MonitoradoRepository monitoradoRepository;
	@Autowired
	ViolacoesRepository violacoesRepository;

	@Autowired
	CSVService csvService;

	CSVHelper csvHelper = new CSVHelper();

	@Test
	void contextLoads() throws IOException {


		Reader in = new FileReader("violacao.csv");
		CSVParser csvParser = CSVFormat.RFC4180.withDelimiter(',').withFirstRecordAsHeader().withTrim().parse(in);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		//List<Violacao> violacaoList = new ArrayList<>();
		Iterable<CSVRecord> csvRecords = csvParser.getRecords();

		for (CSVRecord csvRecord : csvRecords) {
			Monitorado monitorado = monitoradoRepository.findByMatricula(csvRecord.get(1));
			//System.out.printf(monitorado.toString());
			//String mat = csvRecord.get(1);
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
			violacao.setMonitorado(monitorado);
			System.out.println(violacao.toString());
			violacoesRepository.save(violacao);
			//violacoesRepository.saveViolacao(violacao,monitorado.getId());
		}
	}

	@Test
	void testemonitorado() throws IOException{

		System.out.println(monitoradoRepository.findByMatricula("100315"));


	}

}
