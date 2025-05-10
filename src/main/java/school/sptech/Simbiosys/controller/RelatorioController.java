package school.sptech.Simbiosys.controller;

import adapter.RelatorioExcelAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.Simbiosys.exception.DadosInvalidosException;
import school.sptech.Simbiosys.exception.EntidadeJaExistente;
import school.sptech.Simbiosys.exception.EntidadeNaoEncontradaException;
import school.sptech.Simbiosys.model.Relatorio;
import school.sptech.Simbiosys.repository.RelatorioRepository;
import school.sptech.Simbiosys.service.RelatorioService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/relatorios")
@CrossOrigin(origins = "${cors.allowed.origin}")
public class RelatorioController {

    @Autowired
    private RelatorioService service;
    private static final String CAMINHO_PASTA = "/tmp/relatorios/";

    @PostMapping
    public ResponseEntity<Relatorio> cadastrar (@RequestBody Relatorio relatorio) {
        try {
            Relatorio relatorioSalvo = service.cadastrar(relatorio);
            return ResponseEntity.status(201).body(relatorioSalvo);
        } catch (DadosInvalidosException e) {
            return ResponseEntity.status(400).build();
        }catch (EntidadeJaExistente e) {
            return ResponseEntity.status(409).build();
        }
    }

    @GetMapping("/ano/{ano}/listar")
    public ResponseEntity<List<Relatorio>> listar(@PathVariable String ano) {
        List<Relatorio> relatorios = service.buscarRelatoriosPorAno(ano);
        if (relatorios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(relatorios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Relatorio> buscarPorId(@PathVariable Integer id) {
        try {
            Relatorio relatorio = service.buscarPorId(id);
            return ResponseEntity.ok(relatorio);
        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Integer id) {
        try {
            service.deletar(id);
            return ResponseEntity.status(204).build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Relatorio> atualizar(@PathVariable Integer id, @RequestBody Relatorio relatorioAtualizado) {
        try {
            Relatorio relatorioSalvo = service.atualizar(id, relatorioAtualizado);
            return ResponseEntity.ok(relatorioSalvo);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(404).build();
        } catch (EntidadeJaExistente e) {
            return ResponseEntity.status(409).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/mesAno/{mesAno}")
    public ResponseEntity<Relatorio> buscarPorMesAno(@PathVariable String mesAno) {
        try {
            Relatorio relatorio = service.buscarPorMesAno(mesAno);
            return ResponseEntity.ok(relatorio);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @GetMapping("/ano/{ano}")
    public ResponseEntity<Relatorio> getRelatorioSomadoPorAno(@PathVariable String ano) {
        Relatorio relatorioSomado = service.somarRelatoriosPorAno(ano);
        return ResponseEntity.ok(relatorioSomado);
    }

    // /relatorios/periodo?de=01/2024&para=04/2024
    @GetMapping("/periodo")
    public Relatorio getRelatorioSomadoPorPeriodo(
            @RequestParam String de,
            @RequestParam String para
    ) {
        return service.somarRelatoriosPorPeriodo(de, para);
    }

    // ========= FLUXO 1 - EXPORTAÇÃO E RETORNO DO LINK ===========

    @GetMapping("/exportar/ano/{ano}")
    public ResponseEntity<String> exportarPorAno(@PathVariable String ano) throws IOException {
        List<Relatorio> relatorios = service.buscarRelatoriosPorAno(ano);
        String nomeArquivo = "relatorio_ano_" + ano + ".xlsx";
        RelatorioExcelAdapter.gerarRelatorioExcel(relatorios, nomeArquivo);
        String urlDownload = "/relatorios/download/" + nomeArquivo;
        return ResponseEntity.ok(urlDownload);
    }

    ///relatorios/exportar/mes?mesAno=01/2025
    @GetMapping("/exportar/mes")
    public ResponseEntity<String> exportarPorMes(@RequestParam String mesAno) throws IOException {
        List<Relatorio> relatorios = service.buscarRelatoriosPorPeriodo(mesAno, mesAno);
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
        List<Relatorio> relatorios = service.buscarRelatoriosPorPeriodo(de, para);
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
