package app.deppen.dme.utils;

import app.deppen.dme.model.dto.MonitoradoDto;
import app.deppen.dme.model.dto.ViolacaoDto;
import app.deppen.dme.model.entity.Monitorado;
import app.deppen.dme.model.entity.Violacao;
import app.deppen.dme.repository.MonitoradoRepository;
import app.deppen.dme.service.CSVService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class CSVHelper {

    public static String TYPE = "text/csv";
    static String[] HEADERs = {"Id. matrícula", "Nome do monitorado", "RG", "CPF", "Data de nascimento", "Nome da mãe", "Endereço de residência", "Bairro de residência", "Cidade de residência", "Telefone de contato", "Telefone SMS", "Processos", "Mandado", "Origem", "Perfil resumido", "Término previsto do monitoramento", "Data do cadastro"};

    private static final ModelMapper modelMapper = new ModelMapper();




    CSVService csvService;

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());

    }

    public List<Monitorado> csvToMonitorado(InputStream is) {
        try (InputStreamReader reader = new InputStreamReader(is);
             CSVParser csvParser = CSVFormat.RFC4180.withDelimiter(';').withFirstRecordAsHeader().withTrim().parse(reader)) {

            List<Monitorado> monitoradoList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            System.out.println(csvRecords.toString());
            for (CSVRecord csvRecord : csvRecords) {
                MonitoradoDto monitoradoDto = MonitoradoDto.builder()
                        .matrícula(csvRecord.get(0))
                        .nome(csvRecord.get(1))
                        .RG(csvRecord.get(2))
                        .CPF(csvRecord.get(3))
                        .sexo(csvRecord.get(4))
                        .Data_de_nascimento(csvRecord.get(5))
                        .nome_da_mae(csvRecord.get(6))
                        .endereco_de_residencia(csvRecord.get(7))
                        .bairro_de_residencia(csvRecord.get(8))
                        .cidade_de_residencia(csvRecord.get(9))
                        .telefone_de_contato(csvRecord.get(10))
                        .telefone_SMS(csvRecord.get(11))
                        .processos(csvRecord.get(12))
                        .mandado(csvRecord.get(13))
                        .origem(csvRecord.get(14))
                        .perfil_resumido(csvRecord.get(15))
                        .termino_previsto_do_monitoramento(csvRecord.get(16))
                        .data_do_cadastro(csvRecord.get(17))
                        .build();

                monitoradoList.add(convertToEntityMonitorado(monitoradoDto));
            }
            //System.out.println(monitoradoList.stream().toList());
            return monitoradoList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());

        }
    }

    public void csvToViolacoes(InputStream is) {
        try (InputStreamReader reader = new InputStreamReader(is);
             CSVParser csvParser = CSVFormat.RFC4180.withDelimiter(',').withFirstRecordAsHeader().withTrim().parse(reader)) {


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
                System.out.println(violacao.toString());
                System.out.println(csvService.findMonitorado(mat));



            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Violacao convertToEntityViolacao(ViolacaoDto violacaoDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        Violacao violacao;
        violacao = modelMapper.map(violacaoDto, Violacao.class);
        return violacao;
    }

    public MonitoradoDto convertToDto(Monitorado monitorado) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        MonitoradoDto monitoradoDto;
        monitoradoDto = modelMapper.map(monitorado, MonitoradoDto.class);

        return monitoradoDto;

    }

    public Monitorado convertToEntityMonitorado(MonitoradoDto monitoradoDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        Monitorado monitorado;
        monitorado = modelMapper.map(monitoradoDto, Monitorado.class);
        return monitorado;
    }

}

