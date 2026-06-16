package Apresentacao;

import DataAccess.UsuarioDAO;
import DomainModel.Usuario;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class JFUsuario extends JFrame {
    private JTextField id = new JTextField();
    private JTextField nome = new JTextField();
    private JTextField login = new JTextField();
    private JTextField senha = new JTextField();
    private JTextField perfil = new JTextField("Administrador");
    private JTextField status = new JTextField("Ativo");
    private JTable tabela = new JTable();

    public JFUsuario() {
        setTitle("Controle de Usuários");
        setSize(800, 480);
        setLocationRelativeTo(null);
        id.setEnabled(false);

        JPanel form = new JPanel(new GridLayout(6, 2, 5, 5));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        form.add(new JLabel("Código:")); form.add(id);
        form.add(new JLabel("Nome:")); form.add(nome);
        form.add(new JLabel("Login:")); form.add(login);
        form.add(new JLabel("Senha:")); form.add(senha);
        form.add(new JLabel("Perfil:")); form.add(perfil);
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
            Usuario u = new Usuario();
            u.setNome(nome.getText());
            u.setLogin(login.getText());
            u.setSenha(senha.getText());
            u.setPerfil(perfil.getText());
            u.setStatus(status.getText());

            if (id.getText().isEmpty()) {
                UsuarioDAO.getInstance().persist(u);
            } else {
                u.setIdUsuario(Integer.parseInt(id.getText()));
                UsuarioDAO.getInstance().merge(u);
            }

            limpar();
            listar();
            UtilTela.mensagem(this, "Usuário salvo com sucesso!");
        } catch (Exception ex) {
            UtilTela.mensagem(this, "Erro ao salvar usuário: " + ex.getMessage());
        }
    }

    private void buscar() {
        try {
            Integer codigo = UtilTela.pedirId(this, "Informe o código do usuário:");
            if (codigo == null) return;
            Usuario u = UsuarioDAO.getInstance().getById(codigo);
            if (u == null) {
                UtilTela.mensagem(this, "Usuário não encontrado.");
                return;
            }
            id.setText(String.valueOf(u.getIdUsuario()));
            nome.setText(u.getNome());
            login.setText(u.getLogin());
            senha.setText(u.getSenha());
            perfil.setText(u.getPerfil());
            status.setText(u.getStatus());
        } catch (Exception ex) {
            UtilTela.mensagem(this, "Erro ao buscar: " + ex.getMessage());
        }
    }

    private void excluir() {
        try {
            if (id.getText().isEmpty()) {
                UtilTela.mensagem(this, "Busque um usuário antes de excluir.");
                return;
            }
            UsuarioDAO.getInstance().removeById(Integer.parseInt(id.getText()));
            limpar();
            listar();
            UtilTela.mensagem(this, "Usuário excluído.");
        } catch (Exception ex) {
            UtilTela.mensagem(this, "Erro ao excluir: " + ex.getMessage());
        }
    }

    private void limpar() {
        id.setText(""); nome.setText(""); login.setText(""); senha.setText("");
        perfil.setText("Administrador"); status.setText("Ativo");
    }

    private void listar() {
        List<Usuario> lista = UsuarioDAO.getInstance().findAll();
        DefaultTableModel modeloTabela = new DefaultTableModel(
                new Object[]{"ID", "Nome", "Login", "Perfil", "Status"}, 0);
        for (Usuario u : lista) {
            modeloTabela.addRow(new Object[]{
                    u.getIdUsuario(), u.getNome(), u.getLogin(), u.getPerfil(), u.getStatus()
            });
        }
        tabela.setModel(modeloTabela);
    }
}
