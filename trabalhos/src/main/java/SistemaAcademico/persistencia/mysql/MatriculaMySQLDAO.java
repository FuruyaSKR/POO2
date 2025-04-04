package SistemaAcademico.persistencia.mysql;

import SistemaAcademico.classes.*;
import SistemaAcademico.persistencia.IPersistencia;

import java.sql.*;
import java.util.*;

public class MatriculaMySQLDAO implements IPersistencia<Matricula> {

    private static final String URL = "jdbc:mysql://localhost:3306/sistema_academico";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public void salvar(Matricula m) {
        String sql = "INSERT INTO Matricula (id, aluno_id, disciplina_id, curso_id, situacaoFinal) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, m.getId());
            stmt.setInt(2, m.getAluno().getId());
            stmt.setInt(3, m.getDisciplina().getId());
            stmt.setInt(4, m.getCurso().getId());
            stmt.setString(5, m.getSituacaoFinal().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Matricula buscarPorId(int id) {
        String sql = "SELECT * FROM Matricula WHERE id = ?";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Aluno aluno = new Aluno(rs.getInt("aluno_id"), "");
                Disciplina disciplina = new Disciplina(rs.getInt("disciplina_id"), "", true);
                Curso curso = new Curso(rs.getInt("curso_id"), "");
                Matricula m = new Matricula(rs.getInt("id"), aluno, disciplina, curso);
                m.setSituacaoFinal(SituacaoAlunoEnum.valueOf(rs.getString("situacaoFinal")));
                return m;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void atualizar(Matricula m) {
        String sql = "UPDATE Matricula SET situacaoFinal = ? WHERE id = ?";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, m.getSituacaoFinal().toString());
            stmt.setInt(2, m.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM Matricula WHERE id = ?";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Matricula> listarTodos() {
        List<Matricula> lista = new ArrayList<>();
        String sql = "SELECT * FROM Matricula";
        try (Connection conn = conectar();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Aluno aluno = new Aluno(rs.getInt("aluno_id"), "");
                Disciplina disciplina = new Disciplina(rs.getInt("disciplina_id"), "", true);
                Curso curso = new Curso(rs.getInt("curso_id"), "");
                Matricula m = new Matricula(rs.getInt("id"), aluno, disciplina, curso);
                m.setSituacaoFinal(SituacaoAlunoEnum.valueOf(rs.getString("situacaoFinal")));
                lista.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
