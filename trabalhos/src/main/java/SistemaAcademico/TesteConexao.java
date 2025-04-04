package SistemaAcademico;

import SistemaAcademico.persistencia.mysql.AlunoMySQLDAO;

public class TesteConexao {
    public static void main(String[] args) {
        AlunoMySQLDAO dao = new AlunoMySQLDAO();
        if (!dao.testarConexao()) {
            System.out.println("Não foi possível conectar ao banco. Encerrando aplicação.");
            return;
        }
    }
}
