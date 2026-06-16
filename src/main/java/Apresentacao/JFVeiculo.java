package Apresentacao;

import DataAccess.VeiculoDAO;
import DomainModel.Veiculo;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class JFVeiculo extends JFrame {
    private JTextField id = new JTextField();
    private JTextField placa = new JTextField();
    private JTextField marca = new JTextField();
    private JTextField modelo = new JTextField();
    private JTextField ano = new JTextField();
    private JTextField tipo = new JTextField();
    private JTextField cor = new JTextField();
    private JTextField km = new JTextField();
    private JTextField status = new JTextField("Disponível");
    private JTable tabela = new JTable();

    public JFVeiculo() {
        setTitle("Cadastro de Veículos");
        setSize(850, 520);
        setLocationRelativeTo(null);

        id.setEnabled(false);

        JPanel form = new JPanel(new GridLayout(9, 2, 5, 5));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        form.add(new JLabel("Código:")); form.add(id);
        form.add(new JLabel("Placa:")); form.add(placa);
        form.add(new JLabel("Marca:")); form.add(marca);
        form.add(new JLabel("Modelo:")); form.add(modelo);
        form.add(new JLabel("Ano:")); form.add(ano);
        form.add(new JLabel("Tipo:")); form.add(tipo);
        form.add(new JLabel("Cor:")); form.add(cor);
        form.add(new JLabel("Quilometragem:")); form.add(km);
        form.add(new JLabel("Status:")); form.add(status);

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
        botoes.add(salvar);
        botoes.add(buscar);
        botoes.add(excluir);
        botoes.add(limpar);
        botoes.add(listar);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);
        listar();
    }

    private void salvar() {
        try {
            Veiculo v = new Veiculo();
            v.setPlaca(placa.getText());
            v.setMarca(marca.getText());
            v.setModelo(modelo.getText());
            v.setAno(UtilTela.inteiro(ano));
            v.setTipo(tipo.getText());
            v.setCor(cor.getText());
            v.setQuilometragemAtual(UtilTela.decimal(km));
            v.setStatus(status.getText());

            if (id.getText().isEmpty()) {
                VeiculoDAO.getInstance().persist(v);
            } else {
                v.setIdVeiculo(Integer.parseInt(id.getText()));
                VeiculoDAO.getInstance().merge(v);
            }

            limpar();
            listar();
            UtilTela.mensagem(this, "Veículo salvo com sucesso!");
        } catch (Exception ex) {
            UtilTela.mensagem(this, "Erro ao salvar veículo: " + ex.getMessage());
        }
    }

    private void buscar() {
        try {
            Integer codigo = UtilTela.pedirId(this, "Informe o código do veículo:");
            if (codigo == null) return;
            Veiculo v = VeiculoDAO.getInstance().getById(codigo);
            if (v == null) {
                UtilTela.mensagem(this, "Veículo não encontrado.");
                return;
            }
            id.setText(String.valueOf(v.getIdVeiculo()));
            placa.setText(v.getPlaca());
            marca.setText(v.getMarca());
            modelo.setText(v.getModelo());
            ano.setText(String.valueOf(v.getAno()));
            tipo.setText(v.getTipo());
            cor.setText(v.getCor());
            km.setText(String.valueOf(v.getQuilometragemAtual()));
            status.setText(v.getStatus());
        } catch (Exception ex) {
            UtilTela.mensagem(this, "Erro ao buscar: " + ex.getMessage());
        }
    }

    private void excluir() {
        try {
            if (id.getText().isEmpty()) {
                UtilTela.mensagem(this, "Busque um veículo antes de excluir.");
                return;
            }
            VeiculoDAO.getInstance().removeById(Integer.parseInt(id.getText()));
            limpar();
            listar();
            UtilTela.mensagem(this, "Veículo excluído.");
        } catch (Exception ex) {
            UtilTela.mensagem(this, "Erro ao excluir: " + ex.getMessage());
        }
    }

    private void limpar() {
        id.setText("");
        placa.setText("");
        marca.setText("");
        modelo.setText("");
        ano.setText("");
        tipo.setText("");
        cor.setText("");
        km.setText("");
        status.setText("Disponível");
    }

    private void listar() {
        List<Veiculo> lista = VeiculoDAO.getInstance().findAll();
        DefaultTableModel modeloTabela = new DefaultTableModel(
                new Object[]{"ID", "Placa", "Marca", "Modelo", "Ano", "Status"}, 0);
        for (Veiculo v : lista) {
            modeloTabela.addRow(new Object[]{
                    v.getIdVeiculo(), v.getPlaca(), v.getMarca(), v.getModelo(), v.getAno(), v.getStatus()
            });
        }
        tabela.setModel(modeloTabela);
    }
}
