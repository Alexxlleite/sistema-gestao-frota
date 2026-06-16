package Apresentacao;

import DataAccess.MotoristaDAO;
import DomainModel.Motorista;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class JFMotorista extends JFrame {
    private JTextField id = new JTextField();
    private JTextField nome = new JTextField();
    private JTextField cpf = new JTextField();
    private JTextField cnh = new JTextField();
    private JTextField categoria = new JTextField();
    private JTextField telefone = new JTextField();
    private JTextField endereco = new JTextField();
    private JTextField status = new JTextField("Ativo");
    private JTable tabela = new JTable();

    public JFMotorista() {
        setTitle("Cadastro de Motoristas");
        setSize(850, 520);
        setLocationRelativeTo(null);
        id.setEnabled(false);

        JPanel form = new JPanel(new GridLayout(8, 2, 5, 5));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        form.add(new JLabel("Código:")); form.add(id);
        form.add(new JLabel("Nome:")); form.add(nome);
        form.add(new JLabel("CPF:")); form.add(cpf);
        form.add(new JLabel("CNH:")); form.add(cnh);
        form.add(new JLabel("Categoria CNH:")); form.add(categoria);
        form.add(new JLabel("Telefone:")); form.add(telefone);
        form.add(new JLabel("Endereço:")); form.add(endereco);
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
            Motorista m = new Motorista();
            m.setNome(nome.getText());
            m.setCpf(cpf.getText());
            m.setCnh(cnh.getText());
            m.setCategoriaCnh(categoria.getText());
            m.setTelefone(telefone.getText());
            m.setEndereco(endereco.getText());
            m.setStatus(status.getText());

            if (id.getText().isEmpty()) {
                MotoristaDAO.getInstance().persist(m);
            } else {
                m.setIdMotorista(Integer.parseInt(id.getText()));
                MotoristaDAO.getInstance().merge(m);
            }

            limpar();
            listar();
            UtilTela.mensagem(this, "Motorista salvo com sucesso!");
        } catch (Exception ex) {
            UtilTela.mensagem(this, "Erro ao salvar motorista: " + ex.getMessage());
        }
    }

    private void buscar() {
        try {
            Integer codigo = UtilTela.pedirId(this, "Informe o código do motorista:");
            if (codigo == null) return;
            Motorista m = MotoristaDAO.getInstance().getById(codigo);
            if (m == null) {
                UtilTela.mensagem(this, "Motorista não encontrado.");
                return;
            }
            id.setText(String.valueOf(m.getIdMotorista()));
            nome.setText(m.getNome());
            cpf.setText(m.getCpf());
            cnh.setText(m.getCnh());
            categoria.setText(m.getCategoriaCnh());
            telefone.setText(m.getTelefone());
            endereco.setText(m.getEndereco());
            status.setText(m.getStatus());
        } catch (Exception ex) {
            UtilTela.mensagem(this, "Erro ao buscar: " + ex.getMessage());
        }
    }

    private void excluir() {
        try {
            if (id.getText().isEmpty()) {
                UtilTela.mensagem(this, "Busque um motorista antes de excluir.");
                return;
            }
            MotoristaDAO.getInstance().removeById(Integer.parseInt(id.getText()));
            limpar();
            listar();
            UtilTela.mensagem(this, "Motorista excluído.");
        } catch (Exception ex) {
            UtilTela.mensagem(this, "Erro ao excluir: " + ex.getMessage());
        }
    }

    private void limpar() {
        id.setText(""); nome.setText(""); cpf.setText(""); cnh.setText("");
        categoria.setText(""); telefone.setText(""); endereco.setText(""); status.setText("Ativo");
    }

    private void listar() {
        List<Motorista> lista = MotoristaDAO.getInstance().findAll();
        DefaultTableModel modeloTabela = new DefaultTableModel(
                new Object[]{"ID", "Nome", "CPF", "CNH", "Categoria", "Status"}, 0);
        for (Motorista m : lista) {
            modeloTabela.addRow(new Object[]{
                    m.getIdMotorista(), m.getNome(), m.getCpf(), m.getCnh(), m.getCategoriaCnh(), m.getStatus()
            });
        }
        tabela.setModel(modeloTabela);
    }
}
