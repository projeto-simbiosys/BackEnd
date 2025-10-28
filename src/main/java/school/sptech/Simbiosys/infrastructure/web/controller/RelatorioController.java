package school.sptech.Simbiosys.infrastructure.web.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import school.sptech.Simbiosys.core.application.usecase.*;
import school.sptech.Simbiosys.infrastructure.messaging.RelatorioProducer;
import school.sptech.Simbiosys.infrastructure.persistence.file.RelatorioExcelAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import school.sptech.Simbiosys.core.dto.RelatorioResponseDto;
import school.sptech.Simbiosys.infrastructure.persistence.entity.RelatorioEntity;
import school.sptech.Simbiosys.core.application.exception.DadosInvalidosException;
import school.sptech.Simbiosys.core.application.exception.EntidadeJaExistente;
import school.sptech.Simbiosys.core.application.exception.EntidadeNaoEncontradaException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/relatorios")
@CrossOrigin(origins = "${cors.allowed.origin}")
public class RelatorioController {


    @Autowired
    private RelatorioProducer relatorioProducer;

    @Autowired
    private CadastrarRelatorioUseCase cadastrarRelatorioUseCase;

    @Autowired
    private BuscarRelatorioPorIdUseCase buscarRelatorioPorIdUseCase;

    @Autowired
    private BuscarRelatoriosPorPeriodoUseCase buscarRelatoriosPorPeriodoUs;

    @Autowired
    private DeletarRelatorioUseCase deletarRelatorioUseCase;

    @Autowired
    private AtualizarRelatorioUseCase atualizarRelatorioUseCase;

    @Autowired
    private BuscarRelatoriosPorAnoUseCase buscarRelatoriosPorAnoUseCase;

    @Autowired
    private SomarRelatoriosPorAnoUseCase somarRelatoriosPorAnoUseCase;

    @Autowired
    private SomarRelatoriosPorPeriodoUseCase somarRelatoriosPorPeriodoUseCase;

    private static final String CAMINHO_PASTA = "/tmp/relatorios/";

    @PostMapping
    public ResponseEntity<RelatorioResponseDto> cadastrar(@RequestBody RelatorioEntity relatorio, Authentication authentication) {
        try {
            RelatorioEntity relatorioSalvo = cadastrarRelatorioUseCase.execute(relatorio, authentication);
            RelatorioResponseDto relatorioResponseDto = new RelatorioResponseDto(relatorioSalvo);

            // Envia mensagem ao RabbitMQ
            String mensagem = String.format("Novo relatório criado: ID=%d, Ano=%s", relatorioSalvo.getId(), relatorioSalvo.getMesAno());
            relatorioProducer.enviarMensagem(mensagem);

            return ResponseEntity.status(201).body(relatorioResponseDto);
        } catch (DadosInvalidosException e) {
            return ResponseEntity.status(400).build();
        } catch (EntidadeJaExistente e) {
            return ResponseEntity.status(409).build();
        }
    }


    @GetMapping("/ano/{ano}/listar")
    public ResponseEntity<Page<RelatorioEntity>> listar(
            @PathVariable String ano,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                direction.equalsIgnoreCase("asc")
                        ? Sort.by(sortBy).ascending()
                        : Sort.by(sortBy).descending()
        );

        Page<RelatorioEntity> relatorios = buscarRelatoriosPorAnoUseCase.execute(ano, pageable);

        if (relatorios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(relatorios);
    }


    @GetMapping("/{id}")
    public ResponseEntity<RelatorioEntity> buscarPorId(@PathVariable Integer id) {
        try {
            RelatorioEntity relatorio = buscarRelatorioPorIdUseCase.execute(id);
            return ResponseEntity.ok(relatorio);
        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Integer id) {
        try {
            deletarRelatorioUseCase.execute(id);
            return ResponseEntity.status(204).build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RelatorioResponseDto> atualizar(@PathVariable Integer id, @RequestBody RelatorioEntity relatorioInput, Authentication authentication) {
        RelatorioEntity relatorioAtualizado = atualizarRelatorioUseCase.execute(id, relatorioInput, authentication);
        RelatorioResponseDto relatorioResponseDto = new RelatorioResponseDto(relatorioAtualizado);
        return ResponseEntity.ok(relatorioResponseDto);
    }

    @GetMapping("/ano/{ano}")
    public ResponseEntity<RelatorioEntity> getRelatorioSomadoPorAno(@PathVariable String ano) {
        RelatorioEntity relatorioSomado = somarRelatoriosPorAnoUseCase.execute(ano);
        return ResponseEntity.ok(relatorioSomado);
    }

    @GetMapping("/periodo")
    public RelatorioEntity getRelatorioSomadoPorPeriodo(
            @RequestParam String de,
            @RequestParam String para
    ) {
        return somarRelatoriosPorPeriodoUseCase.execute(de, para);
    }

    // ========= FLUXO 1 - EXPORTAÇÃO E RETORNO DO LINK ===========

    @GetMapping("/exportar/ano/{ano}")
    public ResponseEntity<String> exportarPorAno(@PathVariable String ano) throws IOException {
        List<RelatorioEntity> relatorios = buscarRelatoriosPorAnoUseCase.execute(ano, Pageable.unpaged()).getContent();
        String nomeArquivo = "relatorio_ano_" + ano + ".xlsx";
        RelatorioExcelAdapter.gerarRelatorioExcel(relatorios, nomeArquivo);
        String urlDownload = "/relatorios/download/" + nomeArquivo;
        return ResponseEntity.ok(urlDownload);
    }

    ///relatorios/exportar/mes?mesAno=01/2025
    @GetMapping("/exportar/mes")
    public ResponseEntity<String> exportarPorMes(@RequestParam String mesAno) throws IOException {
        List<RelatorioEntity> relatorios = buscarRelatoriosPorPeriodoUs.execute(mesAno, mesAno);
        String nomeArquivo = "relatorio_mes_" + mesAno.replace("/", "-") + ".xlsx";
        RelatorioExcelAdapter.gerarRelatorioExcel(relatorios, nomeArquivo);
        String urlDownload = "/relatorios/download/" + nomeArquivo;
        return ResponseEntity.ok(urlDownload);
    }

    @GetMapping("/exportar/periodo")
    public ResponseEntity<String> exportarPorPeriodo(
            @RequestParam String de,
            @RequestParam String para
    ) throws IOException {
        List<RelatorioEntity> relatorios = buscarRelatoriosPorPeriodoUs.execute(de, para);
        String nomeArquivo = "relatorio_periodo_" + de.replace("/", "-") + "_para_" + para.replace("/", "-") + ".xlsx";
        RelatorioExcelAdapter.gerarRelatorioExcel(relatorios, nomeArquivo);
        String urlDownload = "/relatorios/download/" + nomeArquivo;
        return ResponseEntity.ok(urlDownload);
    }

    // ========= FLUXO 2 - DOWNLOAD =========

    @GetMapping("/download/{nomeArquivo}")
    public ResponseEntity<InputStreamResource> downloadRelatorio(@PathVariable String nomeArquivo) throws IOException {
        File arquivo = new File(CAMINHO_PASTA + nomeArquivo);

        if (!arquivo.exists()) {
            return ResponseEntity.notFound().build();
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(arquivo));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nomeArquivo + "\"")
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(arquivo.length())
                .body(resource);
    }
}
