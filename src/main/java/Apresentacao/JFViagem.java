package Apresentacao;

import DataAccess.MotoristaDAO;
import DataAccess.VeiculoDAO;
import DataAccess.ViagemDAO;
import DomainModel.Motorista;
import DomainModel.Veiculo;
import DomainModel.Viagem;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class JFViagem extends JFrame {
    private JTextField id = new JTextField();
    private JTextField idVeiculo = new JTextField();
    private JTextField idMotorista = new JTextField();
    private JTextField saida = new JTextField();
    private JTextField destino = new JTextField();
    private JTextField dataSaida = new JTextField();
    private JTextField dataRetorno = new JTextField();
    private JTextField kmInicial = new JTextField();
    private JTextField kmFinal = new JTextField();
    private JTextField status = new JTextField("Em andamento");
    private JTextArea observacoes = new JTextArea(2, 20);
    private JTable tabela = new JTable();

    public JFViagem() {
        setTitle("Registro de Viagens");
        setSize(950, 580);
        setLocationRelativeTo(null);
        id.setEnabled(false);

        JPanel form = new JPanel(new GridLayout(11, 2, 5, 5));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        form.add(new JLabel("Código:")); form.add(id);
        form.add(new JLabel("ID Veículo:")); form.add(idVeiculo);
        form.add(new JLabel("ID Motorista:")); form.add(idMotorista);
        form.add(new JLabel("Local de Saída:")); form.add(saida);
        form.add(new JLabel("Destino:")); form.add(destino);
        form.add(new JLabel("Data/Hora Saída:")); form.add(dataSaida);
        form.add(new JLabel("Data/Hora Retorno:")); form.add(dataRetorno);
        form.add(new JLabel("KM Inicial:")); form.add(kmInicial);
        form.add(new JLabel("KM Final:")); form.add(kmFinal);
        form.add(new JLabel("Status:")); form.add(status);
        form.add(new JLabel("Observações:")); form.add(new JScrollPane(observacoes));

        JButton salvar = new JButton("Salvar");
        JButton buscar = new JButton("Buscar");
        JButton excluir = new JButton("Excluir");
        JButton limpar = new JButton("Limpar");
        JButton listar = new JButton("Listar");

        salvar.addActionListener(e -> salvar());
        buscar.addActionListener(e -> buscar());
        excluir.addActionListener(e -> excluir());
        limpar.addActionListener(e -> limpar());
        listar.addActionListener(e -> listar());

        JPanel botoes = new JPanel();
        botoes.add(salvar); botoes.add(buscar); botoes.add(excluir); botoes.add(limpar); botoes.add(listar);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);
        listar();
    }

    private void salvar() {
        try {
            Veiculo veiculo = VeiculoDAO.getInstance().getById(Integer.parseInt(idVeiculo.getText()));
            Motorista motorista = MotoristaDAO.getInstance().getById(Integer.parseInt(idMotorista.getText()));
            if (veiculo == null || motorista == null) {
                UtilTela.mensagem(this, "Veículo ou motorista não encontrado.");
                return;
            }

            Viagem v = new Viagem();
            v.setVeiculo(veiculo);
            v.setMotorista(motorista);
            v.setLocalSaida(saida.getText());
            v.setLocalDestino(destino.getText());
            v.setDataHoraSaida(dataSaida.getText());
            v.setDataHoraRetorno(dataRetorno.getText());
            v.setQuilometragemInicial(UtilTela.decimal(kmInicial));
            v.setQuilometragemFinal(UtilTela.decimal(kmFinal));
            v.setObservacoes(observacoes.getText());
            v.setStatus(status.getText());

            if (id.getText().isEmpty()) {
                ViagemDAO.getInstance().persist(v);
            } else {
                v.setIdViagem(Integer.parseInt(id.getText()));
                ViagemDAO.getInstance().merge(v);
            }

            limpar();
            listar();
            UtilTela.mensagem(this, "Viagem salva com sucesso!");
        } catch (Exception ex) {
            UtilTela.mensagem(this, "Erro ao salvar viagem: " + ex.getMessage());
        }
    }

    private void buscar() {
        try {
            Integer codigo = UtilTela.pedirId(this, "Informe o código da viagem:");
            if (codigo == null) return;
            Viagem v = ViagemDAO.getInstance().getById(codigo);
            if (v == null) {
                UtilTela.mensagem(this, "Viagem não encontrada.");
                return;
            }
            id.setText(String.valueOf(v.getIdViagem()));
            idVeiculo.setText(String.valueOf(v.getVeiculo().getIdVeiculo()));
            idMotorista.setText(String.valueOf(v.getMotorista().getIdMotorista()));
            saida.setText(v.getLocalSaida());
            destino.setText(v.getLocalDestino());
            dataSaida.setText(v.getDataHoraSaida());
            dataRetorno.setText(v.getDataHoraRetorno());
            kmInicial.setText(String.valueOf(v.getQuilometragemInicial()));
            kmFinal.setText(String.valueOf(v.getQuilometragemFinal()));
            observacoes.setText(v.getObservacoes());
            status.setText(v.getStatus());
        } catch (Exception ex) {
            UtilTela.mensagem(this, "Erro ao buscar: " + ex.getMessage());
        }
    }

    private void excluir() {
        try {
            if (id.getText().isEmpty()) {
                UtilTela.mensagem(this, "Busque uma viagem antes de excluir.");
                return;
            }
            ViagemDAO.getInstance().removeById(Integer.parseInt(id.getText()));
            limpar();
            listar();
            UtilTela.mensagem(this, "Viagem excluída.");
        } catch (Exception ex) {
            UtilTela.mensagem(this, "Erro ao excluir: " + ex.getMessage());
        }
    }

    private void limpar() {
        id.setText(""); idVeiculo.setText(""); idMotorista.setText("");
        saida.setText(""); destino.setText(""); dataSaida.setText(""); dataRetorno.setText("");
        kmInicial.setText(""); kmFinal.setText(""); observacoes.setText(""); status.setText("Em andamento");
    }

    private void listar() {
        List<Viagem> lista = ViagemDAO.getInstance().findAll();
        DefaultTableModel modeloTabela = new DefaultTableModel(
                new Object[]{"ID", "Veículo", "Motorista", "Saída", "Destino", "Status"}, 0);
        for (Viagem v : lista) {
            modeloTabela.addRow(new Object[]{
                    v.getIdViagem(),
                    v.getVeiculo() != null ? v.getVeiculo().getPlaca() : "",
                    v.getMotorista() != null ? v.getMotorista().getNome() : "",
                    v.getLocalSaida(), v.getLocalDestino(), v.getStatus()
            });
        }
        tabela.setModel(modeloTabela);
    }
}
