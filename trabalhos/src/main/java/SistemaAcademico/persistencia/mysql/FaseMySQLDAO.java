package SistemaAcademico.persistencia.mysql;

import SistemaAcademico.classes.Disciplina;
import SistemaAcademico.classes.Fase;
import SistemaAcademico.persistencia.IPersistencia;

import java.sql.*;
import java.util.*;

public class FaseMySQLDAO implements IPersistencia<Fase> {
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_academico";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public void salvar(Fase fase) {
        String sqlFase = "INSERT INTO Fase (id, numero, nome) VALUES (?, ?, ?)";
        String sqlAssociacao = "INSERT INTO Fase_Disciplina (fase_id, disciplina_id) VALUES (?, ?)";

        try (Connection conn = conectar()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtFase = conn.prepareStatement(sqlFase)) {
                stmtFase.setInt(1, fase.getNumero());
                stmtFase.setInt(2, fase.getNumero());
                stmtFase.setString(3, fase.getNome());
                stmtFase.executeUpdate();
            }

            try (PreparedStatement stmtAssoc = conn.prepareStatement(sqlAssociacao)) {
                for (Disciplina d : fase.listarDisciplinas()) {
                    stmtAssoc.setInt(1, fase.getNumero());
                    stmtAssoc.setInt(2, d.getId());
                    stmtAssoc.addBatch();
                }
                stmtAssoc.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Fase buscarPorId(int id) {
        String sql = "SELECT * FROM Fase WHERE id = ?";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Fase(rs.getInt("numero"), rs.getString("nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void atualizar(Fase fase) {
        String sql = "UPDATE Fase SET nome = ? WHERE id = ?";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fase.getNome());
            stmt.setInt(2, fase.getNumero());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM Fase WHERE id = ?";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Fase> listarTodos() {
        List<Fase> lista = new ArrayList<>();
        String sql = "SELECT * FROM Fase";
        try (Connection conn = conectar();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Fase fase = new Fase(rs.getInt("numero"), rs.getString("nome"));
                lista.add(fase);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}