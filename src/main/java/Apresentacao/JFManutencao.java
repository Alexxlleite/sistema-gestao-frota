package Apresentacao;

import DataAccess.ManutencaoDAO;
import DataAccess.VeiculoDAO;
import DomainModel.Manutencao;
import DomainModel.Veiculo;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class JFManutencao extends JFrame {
    private JTextField id = new JTextField();
    private JTextField idVeiculo = new JTextField();
    private JTextField tipo = new JTextField();
    private JTextField data = new JTextField();
    private JTextField km = new JTextField();
    private JTextField valor = new JTextField();
    private JTextField oficina = new JTextField();
    private JTextField status = new JTextField("Realizada");
    private JTextArea descricao = new JTextArea(2, 20);
    private JTable tabela = new JTable();

    public JFManutencao() {
        setTitle("Controle de Manutenção");
        setSize(900, 540);
        setLocationRelativeTo(null);
        id.setEnabled(false);

        JPanel form = new JPanel(new GridLayout(9, 2, 5, 5));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        form.add(new JLabel("Código:")); form.add(id);
        form.add(new JLabel("ID Veículo:")); form.add(idVeiculo);
        form.add(new JLabel("Tipo de Manutenção:")); form.add(tipo);
        form.add(new JLabel("Descrição:")); form.add(new JScrollPane(descricao));
        form.add(new JLabel("Data:")); form.add(data);
        form.add(new JLabel("Quilometragem:")); form.add(km);
        form.add(new JLabel("Valor:")); form.add(valor);
        form.add(new JLabel("Oficina:")); form.add(oficina);
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
        botoes.add(salvar); botoes.add(buscar); botoes.add(excluir); botoes.add(limpar); botoes.add(listar);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);
        listar();
    }

    private void salvar() {
        try {
            Veiculo veiculo = VeiculoDAO.getInstance().getById(Integer.parseInt(idVeiculo.getText()));
            if (veiculo == null) {
                UtilTela.mensagem(this, "Veículo não encontrado.");
                return;
            }

            Manutencao m = new Manutencao();
            m.setVeiculo(veiculo);
            m.setTipoManutencao(tipo.getText());
            m.setDescricao(descricao.getText());
            m.setDataManutencao(data.getText());
            m.setQuilometragem(UtilTela.decimal(km));
            m.setValor(UtilTela.decimal(valor));
            m.setOficina(oficina.getText());
            m.setStatus(status.getText());

            if (id.getText().isEmpty()) {
                ManutencaoDAO.getInstance().persist(m);
            } else {
                m.setIdManutencao(Integer.parseInt(id.getText()));
                ManutencaoDAO.getInstance().merge(m);
            }

            limpar();
            listar();
            UtilTela.mensagem(this, "Manutenção salva com sucesso!");
        } catch (Exception ex) {
            UtilTela.mensagem(this, "Erro ao salvar manutenção: " + ex.getMessage());
        }
    }

    private void buscar() {
        try {
            Integer codigo = UtilTela.pedirId(this, "Informe o código da manutenção:");
            if (codigo == null) return;
            Manutencao m = ManutencaoDAO.getInstance().getById(codigo);
            if (m == null) {
                UtilTela.mensagem(this, "Manutenção não encontrada.");
                return;
            }
            id.setText(String.valueOf(m.getIdManutencao()));
            idVeiculo.setText(String.valueOf(m.getVeiculo().getIdVeiculo()));
            tipo.setText(m.getTipoManutencao());
            descricao.setText(m.getDescricao());
            data.setText(m.getDataManutencao());
            km.setText(String.valueOf(m.getQuilometragem()));
            valor.setText(String.valueOf(m.getValor()));
            oficina.setText(m.getOficina());
            status.setText(m.getStatus());
        } catch (Exception ex) {
            UtilTela.mensagem(this, "Erro ao buscar: " + ex.getMessage());
        }
    }

    private void excluir() {
        try {
            if (id.getText().isEmpty()) {
                UtilTela.mensagem(this, "Busque uma manutenção antes de excluir.");
                return;
            }
            ManutencaoDAO.getInstance().removeById(Integer.parseInt(id.getText()));
            limpar();
            listar();
            UtilTela.mensagem(this, "Manutenção excluída.");
        } catch (Exception ex) {
            UtilTela.mensagem(this, "Erro ao excluir: " + ex.getMessage());
        }
    }

    private void limpar() {
        id.setText(""); idVeiculo.setText(""); tipo.setText(""); descricao.setText("");
        data.setText(""); km.setText(""); valor.setText(""); oficina.setText(""); status.setText("Realizada");
    }

    private void listar() {
        List<Manutencao> lista = ManutencaoDAO.getInstance().findAll();
        DefaultTableModel modeloTabela = new DefaultTableModel(
                new Object[]{"ID", "Veículo", "Tipo", "Data", "Valor", "Status"}, 0);
        for (Manutencao m : lista) {
            modeloTabela.addRow(new Object[]{
                    m.getIdManutencao(),
                    m.getVeiculo() != null ? m.getVeiculo().getPlaca() : "",
                    m.getTipoManutencao(), m.getDataManutencao(), m.getValor(), m.getStatus()
            });
        }
        tabela.setModel(modeloTabela);
    }
}
