package SistemaAcademico.persistencia.mysql;

import SistemaAcademico.classes.Aluno;
import SistemaAcademico.persistencia.IPersistencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoMySQLDAO implements IPersistencia<Aluno> {

    private static final String URL = "jdbc:mysql://localhost:3306/sistema_academico";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public boolean testarConexao() {
        try (Connection conn = conectar()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Conexão com o banco de dados estabelecida com sucesso!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Erro ao conectar com o banco de dados: " + e.getMessage());
        }
        return false;
    }

    @Override
    public void salvar(Aluno aluno) {
        String sql = "INSERT INTO Aluno (id, nome) VALUES (?, ?)";
        try (Connection conn = conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, aluno.getId());
            stmt.setString(2, aluno.getNome());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Aluno buscarPorId(int id) {
        String sql = "SELECT * FROM Aluno WHERE id = ?";
        try (Connection conn = conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Aluno(rs.getInt("id"), rs.getString("nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void atualizar(Aluno aluno) {
        String sql = "UPDATE Aluno SET nome = ? WHERE id = ?";
        try (Connection conn = conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setInt(2, aluno.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM Aluno WHERE id = ?";
        try (Connection conn = conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Aluno> listarTodos() {
        List<Aluno> lista = new ArrayList<>();
        String sql = "SELECT * FROM Aluno";

        try (Connection conn = conectar();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Aluno(rs.getInt("id"), rs.getString("nome")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

}
