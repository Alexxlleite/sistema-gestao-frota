package Apresentacao;

import DataAccess.UsuarioDAO;
import DomainModel.Usuario;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class JFLogin extends JFrame {

    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JButton btnEntrar;
    private JButton btnSair;

    public JFLogin() {
        setTitle("Login - Sistema de Gestão de Frota");
        setSize(400, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painel = new JPanel(new GridLayout(5, 1, 8, 8));
        painel.setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel titulo = new JLabel("Acesso ao Sistema", SwingConstants.CENTER);

        txtLogin = new JTextField();
        txtSenha = new JPasswordField();

        btnEntrar = new JButton("Entrar");
        btnSair = new JButton("Sair");

        painel.add(titulo);
        painel.add(new JLabel("Usuário:"));
        painel.add(txtLogin);
        painel.add(new JLabel("Senha:"));
        painel.add(txtSenha);

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnEntrar);
        painelBotoes.add(btnSair);

        add(painel, "Center");
        add(painelBotoes, "South");

        btnEntrar.addActionListener(e -> entrar());
        btnSair.addActionListener(e -> System.exit(0));
    }

    private void entrar() {
        String login = txtLogin.getText();
        String senha = new String(txtSenha.getPassword());

        if (login.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe usuário e senha.");
            return;
        }

        Usuario usuario = UsuarioDAO.getInstance().autenticar(login, senha);

        if (usuario != null) {
            JOptionPane.showMessageDialog(this, "Bem-vindo, " + usuario.getNome() + "!");

            JFSistema sistema = new JFSistema();
            sistema.setVisible(true);

            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos.");
        }
    }
}   