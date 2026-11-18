package br.univ.pharmasys.ui;

import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import br.univ.pharmasys.dao.FornecedorDAO;
import br.univ.pharmasys.model.Fornecedor;

@SuppressWarnings("serial")
public class TelaFornecedores extends JFrame {

    public TelaFornecedores() {
        initComponents();
        carregarDados();
    }

    private void initComponents() {

        jLabelTitulo = new JLabel();
        jScrollPane1 = new JScrollPane();
        tabelaFornecedores = new JTable();
        btnNovo = new JButton();
        btnEditar = new JButton();
        btnExcluir = new JButton();
        btnVoltar = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gerenciar Fornecedores");

        jLabelTitulo.setFont(new java.awt.Font("Segoe UI", 1, 20));
        jLabelTitulo.setText("Gerenciamento de Fornecedores");

        tabelaFornecedores.setModel(new DefaultTableModel(
                new Object [][] {},
                new String [] { "ID", "Nome", "CNPJ", "Email", "Telefone" }
        ));
        jScrollPane1.setViewportView(tabelaFornecedores);

        btnNovo.setText("Novo Fornecedor");
        btnNovo.addActionListener(evt -> abrirCadastro());
        
        btnEditar.setText("Editar");
        btnExcluir.setText("Excluir");
        btnVoltar.setText("Voltar");
        btnVoltar.addActionListener(evt -> voltar());

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabelTitulo)
                                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 650, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnNovo)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnEditar)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnExcluir)
                                                .addGap(220, 220, 220)
                                                .addComponent(btnVoltar)))
                                .addContainerGap(20, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jLabelTitulo)
                                .addGap(30, 30, 30)
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnNovo)
                                        .addComponent(btnEditar)
                                        .addComponent(btnExcluir)
                                        .addComponent(btnVoltar))
                                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    public void carregarDados() {
        DefaultTableModel model = (DefaultTableModel) tabelaFornecedores.getModel();
        model.setRowCount(0);

        try {
            FornecedorDAO dao = new FornecedorDAO();
            List<Fornecedor> fornecedores = dao.listarTodos();
            for (Fornecedor f : fornecedores) {
                model.addRow(new Object[]{
                    f.getIdFornecedor(),
                    f.getNome(),
                    f.getCnpj(),
                    f.getEmail(),
                    f.getTelefoneId()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar fornecedores: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirCadastro() {
        TelaCadastroFornecedor tela = new TelaCadastroFornecedor();
        tela.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                carregarDados();
            }
        });
        tela.setVisible(true);
    }
    
    private void voltar() {
        this.dispose();
    }

    private JButton btnNovo;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnVoltar;
    private JLabel jLabelTitulo;
    private JScrollPane jScrollPane1;
    private JTable tabelaFornecedores;
}
