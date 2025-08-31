package school.sptech.Simbiosys.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class RelatorioEntity {

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
    private EncaminhamentoEntity encaminhamento;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fkoutros_numeros")
    private OutrosNumerosEntity outrosNumerosEntity;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fkacoes_realizadas")
    private AcoesRealizadasEntity acoesRealizadas;

    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private UsuarioEntity usuario;

    public RelatorioEntity() {

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

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public void somar(RelatorioEntity outro) {
        if (outro == null) return;

        if (this.encaminhamento == null) {
            this.encaminhamento = new EncaminhamentoEntity();
        }
        if (outro.getEncaminhamento() != null) {
            this.encaminhamento.somar(outro.getEncaminhamento());
        }

        if (this.outrosNumerosEntity == null) {
            this.outrosNumerosEntity = new OutrosNumerosEntity();
        }
        if (outro.getOutrosNumeros() != null) {
            this.outrosNumerosEntity.somar(outro.getOutrosNumeros());
        }

        if (this.acoesRealizadas == null) {
            this.acoesRealizadas = new AcoesRealizadasEntity();
        }
        if (outro.getAcoesRealizadas() != null) {
            this.acoesRealizadas.somar(outro.getAcoesRealizadas());
        }
    }
}
