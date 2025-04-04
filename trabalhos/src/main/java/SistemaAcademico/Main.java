package SistemaAcademico;

import SistemaAcademico.classes.Aluno;
import SistemaAcademico.classes.Professor;
import SistemaAcademico.crud.AlunoCRUD;
import SistemaAcademico.crud.ProfessorCRUD;
import SistemaAcademico.persistencia.IPersistencia;

import SistemaAcademico.persistencia.json.ProfessorJSONDAO;
import SistemaAcademico.persistencia.json.AlunoJSONDAO;

import SistemaAcademico.persistencia.xml.ProfessorXMLDAO;
import SistemaAcademico.persistencia.xml.AlunoXMLDAO;

import SistemaAcademico.persistencia.mysql.ProfessorMySQLDAO;
import SistemaAcademico.persistencia.mysql.AlunoMySQLDAO;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        IPersistencia<Professor> professorDAO = null;
        IPersistencia<Aluno> alunoDAO = null;

        while (professorDAO == null || alunoDAO == null) {
            System.out.println("Escolha o tipo de persistência:");
            System.out.println("1 - JSON");
            System.out.println("2 - XML");
            System.out.println("3 - MySQL");
            System.out.print("Digite sua opção: ");
            String opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1":
                    professorDAO = new ProfessorJSONDAO();
                    alunoDAO = new AlunoJSONDAO();
                    break;
                case "2":
                    professorDAO = new ProfessorXMLDAO();
                    alunoDAO = new AlunoXMLDAO();
                    break;
                case "3":
                    professorDAO = new ProfessorMySQLDAO();
                    alunoDAO = new AlunoMySQLDAO();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.\n");
            }
        }

        scanner.close();

        ProfessorCRUD professorCRUD = new ProfessorCRUD(professorDAO);
        AlunoCRUD alunoCRUD = new AlunoCRUD(alunoDAO);

        // Executar testes de inserção
        TestesInsercao.testarProfessores(professorCRUD);
        TestesInsercao.testarAlunos(alunoCRUD);
    }
}
