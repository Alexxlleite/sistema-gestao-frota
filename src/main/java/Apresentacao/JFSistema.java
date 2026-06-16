package Apresentacao;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class JFSistema extends JFrame {

    private JPanel painelConteudo;

    public JFSistema() {
        setTitle("Sistema de Gestão de Frota de Veículos");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        painelConteudo = new JPanel(new BorderLayout());
        add(painelConteudo);

        mostrarMenu();
    }

    public void mostrarMenu() {
        painelConteudo.removeAll();

        JPanel painelMenu = new JPanel(new BorderLayout());
        painelMenu.setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel titulo = new JLabel("Sistema de Gestão de Frota de Veículos", SwingConstants.CENTER);

        JPanel botoes = new JPanel(new GridLayout(5, 2, 10, 10));

        JButton btnVeiculos = new JButton("Cadastro de Veículos");
        JButton btnMotoristas = new JButton("Cadastro de Motoristas");
        JButton btnUsuarios = new JButton("Controle de Usuários");
        JButton btnViagens = new JButton("Registro de Viagens");
        JButton btnManutencao = new JButton("Controle de Manutenção");
        JButton btnAbastecimento = new JButton("Controle de Abastecimento");
        JButton btnBusca = new JButton("Busca de Veículos");
        JButton btnRelatorios = new JButton("Relatórios");
        JButton btnSair = new JButton("Sair");

        btnVeiculos.addActionListener(e -> abrirTela("Cadastro de Veículos", new JFVeiculo()));
        btnMotoristas.addActionListener(e -> abrirTela("Cadastro de Motoristas", new JFMotorista()));
        btnUsuarios.addActionListener(e -> abrirTela("Controle de Usuários", new JFUsuario()));
        btnViagens.addActionListener(e -> abrirTela("Registro de Viagens", new JFViagem()));
        btnManutencao.addActionListener(e -> abrirTela("Controle de Manutenção", new JFManutencao()));
        btnAbastecimento.addActionListener(e -> abrirTela("Controle de Abastecimento", new JFAbastecimento()));
        btnBusca.addActionListener(e -> abrirTela("Busca de Veículos", new JFBuscaVeiculo()));
        btnRelatorios.addActionListener(e -> abrirTela("Relatórios", new JFRelatorios()));
        btnSair.addActionListener(e -> System.exit(0));

        botoes.add(btnVeiculos);
        botoes.add(btnMotoristas);
        botoes.add(btnUsuarios);
        botoes.add(btnViagens);
        botoes.add(btnManutencao);
        botoes.add(btnAbastecimento);
        botoes.add(btnBusca);
        botoes.add(btnRelatorios);
        botoes.add(btnSair);

        painelMenu.add(titulo, BorderLayout.NORTH);
        painelMenu.add(botoes, BorderLayout.CENTER);

        painelConteudo.add(painelMenu, BorderLayout.CENTER);

        painelConteudo.revalidate();
        painelConteudo.repaint();
    }

    private void abrirTela(String tituloTela, JFrame tela) {
        painelConteudo.removeAll();

        JPanel painelTela = new JPanel(new BorderLayout());

        JPanel barraSuperior = new JPanel(new BorderLayout());
        barraSuperior.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton btnVoltar = new JButton("Voltar");
        JLabel titulo = new JLabel(tituloTela, SwingConstants.CENTER);

        btnVoltar.addActionListener(e -> mostrarMenu());

        barraSuperior.add(btnVoltar, BorderLayout.WEST);
        barraSuperior.add(titulo, BorderLayout.CENTER);

        painelTela.add(barraSuperior, BorderLayout.NORTH);
        painelTela.add(tela.getContentPane(), BorderLayout.CENTER);

        painelConteudo.add(painelTela, BorderLayout.CENTER);

        painelConteudo.revalidate();
        painelConteudo.repaint();
    }
}