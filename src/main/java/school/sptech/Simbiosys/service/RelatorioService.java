package school.sptech.Simbiosys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import school.sptech.Simbiosys.event.RelatorioCriadoEvent;
import school.sptech.Simbiosys.exception.EntidadeJaExistente;
import school.sptech.Simbiosys.exception.EntidadeNaoEncontradaException;
import school.sptech.Simbiosys.exception.RelatorioNaoEncontradoException;
import school.sptech.Simbiosys.model.AcoesRealizadas;
import school.sptech.Simbiosys.model.Encaminhamento;
import school.sptech.Simbiosys.model.OutrosNumeros;
import school.sptech.Simbiosys.model.Relatorio;
import school.sptech.Simbiosys.repository.RelatorioRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RelatorioService {

    @Autowired
    private RelatorioRepository repository;
    private final ApplicationEventPublisher publisher;

    public RelatorioService(RelatorioRepository repository, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    public Relatorio cadastrar(Relatorio relatorio){
        if(repository.existsByMesAno(relatorio.getMesAno())){
            throw new EntidadeJaExistente("Relatório com nome %s já cadastrado".formatted(relatorio.getMesAno()));
        }
        Relatorio salvo = repository.save(relatorio);

        publisher.publishEvent(new RelatorioCriadoEvent(this, salvo));

        return salvo;
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

    public Relatorio somarRelatoriosPorAno(String ano) {
        List<Relatorio> relatorios = repository.findByAno(ano);

        if (relatorios.isEmpty()) {
            return null;
        }

        Relatorio relatorioSomado = new Relatorio();
        relatorioSomado.setMesAno("Ano " + ano);
        relatorioSomado.setDataAtualizacao(LocalDateTime.now());
        relatorioSomado.setUsuario(null);
        Encaminhamento encaminhamentoTotal = new Encaminhamento();
        OutrosNumeros outrosNumerosTotal = new OutrosNumeros();
        AcoesRealizadas acoesRealizadasTotal = new AcoesRealizadas();

        for (Relatorio r : relatorios) {
            Encaminhamento e = r.getEncaminhamento();
            if (e != null) {
                encaminhamentoTotal.somar(e);
            }

            OutrosNumeros o = r.getOutrosNumeros();
            if (o != null) {
                outrosNumerosTotal.somar(o);
            }

            AcoesRealizadas a = r.getAcoesRealizadas();
            if (a != null) {
                acoesRealizadasTotal.somar(a);
            }
        }

        relatorioSomado.setEncaminhamento(encaminhamentoTotal);
        relatorioSomado.setOutrosNumeros(outrosNumerosTotal);
        relatorioSomado.setAcoesRealizadas(acoesRealizadasTotal);

        return relatorioSomado;
    }

    public Relatorio somarRelatoriosPorPeriodo(String de, String para) {
        List<Relatorio> relatorios = repository.findByPeriodo(de, para);

        System.out.println("Relatórios encontrados: " + relatorios.size());

        if (relatorios.isEmpty()) {
            throw new RelatorioNaoEncontradoException("Nenhum relatório encontrado para o período de " + de + " até " + para);
        }

        Relatorio relatorioSomado = new Relatorio();
        relatorioSomado.setMesAno(de + " até " + para);

        for (Relatorio r : relatorios) {
            relatorioSomado.somar(r);
        }

        return relatorioSomado;
    }

    public List<Relatorio> buscarRelatoriosPorAno(String ano) {
        return repository.findByAno(ano);
    }

    public List<Relatorio> buscarRelatoriosPorPeriodo(String de, String para) {
        return repository.findByPeriodo(de, para);
    }
}

