package Apresentacao;

import DataAccess.AbastecimentoDAO;
import DataAccess.MotoristaDAO;
import DataAccess.VeiculoDAO;
import DomainModel.Abastecimento;
import DomainModel.Motorista;
import DomainModel.Veiculo;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class JFAbastecimento extends JFrame {
    private JTextField id = new JTextField();
    private JTextField idVeiculo = new JTextField();
    private JTextField idMotorista = new JTextField();
    private JTextField combustivel = new JTextField("Gasolina");
    private JTextField litros = new JTextField();
    private JTextField valor = new JTextField();
    private JTextField data = new JTextField();
    private JTextField km = new JTextField();
    private JTable tabela = new JTable();

    public JFAbastecimento() {
        setTitle("Controle de Abastecimento");
        setSize(900, 520);
        setLocationRelativeTo(null);
        id.setEnabled(false);

        JPanel form = new JPanel(new GridLayout(8, 2, 5, 5));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        form.add(new JLabel("Código:")); form.add(id);
        form.add(new JLabel("ID Veículo:")); form.add(idVeiculo);
        form.add(new JLabel("ID Motorista:")); form.add(idMotorista);
        form.add(new JLabel("Combustível:")); form.add(combustivel);
        form.add(new JLabel("Quantidade Litros:")); form.add(litros);
        form.add(new JLabel("Valor Total:")); form.add(valor);
        form.add(new JLabel("Data:")); form.add(data);
        form.add(new JLabel("Quilometragem:")); form.add(km);

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
            Motorista motorista = null;
            if (!idMotorista.getText().trim().isEmpty()) {
                motorista = MotoristaDAO.getInstance().getById(Integer.parseInt(idMotorista.getText()));
            }
            if (veiculo == null) {
                UtilTela.mensagem(this, "Veículo não encontrado.");
                return;
            }

            Abastecimento a = new Abastecimento();
            a.setVeiculo(veiculo);
            a.setMotorista(motorista);
            a.setTipoCombustivel(combustivel.getText());
            a.setQuantidadeLitros(UtilTela.decimal(litros));
            a.setValorTotal(UtilTela.decimal(valor));
            a.setDataAbastecimento(data.getText());
            a.setQuilometragem(UtilTela.decimal(km));

            if (id.getText().isEmpty()) {
                AbastecimentoDAO.getInstance().persist(a);
            } else {
                a.setIdAbastecimento(Integer.parseInt(id.getText()));
                AbastecimentoDAO.getInstance().merge(a);
            }

            limpar();
            listar();
            UtilTela.mensagem(this, "Abastecimento salvo com sucesso!");
        } catch (Exception ex) {
            UtilTela.mensagem(this, "Erro ao salvar abastecimento: " + ex.getMessage());
        }
    }

    private void buscar() {
        try {
            Integer codigo = UtilTela.pedirId(this, "Informe o código do abastecimento:");
            if (codigo == null) return;
            Abastecimento a = AbastecimentoDAO.getInstance().getById(codigo);
            if (a == null) {
                UtilTela.mensagem(this, "Abastecimento não encontrado.");
                return;
            }
            id.setText(String.valueOf(a.getIdAbastecimento()));
            idVeiculo.setText(String.valueOf(a.getVeiculo().getIdVeiculo()));
            idMotorista.setText(a.getMotorista() != null ? String.valueOf(a.getMotorista().getIdMotorista()) : "");
            combustivel.setText(a.getTipoCombustivel());
            litros.setText(String.valueOf(a.getQuantidadeLitros()));
            valor.setText(String.valueOf(a.getValorTotal()));
            data.setText(a.getDataAbastecimento());
            km.setText(String.valueOf(a.getQuilometragem()));
        } catch (Exception ex) {
            UtilTela.mensagem(this, "Erro ao buscar: " + ex.getMessage());
        }
    }

    private void excluir() {
        try {
            if (id.getText().isEmpty()) {
                UtilTela.mensagem(this, "Busque um abastecimento antes de excluir.");
                return;
            }
            AbastecimentoDAO.getInstance().removeById(Integer.parseInt(id.getText()));
            limpar();
            listar();
            UtilTela.mensagem(this, "Abastecimento excluído.");
        } catch (Exception ex) {
            UtilTela.mensagem(this, "Erro ao excluir: " + ex.getMessage());
        }
    }

    private void limpar() {
        id.setText(""); idVeiculo.setText(""); idMotorista.setText(""); combustivel.setText("Gasolina");
        litros.setText(""); valor.setText(""); data.setText(""); km.setText("");
    }

    private void listar() {
        List<Abastecimento> lista = AbastecimentoDAO.getInstance().findAll();
        DefaultTableModel modeloTabela = new DefaultTableModel(
                new Object[]{"ID", "Veículo", "Motorista", "Combustível", "Litros", "Valor"}, 0);
        for (Abastecimento a : lista) {
            modeloTabela.addRow(new Object[]{
                    a.getIdAbastecimento(),
                    a.getVeiculo() != null ? a.getVeiculo().getPlaca() : "",
                    a.getMotorista() != null ? a.getMotorista().getNome() : "",
                    a.getTipoCombustivel(), a.getQuantidadeLitros(), a.getValorTotal()
            });
        }
        tabela.setModel(modeloTabela);
    }
}
