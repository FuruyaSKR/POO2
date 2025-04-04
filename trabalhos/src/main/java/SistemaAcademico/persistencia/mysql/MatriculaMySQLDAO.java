package SistemaAcademico.persistencia.mysql;

import SistemaAcademico.classes.*;
import SistemaAcademico.persistencia.IPersistencia;

import java.sql.*;
import java.time.LocalDate;
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
        String sqlAvaliacao = "INSERT INTO Avaliacao (nota, professor_id, matricula_id) VALUES (?, ?, ?)";
        String sqlFrequencia = "INSERT INTO Frequencia (data, presente, professor_id, matricula_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = conectar()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, m.getId());
                stmt.setInt(2, m.getAluno().getId());
                stmt.setInt(3, m.getDisciplina().getId());
                stmt.setInt(4, m.getCurso().getId());
                stmt.setString(5, m.getSituacaoFinal().toString());
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlAvaliacao)) {
                for (Avaliacao a : m.getAvaliacoes()) {
                    stmt.setDouble(1, a.getNota());
                    stmt.setInt(2, a.getProfessorResponsavel().getId());
                    stmt.setInt(3, m.getId());
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlFrequencia)) {
                for (Frequencia f : m.getFrequencias()) {
                    stmt.setDate(1, java.sql.Date.valueOf(f.getData()));
                    stmt.setBoolean(2, f.isPresente());
                    stmt.setInt(3, f.getProfessorResponsavel().getId());
                    stmt.setInt(4, m.getId());
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
                int id = rs.getInt("id");
                Aluno aluno = new Aluno(rs.getInt("aluno_id"), "");
                Disciplina disciplina = new Disciplina(rs.getInt("disciplina_id"), "", true);
                Curso curso = new Curso(rs.getInt("curso_id"), "");
                Matricula m = new Matricula(id, aluno, disciplina, curso);
                m.setSituacaoFinal(SituacaoAlunoEnum.valueOf(rs.getString("situacaoFinal")));

                try (PreparedStatement stmtAv = conn
                        .prepareStatement("SELECT * FROM Avaliacao WHERE matricula_id = ?")) {
                    stmtAv.setInt(1, id);
                    ResultSet rsAv = stmtAv.executeQuery();
                    while (rsAv.next()) {
                        Professor p = new Professor(rsAv.getInt("professor_id"), "");
                        double nota = rsAv.getDouble("nota");
                        m.registrarAvaliacao(new Avaliacao(nota, p));
                    }
                }

                try (PreparedStatement stmtFq = conn
                        .prepareStatement("SELECT * FROM Frequencia WHERE matricula_id = ?")) {
                    stmtFq.setInt(1, id);
                    ResultSet rsFq = stmtFq.executeQuery();
                    while (rsFq.next()) {
                        LocalDate data = rsFq.getDate("data").toLocalDate();
                        boolean presente = rsFq.getBoolean("presente");
                        Professor p = new Professor(rsFq.getInt("professor_id"), "");
                        m.registrarFrequencia(new Frequencia(data, presente, p));
                    }
                }

                lista.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

}
