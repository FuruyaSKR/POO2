package SistemaAcademico;

import java.util.List;
import java.util.Scanner;

import SistemaAcademico.classes.Aluno;
import SistemaAcademico.classes.Curso;
import SistemaAcademico.classes.Disciplina;
import SistemaAcademico.classes.Fase;
import SistemaAcademico.classes.Matricula;
import SistemaAcademico.classes.Professor;

import SistemaAcademico.crud.AlunoCRUD;
import SistemaAcademico.crud.CursoCRUD;
import SistemaAcademico.crud.DisciplinaCRUD;
import SistemaAcademico.crud.FaseCRUD;
import SistemaAcademico.crud.ProfessorCRUD;
import SistemaAcademico.crud.MatriculaCRUD;

import SistemaAcademico.persistencia.IPersistencia;
import SistemaAcademico.persistencia.json.*;
import SistemaAcademico.persistencia.mysql.*;
import SistemaAcademico.persistencia.xml.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        IPersistencia<Professor> professorDAO = null;
        IPersistencia<Aluno> alunoDAO = null;
        IPersistencia<Disciplina> disciplinaDAO = null;
        IPersistencia<Fase> faseDAO = null;
        IPersistencia<Curso> cursoDAO = null;
        IPersistencia<Matricula> matriculaDAO = null;

        boolean persistenciaSelecionada = false;
        while (!persistenciaSelecionada) {
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
                    cursoDAO = new CursoJSONDAO();
                    matriculaDAO = new MatriculaJSONDAO();
                    persistenciaSelecionada = true;
                    break;
                case "2":
                    professorDAO = new ProfessorXMLDAO();
                    alunoDAO = new AlunoXMLDAO();
                    disciplinaDAO = new DisciplinaXMLDAO();
                    faseDAO = new FaseXMLDAO();
                    cursoDAO = new CursoXMLDAO();
                    matriculaDAO = new MatriculaXMLDAO();
                    persistenciaSelecionada = true;
                    break;
                case "3":
                    professorDAO = new ProfessorMySQLDAO();
                    alunoDAO = new AlunoMySQLDAO();
                    disciplinaDAO = new DisciplinaMySQLDAO();
                    faseDAO = new FaseMySQLDAO();
                    cursoDAO = new CursoMySQLDAO();
                    matriculaDAO = new MatriculaMySQLDAO();
                    persistenciaSelecionada = true;
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
        CursoCRUD cursoCRUD = new CursoCRUD(cursoDAO);
        MatriculaCRUD matriculaCRUD = new MatriculaCRUD(matriculaDAO);

        // CRIAR OS DADOS
        List<Professor> professores = TestesInsercao.criarProfessores();
        List<Aluno> alunos = TestesInsercao.criarAlunos();
        List<Disciplina> disciplinas = TestesInsercao.criarDisciplinas(professores, alunos);
        List<Fase> fases = faseCRUD.listarTodasFases();
        List<Curso> cursos = TestesInsercao.criarCursos(fases, alunos, disciplinas);
        List<Matricula> matriculas = TestesInsercao.criarMatriculas(alunos, disciplinas, cursos);

        // EXECUTAR OS TESTES
        TestesInsercao.testarProfessores(professorCRUD, professores);
        TestesInsercao.testarAlunos(alunoCRUD, alunos);
        TestesInsercao.testarDisciplinas(disciplinaCRUD, disciplinas);
        TestesInsercao.testarFases(faseCRUD, disciplinas);
        TestesInsercao.testarCursos(cursoCRUD, cursos);
        TestesInsercao.testarMatriculas(matriculaCRUD, matriculas);
    }
}
