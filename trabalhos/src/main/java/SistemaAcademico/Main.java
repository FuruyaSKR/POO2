package SistemaAcademico;

import java.util.List;
import java.util.Scanner;
import SistemaAcademico.persistencia.IPersistencia;

import SistemaAcademico.classes.Aluno;
import SistemaAcademico.classes.Disciplina;
import SistemaAcademico.classes.Fase;
import SistemaAcademico.classes.Professor;

import SistemaAcademico.crud.AlunoCRUD;
import SistemaAcademico.crud.DisciplinaCRUD;
import SistemaAcademico.crud.FaseCRUD;
import SistemaAcademico.crud.ProfessorCRUD;

import SistemaAcademico.persistencia.json.AlunoJSONDAO;
import SistemaAcademico.persistencia.json.DisciplinaJSONDAO;
import SistemaAcademico.persistencia.json.FaseJSONDAO;
import SistemaAcademico.persistencia.json.ProfessorJSONDAO;

import SistemaAcademico.persistencia.mysql.AlunoMySQLDAO;
import SistemaAcademico.persistencia.mysql.DisciplinaMySQLDAO;
import SistemaAcademico.persistencia.mysql.FaseMySQLDAO;
import SistemaAcademico.persistencia.mysql.ProfessorMySQLDAO;

import SistemaAcademico.persistencia.xml.AlunoXMLDAO;
import SistemaAcademico.persistencia.xml.DisciplinaXMLDAO;
import SistemaAcademico.persistencia.xml.FaseXMLDAO;
import SistemaAcademico.persistencia.xml.ProfessorXMLDAO;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        IPersistencia<Professor> professorDAO = null;
        IPersistencia<Aluno> alunoDAO = null;
        IPersistencia<Disciplina> disciplinaDAO = null;
        IPersistencia<Fase> faseDAO = null;

        while (professorDAO == null || alunoDAO == null || disciplinaDAO == null || faseDAO == null) {
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
                    disciplinaDAO = new DisciplinaJSONDAO();
                    faseDAO = new FaseJSONDAO();
                    break;
                case "2":
                    professorDAO = new ProfessorXMLDAO();
                    alunoDAO = new AlunoXMLDAO();
                    disciplinaDAO = new DisciplinaXMLDAO();
                    faseDAO = new FaseXMLDAO();
                    break;
                case "3":
                    professorDAO = new ProfessorMySQLDAO();
                    alunoDAO = new AlunoMySQLDAO();
                    disciplinaDAO = new DisciplinaMySQLDAO();
                    faseDAO = new FaseMySQLDAO();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.\n");
            }
        }

        scanner.close();

        // CRIAR OS CRUDs
        ProfessorCRUD professorCRUD = new ProfessorCRUD(professorDAO);
        AlunoCRUD alunoCRUD = new AlunoCRUD(alunoDAO);
        DisciplinaCRUD disciplinaCRUD = new DisciplinaCRUD(disciplinaDAO);
        FaseCRUD faseCRUD = new FaseCRUD(faseDAO);

        // CRIAR OS DADOS
        List<Professor> professores = TestesInsercao.criarProfessores();
        List<Aluno> alunos = TestesInsercao.criarAlunos();
        List<Disciplina> disciplinas = TestesInsercao.criarDisciplinas(professores, alunos);

        // EXECUTAR OS TESTES
        TestesInsercao.testarProfessores(professorCRUD, professores);
        TestesInsercao.testarAlunos(alunoCRUD, alunos);
        TestesInsercao.testarDisciplinas(disciplinaCRUD, disciplinas);
        TestesInsercao.testarFases(faseCRUD, disciplinas);
    }
}
