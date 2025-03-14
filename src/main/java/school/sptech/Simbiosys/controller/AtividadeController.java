package school.sptech.Simbiosys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.Simbiosys.model.Aluno;
import school.sptech.Simbiosys.model.Atividade;
import school.sptech.Simbiosys.repository.AtividadeRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/atividades")
public class AtividadeController {

    @Autowired
    private AtividadeRepository repository;

    @PostMapping
    public ResponseEntity<Atividade> cadastrar(@RequestBody Atividade atividade) {
        if (repository.findByNome(atividade.getNome()).isPresent()){
            return ResponseEntity.status(409).build();
        }
        atividade.setId(null);
        Atividade atividadeSalva = repository.save(atividade);
        return ResponseEntity.status(201).body(atividadeSalva);
    }

    @GetMapping
    public ResponseEntity<List<Atividade>> buscarTodas() {
        List<Atividade> atividades = repository.findAll();

        if (atividades.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(atividades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atividade> bucarPorId(@PathVariable Integer id){
        return ResponseEntity.of(repository.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Atividade> atualizar(@PathVariable Integer id, @RequestBody Atividade atividade) {
        Optional<Atividade> atividadeExistente = repository.findById(id);

        if (atividadeExistente.isEmpty()){
            return ResponseEntity.status(404).build();
        }
        if (!atividadeExistente.get().getNome().equalsIgnoreCase(atividade.getNome())){
            if (repository.findByNome(atividade.getNome()).isPresent()){
                return ResponseEntity.status(409).build();
            }
        }

        atividade.setId(id);
        Atividade atividadeAtualizada = repository.save(atividade);
        return ResponseEntity.status(200).body(atividadeAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if(repository.existsById(id)){
            repository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/filtro-data-inicio")
    public ResponseEntity<List<Atividade>> listarAtividadesPorPeriodo(
            @RequestParam LocalDate data1,
            @RequestParam LocalDate data2
    ){
        List<Atividade> atividades = repository.findByDataInicioBetween(data1, data2);

        if (atividades.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(atividades);
    }
}
