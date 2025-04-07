package school.sptech.Simbiosys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.Simbiosys.exception.EntidadeJaExistente;
import school.sptech.Simbiosys.exception.EntidadeNaoEncontradaException;
import school.sptech.Simbiosys.model.Relatorio;
import school.sptech.Simbiosys.repository.RelatorioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RelatorioService {

    @Autowired
    private RelatorioRepository repository;

    public List<Relatorio> listar(){
        return repository.findAll();
    }

    public Relatorio cadastrar(Relatorio relatorio){
        if(repository.existsByMesAno(relatorio.getMesAno())){
            throw new EntidadeJaExistente("Relatório com nome %s já cadastrado".formatted(relatorio.getMesAno()));
        }
        return repository.save(relatorio);
    }

    public Relatorio buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Relatório de id: %d não encontrado".formatted(id)));
    }

    public void deletar(Integer id){
        repository.deleteById(id);
        throw new EntidadeNaoEncontradaException(
                "Produto de id: %d não encontrado".formatted(id)
        );
    }

    public Relatorio atualizar(Integer id, Relatorio relatorioAtualizado) {
        Optional<Relatorio> relatorioExistenteOpt = repository.findById(id);
        if (relatorioExistenteOpt.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Relatório não encontrado");
        }

        Relatorio relatorioExistente = relatorioExistenteOpt.get();

        if (repository.existsByMesAno(relatorioAtualizado.getMesAno())) {
            throw new EntidadeJaExistente("Conflito de mes/ano");
        }

        if(!relatorioAtualizado.getMesAno().equals(relatorioExistente.getMesAno())){
            throw new EntidadeNaoEncontradaException("Relatório não encontrado");
        }

        atualizarCamposNaoNulos(relatorioExistente, relatorioAtualizado);

        return repository.save(relatorioExistente);
    }

    private void atualizarCamposNaoNulos(Relatorio existente, Relatorio atualizado) {
        if (atualizado.getMesAno() != null) existente.setMesAno(atualizado.getMesAno());
        if (atualizado.getEncaminhamento() != null) existente.setEncaminhamento(atualizado.getEncaminhamento());
        if (atualizado.getOutrosNumeros() != null) existente.setOutrosNumeros(atualizado.getOutrosNumeros());
        if (atualizado.getAcoesRealizadas() != null) existente.setAcoesRealizadas(atualizado.getAcoesRealizadas());
    }

    public Relatorio buscarPorMesAno(String mesAno) {
        if (!repository.existsByMesAno(mesAno)) {
            throw new EntidadeNaoEncontradaException("Relatório com mes/ano: %s não encontrado".formatted(mesAno));
        }
        return repository.findByMesAno(mesAno);
    }
}

