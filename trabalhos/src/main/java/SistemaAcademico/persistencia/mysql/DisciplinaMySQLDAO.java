package SistemaAcademico.persistencia.mysql;

import SistemaAcademico.classes.Disciplina;
import SistemaAcademico.classes.Professor;
import SistemaAcademico.persistencia.IPersistencia;

import java.sql.*;
import java.util.*;

public class DisciplinaMySQLDAO implements IPersistencia<Disciplina> {

    private static final String URL = "jdbc:mysql://localhost:3306/sistema_academico";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public void salvar(Disciplina d) {
        String sql = "INSERT INTO disciplina (id, nome, ofertada, capacidadeMaxima) VALUES (?, ?, ?, ?)";
        try (Connection conn = conectar()) {
            conn.setAutoCommit(false);

            // Inserir disciplina
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, d.getId());
                stmt.setString(2, d.getNome());
                stmt.setBoolean(3, d.isOfertada());
                stmt.setInt(4, d.getCapacidadeMaxima());
                stmt.executeUpdate();
            }

            // Inserir professores
            String insertProfessor = "INSERT INTO disciplina_professor (disciplina_id, professor_id) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertProfessor)) {
                for (Professor p : d.listarProfessores()) {
                    stmt.setInt(1, d.getId());
                    stmt.setInt(2, p.getId());
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }

            // Inserir alunos
            String insertAluno = "INSERT INTO disciplina_aluno (disciplina_id, aluno_id) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertAluno)) {
                for (var aluno : d.getAlunosMatriculados()) {
                    stmt.setInt(1, d.getId());
                    stmt.setInt(2, aluno.getId());
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Disciplina buscarPorId(int id) {
        String sql = "SELECT * FROM disciplina WHERE id = ?";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Disciplina d = new Disciplina(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getBoolean("ofertada"));
                d.setCapacidadeMaxima(rs.getInt("capacidadeMaxima"));
                return d;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void atualizar(Disciplina d) {
        String sql = "UPDATE disciplina SET nome = ? WHERE id = ?";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, d.getNome());
            stmt.setInt(2, d.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM disciplina WHERE id = ?";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Disciplina> listarTodos() {
        List<Disciplina> lista = new ArrayList<>();
        String sql = "SELECT * FROM disciplina";
        try (Connection conn = conectar();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Disciplina d = new Disciplina(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getBoolean("ofertada"));
                d.setCapacidadeMaxima(rs.getInt("capacidadeMaxima"));
                lista.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

}