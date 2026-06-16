package DomainModel;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "manutencao")
public class Manutencao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idManutencao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    private String tipoManutencao;
    private String descricao;
    private String dataManutencao;
    private double quilometragem;
    private double valor;
    private String oficina;
    private String status;

    public int getIdManutencao() { return idManutencao; }
    public void setIdManutencao(int idManutencao) { this.idManutencao = idManutencao; }

    public Veiculo getVeiculo() { return veiculo; }
    public void setVeiculo(Veiculo veiculo) { this.veiculo = veiculo; }

    public String getTipoManutencao() { return tipoManutencao; }
    public void setTipoManutencao(String tipoManutencao) { this.tipoManutencao = tipoManutencao; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getDataManutencao() { return dataManutencao; }
    public void setDataManutencao(String dataManutencao) { this.dataManutencao = dataManutencao; }

    public double getQuilometragem() { return quilometragem; }
    public void setQuilometragem(double quilometragem) { this.quilometragem = quilometragem; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public String getOficina() { return oficina; }
    public void setOficina(String oficina) { this.oficina = oficina; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
