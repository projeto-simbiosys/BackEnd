package school.sptech.Simbiosys.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Relatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "mes_ano", unique = true, nullable = false)
    private String mesAno;

    private LocalDateTime dataAtualizacao;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fkencaminhamento")
    private Encaminhamento encaminhamento;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fkoutros_numeros")
    private OutrosNumeros outrosNumeros;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fkacoes_realizadas")
    private AcoesRealizadas acoesRealizadas;

    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;

    public Relatorio() {

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
