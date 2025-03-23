package school.sptech.Simbiosys.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class OutrosNumeros {
    private Integer alimentacao;
    private Integer numeroDePessoasPresencial;
    private Integer cestasBasicasDoadas;
    private Integer kitsHigieneDoados;
    private Integer totalParticipantesAtividadeDistancia;
    private Integer totalParticipantesAtividadePresencial;

    public OutrosNumeros() {}

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
}
