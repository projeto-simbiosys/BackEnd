package school.sptech.Simbiosys.infrastructure.persistence.entity;

import jakarta.persistence.*;

@Entity
public class AcoesRealizadasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer totalAtividadesGrupoVirtual;
    private Integer totalAtividadesCulturaisExternas;
    private Integer totalAtividadesCulturaisVirtuais;

    private Integer totalPalestrasPresenciais;
    private Integer totalPalestrasVirtuais;

    private Integer totalVisitasFamiliaresPresenciais;
    private Integer totalVisitasFamiliaresVirtuais;

    private Integer totalVisitasMonitoradasPresenciais;
    private Integer totalVisitasMonitoradasVirtuais;

    private Integer totalCursosMinistradosPresenciais;
    private Integer totalCursosMinistradosVirtuais;

    private Integer totalPessoasCursosCapacitacaoPresenciais;
    private Integer totalPessoasCursosCapacitacaoVirtuais;
    private Integer totalPessoasCursosProfissionalizantesPresenciais;
    private Integer totalPessoasCursosProfissionalizantesVirtuais;

    public AcoesRealizadasEntity() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotalAtividadesGrupoVirtual() {
        return totalAtividadesGrupoVirtual;
    }

    public void setTotalAtividadesGrupoVirtual(Integer totalAtividadesGrupoVirtual) {
        this.totalAtividadesGrupoVirtual = totalAtividadesGrupoVirtual;
    }

    public Integer getTotalAtividadesCulturaisExternas() {
        return totalAtividadesCulturaisExternas;
    }

    public void setTotalAtividadesCulturaisExternas(Integer totalAtividadesCulturaisExternas) {
        this.totalAtividadesCulturaisExternas = totalAtividadesCulturaisExternas;
    }

    public Integer getTotalAtividadesCulturaisVirtuais() {
        return totalAtividadesCulturaisVirtuais;
    }

    public void setTotalAtividadesCulturaisVirtuais(Integer totalAtividadesCulturaisVirtuais) {
        this.totalAtividadesCulturaisVirtuais = totalAtividadesCulturaisVirtuais;
    }

    public Integer getTotalPalestrasPresenciais() {
        return totalPalestrasPresenciais;
    }

    public void setTotalPalestrasPresenciais(Integer totalPalestrasPresenciais) {
        this.totalPalestrasPresenciais = totalPalestrasPresenciais;
    }

    public Integer getTotalPalestrasVirtuais() {
        return totalPalestrasVirtuais;
    }

    public void setTotalPalestrasVirtuais(Integer totalPalestrasVirtuais) {
        this.totalPalestrasVirtuais = totalPalestrasVirtuais;
    }

    public Integer getTotalVisitasFamiliaresPresenciais() {
        return totalVisitasFamiliaresPresenciais;
    }

    public void setTotalVisitasFamiliaresPresenciais(Integer totalVisitasFamiliaresPresenciais) {
        this.totalVisitasFamiliaresPresenciais = totalVisitasFamiliaresPresenciais;
    }

    public Integer getTotalVisitasFamiliaresVirtuais() {
        return totalVisitasFamiliaresVirtuais;
    }

    public void setTotalVisitasFamiliaresVirtuais(Integer totalVisitasFamiliaresVirtuais) {
        this.totalVisitasFamiliaresVirtuais = totalVisitasFamiliaresVirtuais;
    }

    public Integer getTotalVisitasMonitoradasPresenciais() {
        return totalVisitasMonitoradasPresenciais;
    }

    public void setTotalVisitasMonitoradasPresenciais(Integer totalVisitasMonitoradasPresenciais) {
        this.totalVisitasMonitoradasPresenciais = totalVisitasMonitoradasPresenciais;
    }

    public Integer getTotalVisitasMonitoradasVirtuais() {
        return totalVisitasMonitoradasVirtuais;
    }

    public void setTotalVisitasMonitoradasVirtuais(Integer totalVisitasMonitoradasVirtuais) {
        this.totalVisitasMonitoradasVirtuais = totalVisitasMonitoradasVirtuais;
    }

    public Integer getTotalCursosMinistradosPresenciais() {
        return totalCursosMinistradosPresenciais;
    }

    public void setTotalCursosMinistradosPresenciais(Integer totalCursosMinistradosPresenciais) {
        this.totalCursosMinistradosPresenciais = totalCursosMinistradosPresenciais;
    }

    public Integer getTotalCursosMinistradosVirtuais() {
        return totalCursosMinistradosVirtuais;
    }

    public void setTotalCursosMinistradosVirtuais(Integer totalCursosMinistradosVirtuais) {
        this.totalCursosMinistradosVirtuais = totalCursosMinistradosVirtuais;
    }

    public Integer getTotalPessoasCursosCapacitacaoPresenciais() {
        return totalPessoasCursosCapacitacaoPresenciais;
    }

    public void setTotalPessoasCursosCapacitacaoPresenciais(Integer totalPessoasCursosCapacitacaoPresenciais) {
        this.totalPessoasCursosCapacitacaoPresenciais = totalPessoasCursosCapacitacaoPresenciais;
    }

    public Integer getTotalPessoasCursosCapacitacaoVirtuais() {
        return totalPessoasCursosCapacitacaoVirtuais;
    }

    public void setTotalPessoasCursosCapacitacaoVirtuais(Integer totalPessoasCursosCapacitacaoVirtuais) {
        this.totalPessoasCursosCapacitacaoVirtuais = totalPessoasCursosCapacitacaoVirtuais;
    }

    public Integer getTotalPessoasCursosProfissionalizantesPresenciais() {
        return totalPessoasCursosProfissionalizantesPresenciais;
    }

    public void setTotalPessoasCursosProfissionalizantesPresenciais(Integer totalPessoasCursosProfissionalizantesPresenciais) {
        this.totalPessoasCursosProfissionalizantesPresenciais = totalPessoasCursosProfissionalizantesPresenciais;
    }

    public Integer getTotalPessoasCursosProfissionalizantesVirtuais() {
        return totalPessoasCursosProfissionalizantesVirtuais;
    }

    public void setTotalPessoasCursosProfissionalizantesVirtuais(Integer totalPessoasCursosProfissionalizantesVirtuais) {
        this.totalPessoasCursosProfissionalizantesVirtuais = totalPessoasCursosProfissionalizantesVirtuais;
    }

    public void somar(AcoesRealizadasEntity outro) {
        if (outro == null) return;

        this.totalAtividadesGrupoVirtual = safeSum(this.totalAtividadesGrupoVirtual, outro.getTotalAtividadesGrupoVirtual());
        this.totalAtividadesCulturaisExternas = safeSum(this.totalAtividadesCulturaisExternas, outro.getTotalAtividadesCulturaisExternas());
        this.totalAtividadesCulturaisVirtuais = safeSum(this.totalAtividadesCulturaisVirtuais, outro.getTotalAtividadesCulturaisVirtuais());
        this.totalPalestrasPresenciais = safeSum(this.totalPalestrasPresenciais, outro.getTotalPalestrasPresenciais());
        this.totalPalestrasVirtuais = safeSum(this.totalPalestrasVirtuais, outro.getTotalPalestrasVirtuais());
        this.totalVisitasFamiliaresPresenciais = safeSum(this.totalVisitasFamiliaresPresenciais, outro.getTotalVisitasFamiliaresPresenciais());
        this.totalVisitasFamiliaresVirtuais = safeSum(this.totalVisitasFamiliaresVirtuais, outro.getTotalVisitasFamiliaresVirtuais());
        this.totalVisitasMonitoradasPresenciais = safeSum(this.totalVisitasMonitoradasPresenciais, outro.getTotalVisitasMonitoradasPresenciais());
        this.totalVisitasMonitoradasVirtuais = safeSum(this.totalVisitasMonitoradasVirtuais, outro.getTotalVisitasMonitoradasVirtuais());
        this.totalCursosMinistradosPresenciais = safeSum(this.totalCursosMinistradosPresenciais, outro.getTotalCursosMinistradosPresenciais());
        this.totalCursosMinistradosVirtuais = safeSum(this.totalCursosMinistradosVirtuais, outro.getTotalCursosMinistradosVirtuais());
        this.totalPessoasCursosCapacitacaoPresenciais = safeSum(this.totalPessoasCursosCapacitacaoPresenciais, outro.getTotalPessoasCursosCapacitacaoPresenciais());
        this.totalPessoasCursosCapacitacaoVirtuais = safeSum(this.totalPessoasCursosCapacitacaoVirtuais, outro.getTotalPessoasCursosCapacitacaoVirtuais());
        this.totalPessoasCursosProfissionalizantesPresenciais = safeSum(this.totalPessoasCursosProfissionalizantesPresenciais, outro.getTotalPessoasCursosProfissionalizantesPresenciais());
        this.totalPessoasCursosProfissionalizantesVirtuais = safeSum(this.totalPessoasCursosProfissionalizantesVirtuais, outro.getTotalPessoasCursosProfissionalizantesVirtuais());
    }

    private Integer safeSum(Integer a, Integer b) {
        int safeA = (a != null) ? a : 0;
        int safeB = (b != null) ? b : 0;
        return safeA + safeB;
    }
}

