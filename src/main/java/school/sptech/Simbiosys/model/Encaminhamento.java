package school.sptech.Simbiosys.model;

import jakarta.persistence.*;

@Entity
public class Encaminhamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer encBeneficioPrestacaoContinuada;
    private Integer encAposentadoria;
    private Integer encAssistenciaSocial;
    private Integer encCursosProfissionalizantesForaOrganizacao;
    private Integer encCursosProfissionalizantesDentroOrganizacao;
    private Integer encEducacaoNaoFormal;
    private Integer encEducacaoFormal;
    private Integer encDocumentos;
    private Integer encTrabalho;
    private Integer encGeracaoRenda;
    private Integer encSaude;
    private Integer encTratamentoDrogas;
    private Integer encProgramasTransferenciaRenda;
    private Integer encPoliticasPublicas;

    public Encaminhamento() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEncBeneficioPrestacaoContinuada() {
        return encBeneficioPrestacaoContinuada;
    }

    public void setEncBeneficioPrestacaoContinuada(Integer encBeneficioPrestacaoContinuada) {
        this.encBeneficioPrestacaoContinuada = encBeneficioPrestacaoContinuada;
    }

    public Integer getEncAposentadoria() {
        return encAposentadoria;
    }

    public void setEncAposentadoria(Integer encAposentadoria) {
        this.encAposentadoria = encAposentadoria;
    }

    public Integer getEncAssistenciaSocial() {
        return encAssistenciaSocial;
    }

    public void setEncAssistenciaSocial(Integer encAssistenciaSocial) {
        this.encAssistenciaSocial = encAssistenciaSocial;
    }

    public Integer getEncCursosProfissionalizantesForaOrganizacao() {
        return encCursosProfissionalizantesForaOrganizacao;
    }

    public void setEncCursosProfissionalizantesForaOrganizacao(Integer encCursosProfissionalizantesForaOrganizacao) {
        this.encCursosProfissionalizantesForaOrganizacao = encCursosProfissionalizantesForaOrganizacao;
    }

    public Integer getEncCursosProfissionalizantesDentroOrganizacao() {
        return encCursosProfissionalizantesDentroOrganizacao;
    }

    public void setEncCursosProfissionalizantesDentroOrganizacao(Integer encCursosProfissionalizantesDentroOrganizacao) {
        this.encCursosProfissionalizantesDentroOrganizacao = encCursosProfissionalizantesDentroOrganizacao;
    }

    public Integer getEncEducacaoNaoFormal() {
        return encEducacaoNaoFormal;
    }

    public void setEncEducacaoNaoFormal(Integer encEducacaoNaoFormal) {
        this.encEducacaoNaoFormal = encEducacaoNaoFormal;
    }

    public Integer getEncEducacaoFormal() {
        return encEducacaoFormal;
    }

    public void setEncEducacaoFormal(Integer encEducacaoFormal) {
        this.encEducacaoFormal = encEducacaoFormal;
    }

    public Integer getEncDocumentos() {
        return encDocumentos;
    }

    public void setEncDocumentos(Integer encDocumentos) {
        this.encDocumentos = encDocumentos;
    }

    public Integer getEncTrabalho() {
        return encTrabalho;
    }

    public void setEncTrabalho(Integer encTrabalho) {
        this.encTrabalho = encTrabalho;
    }

    public Integer getEncGeracaoRenda() {
        return encGeracaoRenda;
    }

    public void setEncGeracaoRenda(Integer encGeracaoRenda) {
        this.encGeracaoRenda = encGeracaoRenda;
    }

    public Integer getEncSaude() {
        return encSaude;
    }

    public void setEncSaude(Integer encSaude) {
        this.encSaude = encSaude;
    }

    public Integer getEncTratamentoDrogas() {
        return encTratamentoDrogas;
    }

    public void setEncTratamentoDrogas(Integer encTratamentoDrogas) {
        this.encTratamentoDrogas = encTratamentoDrogas;
    }

    public Integer getEncProgramasTransferenciaRenda() {
        return encProgramasTransferenciaRenda;
    }

    public void setEncProgramasTransferenciaRenda(Integer encProgramasTransferenciaRenda) {
        this.encProgramasTransferenciaRenda = encProgramasTransferenciaRenda;
    }

    public Integer getEncPoliticasPublicas() {
        return encPoliticasPublicas;
    }

    public void setEncPoliticasPublicas(Integer encPoliticasPublicas) {
        this.encPoliticasPublicas = encPoliticasPublicas;
    }
}
