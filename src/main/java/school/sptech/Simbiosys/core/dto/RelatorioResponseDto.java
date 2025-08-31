package school.sptech.Simbiosys.core.dto;

import school.sptech.Simbiosys.infrastructure.persistence.entity.AcoesRealizadasEntity;
import school.sptech.Simbiosys.infrastructure.persistence.entity.EncaminhamentoEntity;
import school.sptech.Simbiosys.infrastructure.persistence.entity.OutrosNumerosEntity;
import school.sptech.Simbiosys.infrastructure.persistence.entity.RelatorioEntity;

import java.time.LocalDateTime;

public class RelatorioResponseDto {

    private Integer id;
    private String mesAno;
    private LocalDateTime dataAtualizacao;
    private Boolean aberto;
    private EncaminhamentoEntity encaminhamento;
    private OutrosNumerosEntity outrosNumerosEntity;
    private AcoesRealizadasEntity acoesRealizadas;
    private UsuarioResponseDto usuario;

    public RelatorioResponseDto(RelatorioEntity relatorio) {
        this.id = relatorio.getId();
        this.mesAno = relatorio.getMesAno();
        this.dataAtualizacao = relatorio.getDataAtualizacao();
        this.aberto = relatorio.getAberto();
        this.encaminhamento = relatorio.getEncaminhamento();
        this.outrosNumerosEntity = relatorio.getOutrosNumeros();
        this.acoesRealizadas = relatorio.getAcoesRealizadas();
        this.usuario = new UsuarioResponseDto(relatorio.getUsuario());
    }

    public RelatorioResponseDto() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMesAno() {
        return mesAno;
    }

    public void setMesAno(String mesAno) {
        this.mesAno = mesAno;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Boolean getAberto() {
        return aberto;
    }

    public void setAberto(Boolean aberto) {
        this.aberto = aberto;
    }

    public EncaminhamentoEntity getEncaminhamento() {
        return encaminhamento;
    }

    public void setEncaminhamento(EncaminhamentoEntity encaminhamento) {
        this.encaminhamento = encaminhamento;
    }

    public OutrosNumerosEntity getOutrosNumeros() {
        return outrosNumerosEntity;
    }

    public void setOutrosNumeros(OutrosNumerosEntity outrosNumerosEntity) {
        this.outrosNumerosEntity = outrosNumerosEntity;
    }

    public AcoesRealizadasEntity getAcoesRealizadas() {
        return acoesRealizadas;
    }

    public void setAcoesRealizadas(AcoesRealizadasEntity acoesRealizadas) {
        this.acoesRealizadas = acoesRealizadas;
    }

    public UsuarioResponseDto getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResponseDto usuario) {
        this.usuario = usuario;
    }
}
