package DomainModel;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "viagem")
public class Viagem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idViagem;

    @ManyToOne(optional = false)
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "motorista_id")
    private Motorista motorista;

    private String localSaida;
    private String localDestino;
    private String dataHoraSaida;
    private String dataHoraRetorno;
    private double quilometragemInicial;
    private double quilometragemFinal;
    private String observacoes;
    private String status;

    public int getIdViagem() { return idViagem; }
    public void setIdViagem(int idViagem) { this.idViagem = idViagem; }

    public Veiculo getVeiculo() { return veiculo; }
    public void setVeiculo(Veiculo veiculo) { this.veiculo = veiculo; }

    public Motorista getMotorista() { return motorista; }
    public void setMotorista(Motorista motorista) { this.motorista = motorista; }

    public String getLocalSaida() { return localSaida; }
    public void setLocalSaida(String localSaida) { this.localSaida = localSaida; }

    public String getLocalDestino() { return localDestino; }
    public void setLocalDestino(String localDestino) { this.localDestino = localDestino; }

    public String getDataHoraSaida() { return dataHoraSaida; }
    public void setDataHoraSaida(String dataHoraSaida) { this.dataHoraSaida = dataHoraSaida; }

    public String getDataHoraRetorno() { return dataHoraRetorno; }
    public void setDataHoraRetorno(String dataHoraRetorno) { this.dataHoraRetorno = dataHoraRetorno; }

    public double getQuilometragemInicial() { return quilometragemInicial; }
    public void setQuilometragemInicial(double quilometragemInicial) { this.quilometragemInicial = quilometragemInicial; }

    public double getQuilometragemFinal() { return quilometragemFinal; }
    public void setQuilometragemFinal(double quilometragemFinal) { this.quilometragemFinal = quilometragemFinal; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double calcularQuilometragemPercorrida() {
        return quilometragemFinal - quilometragemInicial;
    }
}
