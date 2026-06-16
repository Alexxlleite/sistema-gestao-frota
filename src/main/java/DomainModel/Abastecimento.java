package DomainModel;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "abastecimento")
public class Abastecimento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAbastecimento;

    @ManyToOne(optional = false)
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @ManyToOne
    @JoinColumn(name = "motorista_id")
    private Motorista motorista;

    private String tipoCombustivel;
    private double quantidadeLitros;
    private double valorTotal;
    private String dataAbastecimento;
    private double quilometragem;

    public int getIdAbastecimento() { return idAbastecimento; }
    public void setIdAbastecimento(int idAbastecimento) { this.idAbastecimento = idAbastecimento; }

    public Veiculo getVeiculo() { return veiculo; }
    public void setVeiculo(Veiculo veiculo) { this.veiculo = veiculo; }

    public Motorista getMotorista() { return motorista; }
    public void setMotorista(Motorista motorista) { this.motorista = motorista; }

    public String getTipoCombustivel() { return tipoCombustivel; }
    public void setTipoCombustivel(String tipoCombustivel) { this.tipoCombustivel = tipoCombustivel; }

    public double getQuantidadeLitros() { return quantidadeLitros; }
    public void setQuantidadeLitros(double quantidadeLitros) { this.quantidadeLitros = quantidadeLitros; }

    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }

    public String getDataAbastecimento() { return dataAbastecimento; }
    public void setDataAbastecimento(String dataAbastecimento) { this.dataAbastecimento = dataAbastecimento; }

    public double getQuilometragem() { return quilometragem; }
    public void setQuilometragem(double quilometragem) { this.quilometragem = quilometragem; }

    public double calcularValorPorLitro() {
        if (quantidadeLitros == 0) {
            return 0;
        }
        return valorTotal / quantidadeLitros;
    }
}
