package Apresentacao;

import DataAccess.AbastecimentoDAO;
import DataAccess.ManutencaoDAO;
import DataAccess.MotoristaDAO;
import DataAccess.VeiculoDAO;
import DataAccess.ViagemDAO;
import DomainModel.Abastecimento;
import DomainModel.Manutencao;
import DomainModel.Motorista;
import DomainModel.Veiculo;
import DomainModel.Viagem;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class JFRelatorios extends JFrame {

    private JTable tabela;

    public JFRelatorios() {
        setTitle("Relatórios do Sistema");
        setSize(950, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painelBotoes = new JPanel(new GridLayout(1, 5, 8, 8));
        painelBotoes.setBorder(new EmptyBorder(15, 15, 15, 15));

        JButton btnVeiculos = new JButton("Veículos");
        JButton btnMotoristas = new JButton("Motoristas");
        JButton btnViagens = new JButton("Viagens");
        JButton btnManutencoes = new JButton("Manutenções");
        JButton btnAbastecimentos = new JButton("Abastecimentos");

        painelBotoes.add(btnVeiculos);
        painelBotoes.add(btnMotoristas);
        painelBotoes.add(btnViagens);
        painelBotoes.add(btnManutencoes);
        painelBotoes.add(btnAbastecimentos);

        tabela = new JTable();

        add(painelBotoes, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        btnVeiculos.addActionListener(e -> relatorioVeiculos());
        btnMotoristas.addActionListener(e -> relatorioMotoristas());
        btnViagens.addActionListener(e -> relatorioViagens());
        btnManutencoes.addActionListener(e -> relatorioManutencoes());
        btnAbastecimentos.addActionListener(e -> relatorioAbastecimentos());

        relatorioVeiculos();
    }

    private void relatorioVeiculos() {
        List<Veiculo> lista = VeiculoDAO.getInstance().findAll();

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Placa");
        modelo.addColumn("Marca");
        modelo.addColumn("Modelo");
        modelo.addColumn("Ano");
        modelo.addColumn("Km Atual");
        modelo.addColumn("Status");

        for (Veiculo v : lista) {
            modelo.addRow(new Object[]{
                v.getIdVeiculo(),
                v.getPlaca(),
                v.getMarca(),
                v.getModelo(),
                v.getAno(),
                v.getQuilometragemAtual(),
                v.getStatus()
            });
        }

        tabela.setModel(modelo);
    }

    private void relatorioMotoristas() {
        List<Motorista> lista = MotoristaDAO.getInstance().findAll();

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nome");
        modelo.addColumn("CPF");
        modelo.addColumn("CNH");
        modelo.addColumn("Categoria");
        modelo.addColumn("Telefone");
        modelo.addColumn("Status");

        for (Motorista m : lista) {
            modelo.addRow(new Object[]{
                m.getIdMotorista(),
                m.getNome(),
                m.getCpf(),
                m.getCnh(),
                m.getCategoriaCnh(),
                m.getTelefone(),
                m.getStatus()
            });
        }

        tabela.setModel(modelo);
    }

    private void relatorioViagens() {
        List<Viagem> lista = ViagemDAO.getInstance().findAll();

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Veículo");
        modelo.addColumn("Motorista");
        modelo.addColumn("Saída");
        modelo.addColumn("Destino");
        modelo.addColumn("Data Saída");
        modelo.addColumn("Data Retorno");
        modelo.addColumn("Status");

        for (Viagem v : lista) {
            modelo.addRow(new Object[]{
                v.getIdViagem(),
                v.getVeiculo() != null ? v.getVeiculo().getPlaca() : "",
                v.getMotorista() != null ? v.getMotorista().getNome() : "",
                v.getLocalSaida(),
                v.getLocalDestino(),
                v.getDataHoraSaida(),
                v.getDataHoraRetorno(),
                v.getStatus()
            });
        }

        tabela.setModel(modelo);
    }

    private void relatorioManutencoes() {
        List<Manutencao> lista = ManutencaoDAO.getInstance().findAll();

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Veículo");
        modelo.addColumn("Tipo");
        modelo.addColumn("Data");
        modelo.addColumn("Km");
        modelo.addColumn("Valor");
        modelo.addColumn("Oficina");
        modelo.addColumn("Status");

        for (Manutencao m : lista) {
            modelo.addRow(new Object[]{
                m.getIdManutencao(),
                m.getVeiculo() != null ? m.getVeiculo().getPlaca() : "",
                m.getTipoManutencao(),
                m.getDataManutencao(),
                m.getQuilometragem(),
                m.getValor(),
                m.getOficina(),
                m.getStatus()
            });
        }

        tabela.setModel(modelo);
    }

    private void relatorioAbastecimentos() {
        List<Abastecimento> lista = AbastecimentoDAO.getInstance().findAll();

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Veículo");
        modelo.addColumn("Motorista");
        modelo.addColumn("Combustível");
        modelo.addColumn("Litros");
        modelo.addColumn("Valor Total");
        modelo.addColumn("Data");
        modelo.addColumn("Km");

        for (Abastecimento a : lista) {
            modelo.addRow(new Object[]{
                a.getIdAbastecimento(),
                a.getVeiculo() != null ? a.getVeiculo().getPlaca() : "",
                a.getMotorista() != null ? a.getMotorista().getNome() : "",
                a.getTipoCombustivel(),
                a.getQuantidadeLitros(),
                a.getValorTotal(),
                a.getDataAbastecimento(),
                a.getQuilometragem()
            });
        }

        tabela.setModel(modelo);
    }
}