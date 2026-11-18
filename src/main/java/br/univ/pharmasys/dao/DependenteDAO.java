package br.univ.pharmasys.dao;

import br.univ.pharmasys.model.Dependente;
import br.univ.pharmasys.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DependenteDAO {

    private static final String INSERT_SQL = "INSERT INTO DEPENDENTE (CPF, NOME, SEXO, DATA_NASCIMENTO, FUNCIONARIO_ID) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE DEPENDENTE SET CPF = ?, NOME = ?, SEXO = ?, DATA_NASCIMENTO = ?, FUNCIONARIO_ID = ? WHERE ID_DEPENDENTE = ?";
    private static final String DELETE_SQL = "DELETE FROM DEPENDENTE WHERE ID_DEPENDENTE = ?";
    private static final String SELECT_ALL_SQL = "SELECT D.*, F.CPF AS CPF_TITULAR FROM DEPENDENTE D LEFT JOIN FUNCIONARIO F ON D.FUNCIONARIO_ID = F.ID_FUNCIONARIO ORDER BY D.NOME";
    private static final String SELECT_BY_ID_SQL = "SELECT D.*, F.CPF AS CPF_TITULAR FROM DEPENDENTE D LEFT JOIN FUNCIONARIO F ON D.FUNCIONARIO_ID = F.ID_FUNCIONARIO WHERE D.ID_DEPENDENTE = ?";
    private static final String SELECT_BY_FUNC_CPF = "SELECT D.*, F.CPF AS CPF_TITULAR FROM DEPENDENTE D INNER JOIN FUNCIONARIO F ON D.FUNCIONARIO_ID = F.ID_FUNCIONARIO WHERE F.CPF = ? ORDER BY D.NOME";

    // 1. Salvar
    public void salvar(Dependente dependente) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, dependente.getCpf() != null ? dependente.getCpf() : dependente.getCpfFuncionario());
            stmt.setString(2, dependente.getNome());
            stmt.setString(3, dependente.getSexo());
            
            if (dependente.getDataNascimento() != null) {
                stmt.setDate(4, Date.valueOf(dependente.getDataNascimento()));
            } else {
                stmt.setNull(4, Types.DATE);
            }

            if (dependente.getFuncionarioId() != null) {
                stmt.setLong(5, dependente.getFuncionarioId());
            } else if (dependente.getFuncionario() != null) {
                stmt.setLong(5, dependente.getFuncionario().getIdFuncionario());
            } else {
                throw new SQLException("O Dependente deve ter um funcionário associado.");
            }

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    dependente.setId(generatedKeys.getLong(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar dependente: " + e.getMessage(), e);
        }
    }

    // 2. Atualizar
    public void atualizar(Dependente dependente) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            stmt.setString(1, dependente.getCpf() != null ? dependente.getCpf() : dependente.getCpfFuncionario());
            stmt.setString(2, dependente.getNome());
            stmt.setString(3, dependente.getSexo());

            if (dependente.getDataNascimento() != null) {
                stmt.setDate(4, Date.valueOf(dependente.getDataNascimento()));
            } else {
                stmt.setNull(4, Types.DATE);
            }

            if (dependente.getFuncionarioId() != null) {
                stmt.setLong(5, dependente.getFuncionarioId());
            } else if (dependente.getFuncionario() != null) {
                stmt.setLong(5, dependente.getFuncionario().getIdFuncionario());
            } else {
                stmt.setNull(5, Types.BIGINT);
            }

            stmt.setLong(6, dependente.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar dependente: " + e.getMessage(), e);
        }
    }

    // 3. Deletar
    public void deletar(Long id) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar dependente: " + e.getMessage(), e);
        }
    }

    // 4. Buscar por ID
    public Dependente buscarPorId(Long id) {
        Dependente dependente = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    dependente = mapearResultSet(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar dependente por ID: " + e.getMessage(), e);
        }

        return dependente;
    }

    // 5. Listar tudo
    public List<Dependente> listarTodos() {
        List<Dependente> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar dependentes: " + e.getMessage(), e);
        }

        return lista;
    }

    // 6. Listar por Funcionário
    public List<Dependente> listarPorCpfFuncionario(String cpfFuncionario) {
        List<Dependente> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_FUNC_CPF)) {

            stmt.setString(1, cpfFuncionario);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearResultSet(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar dependentes do funcionário: " + e.getMessage(), e);
        }

        return lista;
    }

    private Dependente mapearResultSet(ResultSet rs) throws SQLException {
        Dependente d = new Dependente();
        
        d.setId(rs.getLong("ID_DEPENDENTE"));
        d.setCpf(rs.getString("CPF"));
        d.setNome(rs.getString("NOME"));
        d.setSexo(rs.getString("SEXO"));
        d.setFuncionarioId(rs.getLong("FUNCIONARIO_ID"));
        
        try {
            d.setCpfFuncionario(rs.getString("CPF_TITULAR"));
        } catch (SQLException ignored) {}
        
        if (rs.getDate("DATA_NASCIMENTO") != null) {
            d.setDataNascimento(rs.getDate("DATA_NASCIMENTO").toLocalDate());
        }

        return d;
    }
}
