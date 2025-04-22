package school.sptech.Simbiosys.controller;

import adapter.RelatorioExcelAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.Simbiosys.exception.DadosInvalidosException;
import school.sptech.Simbiosys.exception.EntidadeJaExistente;
import school.sptech.Simbiosys.exception.EntidadeNaoEncontradaException;
import school.sptech.Simbiosys.model.Relatorio;
import school.sptech.Simbiosys.repository.RelatorioRepository;
import school.sptech.Simbiosys.service.RelatorioService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/relatorios")
@CrossOrigin(origins = "${cors.allowed.origins}")
public class RelatorioController {

    @Autowired
    private RelatorioService service;

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

    @GetMapping
    public ResponseEntity<List<Relatorio>> listar() {
        List<Relatorio> relatorios = service.listar();
        if (relatorios.isEmpty()) {
            return ResponseEntity.status(204).build();
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
    public List<Relatorio> getRelatoriosPorAno(@PathVariable String ano) {
        return service.buscarRelatoriosPorAno(ano);
    }

    // /relatorios/periodo?de=01/2024&para=04/2024
    @GetMapping("/periodo")
    public List<Relatorio> getRelatoriosPorPeriodo(
            @RequestParam String de,
            @RequestParam String para
    ) {
        return service.buscarRelatoriosPorPeriodo(de, para);
    }

    @GetMapping("/ano/{ano}")
    public ResponseEntity<byte[]> exportarPorAno(@PathVariable String ano) throws IOException {
        List<Relatorio> relatorios = service.buscarRelatoriosPorAno(ano);
        return gerarResponseExcel(relatorios, "relatorio_ano_" + ano);
    }

    @GetMapping("/mes")
    public ResponseEntity<byte[]> exportarPorMes(@RequestParam String mesAno) throws IOException {
        List<Relatorio> relatorios = service.buscarRelatoriosPorPeriodo(mesAno, mesAno);
        return gerarResponseExcel(relatorios, "relatorio_mes_" + mesAno.replace("/", "-"));
    }

    @GetMapping("/periodo")
    public ResponseEntity<byte[]> exportarPorPeriodo(
            @RequestParam String de,
            @RequestParam String para) throws IOException {
        List<Relatorio> relatorios = service.buscarRelatoriosPorPeriodo(de, para);
        return gerarResponseExcel(relatorios, "relatorio_de_" + de + "_para_" + para);
    }

    private ResponseEntity<byte[]> gerarResponseExcel(List<Relatorio> relatorios, String nomeArquivo) throws IOException {
        byte[] excel = RelatorioExcelAdapter.exportarParaExcel(relatorios);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + nomeArquivo + ".xlsx")
                .header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .body(excel);
    }

}
