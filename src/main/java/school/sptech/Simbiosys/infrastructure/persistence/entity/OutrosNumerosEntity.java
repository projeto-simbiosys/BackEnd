package school.sptech.Simbiosys.infrastructure.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "outros_numeros")
public class OutrosNumerosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer alimentacao;
    private Integer numeroDePessoasPresencial;
    private Integer cestasBasicasDoadas;
    private Integer kitsHigieneDoados;
    private Integer totalParticipantesAtividadeDistancia;
    private Integer totalParticipantesAtividadePresencial;

    public OutrosNumerosEntity() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAlimentacao() {
        return alimentacao;
    }

    public void setAlimentacao(Integer alimentacao) {
        this.alimentacao = alimentacao;
    }

    public Integer getNumeroDePessoasPresencial() {
        return numeroDePessoasPresencial;
    }

    public void setNumeroDePessoasPresencial(Integer numeroDePessoasPresencial) {
        this.numeroDePessoasPresencial = numeroDePessoasPresencial;
    }

    public Integer getCestasBasicasDoadas() {
        return cestasBasicasDoadas;
    }

    public void setCestasBasicasDoadas(Integer cestasBasicasDoadas) {
        this.cestasBasicasDoadas = cestasBasicasDoadas;
    }

    public Integer getKitsHigieneDoados() {
        return kitsHigieneDoados;
    }

    public void setKitsHigieneDoados(Integer kitsHigieneDoados) {
        this.kitsHigieneDoados = kitsHigieneDoados;
    }

    public Integer getTotalParticipantesAtividadeDistancia() {
        return totalParticipantesAtividadeDistancia;
    }

    public void setTotalParticipantesAtividadeDistancia(Integer totalParticipantesAtividadeDistancia) {
        this.totalParticipantesAtividadeDistancia = totalParticipantesAtividadeDistancia;
    }

    public Integer getTotalParticipantesAtividadePresencial() {
        return totalParticipantesAtividadePresencial;
    }

    public void setTotalParticipantesAtividadePresencial(Integer totalParticipantesAtividadePresencial) {
        this.totalParticipantesAtividadePresencial = totalParticipantesAtividadePresencial;
    }

    public void somar(OutrosNumerosEntity outro) {
        if (outro == null) return;

        this.alimentacao = safeSum(this.alimentacao, outro.getAlimentacao());
        this.numeroDePessoasPresencial = safeSum(this.numeroDePessoasPresencial, outro.getNumeroDePessoasPresencial());
        this.cestasBasicasDoadas = safeSum(this.cestasBasicasDoadas, outro.getCestasBasicasDoadas());
        this.kitsHigieneDoados = safeSum(this.kitsHigieneDoados, outro.getKitsHigieneDoados());
        this.totalParticipantesAtividadeDistancia = safeSum(this.totalParticipantesAtividadeDistancia, outro.getTotalParticipantesAtividadeDistancia());
        this.totalParticipantesAtividadePresencial = safeSum(this.totalParticipantesAtividadePresencial, outro.getTotalParticipantesAtividadePresencial());
    }

    private Integer safeSum(Integer a, Integer b) {
        int safeA = (a != null) ? a : 0;
        int safeB = (b != null) ? b : 0;
        return safeA + safeB;
    }
}
