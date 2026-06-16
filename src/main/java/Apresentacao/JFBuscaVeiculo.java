package Apresentacao;

import DataAccess.VeiculoDAO;
import DomainModel.Veiculo;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class JFBuscaVeiculo extends JFrame {

    private JTextField txtPlaca;
    private JTextField txtStatus;
    private JTable tabela;

    public JFBuscaVeiculo() {
        setTitle("Busca de Veículos");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painelCampos = new JPanel(new GridLayout(2, 3, 10, 10));
        painelCampos.setBorder(new EmptyBorder(15, 15, 15, 15));

        txtPlaca = new JTextField();
        txtStatus = new JTextField();

        JButton btnBuscarPlaca = new JButton("Buscar por Placa");
        JButton btnBuscarStatus = new JButton("Buscar por Status");

        painelCampos.add(new JLabel("Placa:"));
        painelCampos.add(txtPlaca);
        painelCampos.add(btnBuscarPlaca);

        painelCampos.add(new JLabel("Status:"));
        painelCampos.add(txtStatus);
        painelCampos.add(btnBuscarStatus);

        tabela = new JTable();

        JButton btnListarTodos = new JButton("Listar Todos");

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnListarTodos);

        add(painelCampos, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        btnBuscarPlaca.addActionListener(e -> buscarPorPlaca());
        btnBuscarStatus.addActionListener(e -> buscarPorStatus());
        btnListarTodos.addActionListener(e -> listarTodos());

        listarTodos();
    }

    private void buscarPorPlaca() {
        String placa = txtPlaca.getText();

        if (placa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe a placa para buscar.");
            return;
        }

        List<Veiculo> lista = VeiculoDAO.getInstance().buscarPorPlaca(placa);
        preencherTabela(lista);
    }

    private void buscarPorStatus() {
        String status = txtStatus.getText();

        if (status.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o status para buscar.");
            return;
        }

        List<Veiculo> lista = VeiculoDAO.getInstance().buscarPorStatus(status);
        preencherTabela(lista);
    }

    private void listarTodos() {
        List<Veiculo> lista = VeiculoDAO.getInstance().findAll();
        preencherTabela(lista);
    }

    private void preencherTabela(List<Veiculo> lista) {
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
}