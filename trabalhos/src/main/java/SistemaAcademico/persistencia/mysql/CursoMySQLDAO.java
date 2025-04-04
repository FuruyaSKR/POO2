package SistemaAcademico.persistencia.mysql;

import SistemaAcademico.classes.Curso;
import SistemaAcademico.classes.Disciplina;
import SistemaAcademico.classes.Fase;
import SistemaAcademico.persistencia.IPersistencia;

import java.sql.*;
import java.util.*;

public class CursoMySQLDAO implements IPersistencia<Curso> {
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_academico";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public void salvar(Curso curso) {
        String sqlCurso = "INSERT INTO Curso (id, nome) VALUES (?, ?)";
        String sqlRelacao = "INSERT INTO curso_fase (curso_id, fase_id) VALUES (?, ?)";

        try (Connection conn = conectar()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtCurso = conn.prepareStatement(sqlCurso)) {
                stmtCurso.setInt(1, curso.getId());
                stmtCurso.setString(2, curso.getNome());
                stmtCurso.executeUpdate();
            }

            try (PreparedStatement stmtRelacao = conn.prepareStatement(sqlRelacao)) {
                for (Fase f : curso.getFases()) {
                    stmtRelacao.setInt(1, curso.getId());
                    stmtRelacao.setInt(2, f.getNumero());
                    stmtRelacao.addBatch();
                }
                stmtRelacao.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Curso buscarPorId(int id) {
        String sqlCurso = "SELECT * FROM Curso WHERE id = ?";
        String sqlFases = "SELECT f.numero, f.nome FROM Fase f JOIN curso_fase cf ON f.id = cf.fase_id WHERE cf.curso_id = ?";

        try (Connection conn = conectar();
                PreparedStatement stmtCurso = conn.prepareStatement(sqlCurso)) {

            stmtCurso.setInt(1, id);
            ResultSet rsCurso = stmtCurso.executeQuery();

            if (rsCurso.next()) {
                Curso curso = new Curso(rsCurso.getInt("id"), rsCurso.getString("nome"));

                try (PreparedStatement stmtFases = conn.prepareStatement(sqlFases)) {
                    stmtFases.setInt(1, curso.getId());
                    ResultSet rsFases = stmtFases.executeQuery();

                    while (rsFases.next()) {
                        Fase fase = new Fase(rsFases.getInt("numero"), rsFases.getString("nome"));
                        curso.adicionarFase(fase);
                    }
                }

                return curso;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void atualizar(Curso curso) {
        String sql = "UPDATE Curso SET nome = ? WHERE id = ?";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, curso.getNome());
            stmt.setInt(2, curso.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM Curso WHERE id = ?";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Curso> listarTodos() {
        List<Curso> cursos = new ArrayList<>();

        String sqlCurso = "SELECT * FROM Curso";
        String sqlFases = "SELECT f.id, f.numero, f.nome FROM Fase f " +
                "JOIN curso_fase cf ON f.id = cf.fase_id WHERE cf.curso_id = ?";
        String sqlDisciplinas = "SELECT d.id, d.nome, d.ofertada, d.capacidadeMaxima " +
                "FROM Disciplina d " +
                "JOIN fase_disciplina fd ON d.id = fd.disciplina_id " +
                "WHERE fd.fase_id = ?";

        try (Connection conn = conectar();
                Statement stmtCurso = conn.createStatement();
                ResultSet rsCurso = stmtCurso.executeQuery(sqlCurso)) {

            while (rsCurso.next()) {
                Curso curso = new Curso(rsCurso.getInt("id"), rsCurso.getString("nome"));

                try (PreparedStatement stmtFases = conn.prepareStatement(sqlFases)) {
                    stmtFases.setInt(1, curso.getId());
                    ResultSet rsFases = stmtFases.executeQuery();

                    while (rsFases.next()) {
                        int faseId = rsFases.getInt("id");
                        Fase fase = new Fase(rsFases.getInt("numero"), rsFases.getString("nome"));

                        try (PreparedStatement stmtDisc = conn.prepareStatement(sqlDisciplinas)) {
                            stmtDisc.setInt(1, faseId);
                            ResultSet rsDisc = stmtDisc.executeQuery();

                            while (rsDisc.next()) {
                                var disc = new Disciplina(
                                        rsDisc.getInt("id"),
                                        rsDisc.getString("nome"),
                                        rsDisc.getBoolean("ofertada"));
                                disc.setCapacidadeMaxima(rsDisc.getInt("capacidadeMaxima"));
                                fase.adicionarDisciplina(disc);
                            }
                        }

                        curso.adicionarFase(fase);
                    }
                }

                cursos.add(curso);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cursos;
    }

}
