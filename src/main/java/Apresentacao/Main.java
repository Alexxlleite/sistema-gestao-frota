package Apresentacao;

import DataAccess.UsuarioDAO;
import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        try {
            UsuarioDAO.getInstance().criarAdminPadraoSeNaoExistir();

            new JFLogin().setVisible(true);

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao iniciar o sistema:\n" + erro.getMessage());
        }
    }
}