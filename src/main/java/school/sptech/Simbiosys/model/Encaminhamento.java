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

    public void somar(Encaminhamento outro) {
        if (outro == null) return;

        this.encBeneficioPrestacaoContinuada = safeSum(this.encBeneficioPrestacaoContinuada, outro.getEncBeneficioPrestacaoContinuada());
        this.encAposentadoria = safeSum(this.encAposentadoria, outro.getEncAposentadoria());
        this.encAssistenciaSocial = safeSum(this.encAssistenciaSocial, outro.getEncAssistenciaSocial());
        this.encCursosProfissionalizantesForaOrganizacao = safeSum(this.encCursosProfissionalizantesForaOrganizacao, outro.getEncCursosProfissionalizantesForaOrganizacao());
        this.encCursosProfissionalizantesDentroOrganizacao = safeSum(this.encCursosProfissionalizantesDentroOrganizacao, outro.getEncCursosProfissionalizantesDentroOrganizacao());
        this.encEducacaoNaoFormal = safeSum(this.encEducacaoNaoFormal, outro.getEncEducacaoNaoFormal());
        this.encEducacaoFormal = safeSum(this.encEducacaoFormal, outro.getEncEducacaoFormal());
        this.encDocumentos = safeSum(this.encDocumentos, outro.getEncDocumentos());
        this.encTrabalho = safeSum(this.encTrabalho, outro.getEncTrabalho());
        this.encGeracaoRenda = safeSum(this.encGeracaoRenda, outro.getEncGeracaoRenda());
        this.encSaude = safeSum(this.encSaude, outro.getEncSaude());
        this.encTratamentoDrogas = safeSum(this.encTratamentoDrogas, outro.getEncTratamentoDrogas());
        this.encProgramasTransferenciaRenda = safeSum(this.encProgramasTransferenciaRenda, outro.getEncProgramasTransferenciaRenda());
        this.encPoliticasPublicas = safeSum(this.encPoliticasPublicas, outro.getEncPoliticasPublicas());
    }

    private Integer safeSum(Integer a, Integer b) {
        int safeA = (a != null) ? a : 0;
        int safeB = (b != null) ? b : 0;
        return safeA + safeB;
    }
}
