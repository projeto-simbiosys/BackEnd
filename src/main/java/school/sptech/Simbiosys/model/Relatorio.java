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

    @Column(name = "aberto")
    private Boolean aberto = true;

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

    public Boolean getAberto() {
        return aberto;
    }

    public void setAberto(Boolean aberto) {
        this.aberto = aberto;
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

    public void somar(Relatorio outro) {
        if (outro == null) return;

        if (this.encaminhamento == null) {
            this.encaminhamento = new Encaminhamento();
        }
        if (outro.getEncaminhamento() != null) {
            this.encaminhamento.somar(outro.getEncaminhamento());
        }

        if (this.outrosNumeros == null) {
            this.outrosNumeros = new OutrosNumeros();
        }
        if (outro.getOutrosNumeros() != null) {
            this.outrosNumeros.somar(outro.getOutrosNumeros());
        }

        if (this.acoesRealizadas == null) {
            this.acoesRealizadas = new AcoesRealizadas();
        }
        if (outro.getAcoesRealizadas() != null) {
            this.acoesRealizadas.somar(outro.getAcoesRealizadas());
        }
    }
}
