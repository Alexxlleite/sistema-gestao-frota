package Apresentacao;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class UtilTela {
    public static int inteiro(JTextField campo) {
        if (campo.getText().trim().isEmpty()) return 0;
        return Integer.parseInt(campo.getText().trim());
    }

    public static double decimal(JTextField campo) {
        if (campo.getText().trim().isEmpty()) return 0;
        return Double.parseDouble(campo.getText().trim().replace(",", "."));
    }

    public static void mensagem(Object parent, String texto) {
        JOptionPane.showMessageDialog(null, texto);
    }

    public static Integer pedirId(Object parent, String mensagem) {
        String input = JOptionPane.showInputDialog(null, mensagem);
        if (input == null || input.trim().isEmpty()) return null;
        return Integer.parseInt(input.trim());
    }
}
