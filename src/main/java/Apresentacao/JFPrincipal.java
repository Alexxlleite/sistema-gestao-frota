package Apresentacao;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class JFPrincipal extends JFrame {
    public JFPrincipal() {
        setTitle("Sistema de Gestão de Frota de Veículos");
        setSize(520, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painel = new JPanel(new GridLayout(8, 1, 8, 8));
        painel.setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel titulo = new JLabel("Sistema de Gestão de Frota de Veículos", SwingConstants.CENTER);
        painel.add(titulo);

        JButton btnVeiculos = new JButton("Cadastro de Veículos");
        JButton btnMotoristas = new JButton("Cadastro de Motoristas");
        JButton btnUsuarios = new JButton("Controle de Usuários");
        JButton btnViagens = new JButton("Registro de Viagens");
        JButton btnManutencoes = new JButton("Controle de Manutenção");
        JButton btnAbastecimentos = new JButton("Controle de Abastecimento");
        JButton btnBuscaVeiculos = new JButton("Busca de Veículos");
        JButton btnRelatorios = new JButton("Relatórios");
        JButton btnSair = new JButton("Sair");

        btnVeiculos.addActionListener(e -> new JFVeiculo().setVisible(true));
        btnMotoristas.addActionListener(e -> new JFMotorista().setVisible(true));
        btnUsuarios.addActionListener(e -> new JFUsuario().setVisible(true));
        btnViagens.addActionListener(e -> new JFViagem().setVisible(true));
        btnManutencoes.addActionListener(e -> new JFManutencao().setVisible(true));
        btnAbastecimentos.addActionListener(e -> new JFAbastecimento().setVisible(true));
        btnBuscaVeiculos.addActionListener(e -> new JFBuscaVeiculo().setVisible(true));
        btnRelatorios.addActionListener(e -> new JFRelatorios().setVisible(true));
        btnSair.addActionListener(e -> System.exit(0));

        painel.add(btnVeiculos);
        painel.add(btnMotoristas);
        painel.add(btnUsuarios);
        painel.add(btnViagens);
        painel.add(btnManutencoes);
        painel.add(btnAbastecimentos);
        painel.add(btnBuscaVeiculos);
        painel.add(btnRelatorios);
        painel.add(btnSair);

        add(painel);
    }
}
