package school.sptech.Simbiosys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.Simbiosys.model.Relatorio;
import school.sptech.Simbiosys.repository.RelatorioRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioRepository repository;

    @PostMapping
    public ResponseEntity<Relatorio> cadastrar (@RequestBody Relatorio relatorio) {
        if (repository.existsByMesAno(relatorio.getMesAno()) || repository.existsById(relatorio.getId())) {
            return ResponseEntity.status(409).build();
        }
        relatorio.setId(null);
        Relatorio relatorioSalvo = repository.save(relatorio);
        return ResponseEntity.status(201).body(relatorioSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Relatorio>> listar() {
        List<Relatorio> relatorios = repository.findAll();
        if (relatorios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(relatorios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Relatorio> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.of(repository.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Integer id) {
        repository.deleteById(id);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Relatorio> atualizar(@PathVariable Integer id, @RequestBody Relatorio relatorioAtualizado) {

        Optional<Relatorio> relatorioExistenteOpt = repository.findById(id);
        if (relatorioExistenteOpt.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        Relatorio relatorioExistente = relatorioExistenteOpt.get();

        if (repository.existsByMesAno(relatorioAtualizado.getMesAno()) &&
                !relatorioAtualizado.getMesAno().equals(relatorioExistente.getMesAno())) {
            return ResponseEntity.status(409).body(null);
        }

        atualizarCamposNaoNulos(relatorioExistente, relatorioAtualizado);

        Relatorio relatorioSalvo = repository.save(relatorioExistente);
        return ResponseEntity.ok(relatorioSalvo);
    }

    private void atualizarCamposNaoNulos(Relatorio existente, Relatorio atualizado) {
        if (atualizado.getMesAno() != null) existente.setMesAno(atualizado.getMesAno());

        if (atualizado.getEncaminhamento() != null) existente.setEncaminhamento(atualizado.getEncaminhamento());
        if (atualizado.getOutrosNumeros() != null) existente.setOutrosNumeros(atualizado.getOutrosNumeros());
        if (atualizado.getAcoesRealizadas() != null) existente.setAcoesRealizadas(atualizado.getAcoesRealizadas());
    }

    @GetMapping("/mesAno/{mesAno}")
    public ResponseEntity<Relatorio> buscarPorMesAno(@PathVariable String mesAno) {
        if (!repository.existsByMesAno(mesAno)) {
            return ResponseEntity.status(404).body(null);
        }

        Relatorio relatorio = repository.findByMesAno(mesAno);
        return ResponseEntity.ok(relatorio);
    }

}
