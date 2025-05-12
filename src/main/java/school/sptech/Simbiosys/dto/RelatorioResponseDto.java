package school.sptech.Simbiosys.dto;

import school.sptech.Simbiosys.model.AcoesRealizadas;
import school.sptech.Simbiosys.model.Encaminhamento;
import school.sptech.Simbiosys.model.OutrosNumeros;
import school.sptech.Simbiosys.model.Relatorio;

import java.time.LocalDateTime;

public class RelatorioResponseDto {

    private Integer id;
    private String mesAno;
    private LocalDateTime dataAtualizacao;
    private Boolean aberto;
    private Encaminhamento encaminhamento;
    private OutrosNumeros outrosNumeros;
    private AcoesRealizadas acoesRealizadas;
    private UsuarioResponseDto usuario;

    public RelatorioResponseDto(Relatorio relatorio) {
        this.id = relatorio.getId();
        this.mesAno = relatorio.getMesAno();
        this.dataAtualizacao = relatorio.getDataAtualizacao();
        this.aberto = relatorio.getAberto();
        this.encaminhamento = relatorio.getEncaminhamento();
        this.outrosNumeros = relatorio.getOutrosNumeros();
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

    public Encaminhamento getEncaminhamento() {
        return encaminhamento;
    }

    public void setEncaminhamento(Encaminhamento encaminhamento) {
        this.encaminhamento = encaminhamento;
    }

    public OutrosNumeros getOutrosNumeros() {
        return outrosNumeros;
    }

    public void setOutrosNumeros(OutrosNumeros outrosNumeros) {
        this.outrosNumeros = outrosNumeros;
    }

    public AcoesRealizadas getAcoesRealizadas() {
        return acoesRealizadas;
    }

    public void setAcoesRealizadas(AcoesRealizadas acoesRealizadas) {
        this.acoesRealizadas = acoesRealizadas;
    }

    public UsuarioResponseDto getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResponseDto usuario) {
        this.usuario = usuario;
    }
}
