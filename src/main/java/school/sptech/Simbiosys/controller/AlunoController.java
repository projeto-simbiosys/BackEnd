package school.sptech.Simbiosys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.Simbiosys.model.Aluno;
import school.sptech.Simbiosys.repository.AlunoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository repository;

    @PostMapping
    public ResponseEntity<Aluno> criar(@RequestBody Aluno aluno) {
        if (repository.findByRgOrCpf(aluno.getRg(), aluno.getCpf()).isPresent()){
            return ResponseEntity.status(409).build();
        }
        aluno.setId(null);
        Aluno alunoSalvo = repository.save(aluno);
        return ResponseEntity.status(201).body(alunoSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Aluno>> buscarTodos() {
        List<Aluno> usuarios = repository.findAll();

        if (usuarios.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.of(repository.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizar(@PathVariable Integer id, @RequestBody Aluno aluno) {
        Optional<Aluno> alunoExistente = repository.findById(id);

        if (alunoExistente.isEmpty()){
            return ResponseEntity.status(404).build();
        }
        if (!alunoExistente.get().getRg().equalsIgnoreCase(aluno.getRg())){
            if (repository.findByRg(aluno.getRg()).isPresent()){
                return ResponseEntity.status(409).build();
            }
        } else if (!alunoExistente.get().getCpf().equalsIgnoreCase(aluno.getCpf())) {
            if (repository.findByCpf(aluno.getCpf()).isPresent()){
                return ResponseEntity.status(409).build();
            }
        }
        aluno.setId(id);
        Aluno alunoAtualizado = repository.save(aluno);
        return ResponseEntity.status(200).body(alunoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if(repository.existsById(id)){
            repository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/cpf")
    public ResponseEntity<Optional<Aluno>> buscarPorCpf(@RequestParam String cpf){
        Optional<Aluno> alunoCpf = repository.findByCpf(cpf);

        if (alunoCpf.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(alunoCpf);
    }

    @GetMapping("/escolaridade")
    public ResponseEntity<List<Aluno>> buscarPorEscolaridade(@RequestParam String escolaridade){
        List<Aluno> alunosEscolaridade = repository.findByEscolaridade(escolaridade);

        if (alunosEscolaridade.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(alunosEscolaridade);
    }
}
