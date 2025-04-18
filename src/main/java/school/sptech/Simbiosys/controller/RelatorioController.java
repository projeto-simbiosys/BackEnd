package school.sptech.Simbiosys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.Simbiosys.exception.DadosInvalidosException;
import school.sptech.Simbiosys.exception.EntidadeJaExistente;
import school.sptech.Simbiosys.exception.EntidadeNaoEncontradaException;
import school.sptech.Simbiosys.model.Relatorio;
import school.sptech.Simbiosys.repository.RelatorioRepository;
import school.sptech.Simbiosys.service.RelatorioService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/relatorios")
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

}
