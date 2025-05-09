package SistemaAcademico;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import SistemaAcademico.classes.Aluno;
import SistemaAcademico.classes.Avaliacao;
import SistemaAcademico.classes.Curso;
import SistemaAcademico.classes.Disciplina;
import SistemaAcademico.classes.Fase;
import SistemaAcademico.classes.Frequencia;
import SistemaAcademico.classes.Matricula;
import SistemaAcademico.classes.Professor;
import SistemaAcademico.classes.SituacaoAlunoEnum;
import SistemaAcademico.crud.AlunoCRUD;
import SistemaAcademico.crud.CursoCRUD;
import SistemaAcademico.crud.DisciplinaCRUD;
import SistemaAcademico.crud.FaseCRUD;
import SistemaAcademico.crud.MatriculaCRUD;
import SistemaAcademico.crud.ProfessorCRUD;

public class TestesInsercao {

    public static List<Professor> criarProfessores() {
        return List.of(
                new Professor(1, "Maria Oliveira"),
                new Professor(2, "Carlos Souza"),
                new Professor(3, "Ana Lima"),
                new Professor(4, "Roberto Matos"));
    }

    public static List<Aluno> criarAlunos() {
        return List.of(
                new Aluno(1, "João da Silva"),
                new Aluno(2, "Fernanda Costa"),
                new Aluno(3, "Lucas Pereira"),
                new Aluno(4, "Mariana Rocha"));
    }

    public static List<Disciplina> criarDisciplinas(List<Professor> professores, List<Aluno> alunos) {
        Disciplina d1 = new Disciplina(1, "Matemática I", true);
        d1.setCapacidadeMaxima(1);
        d1.adicionarProfessor(professores.get(1));
        d1.adicionarAluno(alunos.get(1));
        d1.adicionarAluno(alunos.get(3));

        Disciplina d2 = new Disciplina(2, "Algoritmos", true);
        d2.setCapacidadeMaxima(2);
        d2.adicionarProfessor(professores.get(1));
        d2.adicionarProfessor(professores.get(3));
        d2.adicionarAluno(alunos.get(1));
        d2.adicionarAluno(alunos.get(2));

        Disciplina d3 = new Disciplina(3, "Estrutura de Dados", false);
        d3.setCapacidadeMaxima(2);

        Disciplina d4 = new Disciplina(4, "Banco de Dados", false);
        d4.setCapacidadeMaxima(2);

        return List.of(d1, d2, d3, d4);
    }

    public static List<Curso> criarCursos(List<Fase> fases, List<Aluno> alunos, List<Disciplina> disciplinas) {
        Curso curso1 = new Curso(1, "Ciência da Computação");

        for (Fase f : fases) {
            curso1.adicionarFase(f);
        }

        for (Aluno a : alunos) {
            curso1.adicionarAluno(a);
        }

        for (Disciplina d : disciplinas) {
            curso1.ofertarDisciplina(d);
        }

        return List.of(curso1);
    }

    public static List<Matricula> criarMatriculas(List<Aluno> alunos, List<Disciplina> disciplinas,
            List<Curso> cursos) {
        List<Matricula> matriculas = new ArrayList<>();

        Curso curso = cursos.get(0); // usando o primeiro curso criado

        Matricula m1 = new Matricula(1, alunos.get(1), disciplinas.get(0), curso);
        Matricula m2 = new Matricula(2, alunos.get(2), disciplinas.get(1), curso);
        Matricula m3 = new Matricula(3, alunos.get(0), disciplinas.get(3), curso);

        matriculas.add(m1);
        matriculas.add(m2);
        matriculas.add(m3);

        return matriculas;
    }

    // --------------------------------------------------------------------------------------------------------------------

    public static void testarProfessores(ProfessorCRUD professorCRUD, List<Professor> professores) {
        System.out.println("----- Testando Professores -----");

        for (Professor p : professores) {
            professorCRUD.salvarProfessor(p);
        }

        professores.get(1).setNome("Carlos Souza Atualizado");
        professorCRUD.atualizarProfessor(professores.get(1));

        professorCRUD.deletarProfessor(professores.get(2).getId());

        System.out.println("\nListando todos os professores após operações:");
        for (Professor p : professorCRUD.listarTodosProfessores()) {
            System.out.println(p.getId() + " - " + p.getNome());
        }
    }

    public static void testarAlunos(AlunoCRUD alunoCRUD, List<Aluno> alunos) {
        System.out.println("\n----- Testando Alunos -----");

        for (Aluno a : alunos) {
            alunoCRUD.salvarAluno(a);
        }

        alunos.get(3).setNome("Mariana Rocha Atualizada");
        alunoCRUD.atualizarAluno(alunos.get(3));

        alunoCRUD.deletarAluno(alunos.get(0).getId());

        System.out.println("\nListando todos os alunos após operações:");
        for (Aluno a : alunoCRUD.listarTodosAlunos()) {
            System.out.println(a.getId() + " - " + a.getNome());
        }
    }

    public static void testarDisciplinas(DisciplinaCRUD disciplinaCRUD, List<Disciplina> disciplinas) {
        System.out.println("\n----- Testando Disciplinas -----");

        for (Disciplina d : disciplinas) {
            disciplinaCRUD.salvarDisciplina(d);
        }

        Disciplina d2 = disciplinas.get(1);
        d2.setNome("Algoritmos e Lógica");
        disciplinaCRUD.atualizarDisciplina(d2);

        disciplinaCRUD.deletarDisciplina(disciplinas.get(2).getId());

        System.out.println("\nListando todas as disciplinas após operações:");
        for (Disciplina d : disciplinaCRUD.listarTodasDisciplinas()) {
            System.out.println(d.getId() + " - " + d.getNome() + " (Ofertada: " + d.isOfertada() + ")");
            System.out.println("Professores: " + d.listarProfessores().stream().map(Professor::getNome).toList());
            System.out.println("Tem vaga? " + d.temVaga());
            System.out.println();
        }
    }

    public static void testarFases(FaseCRUD faseCRUD, List<Disciplina> disciplinas) {
        System.out.println("\n----- Testando Fases -----");

        Fase f1 = new Fase(1, "1ª Fase");
        Fase f2 = new Fase(2, "2ª Fase");
        Fase f3 = new Fase(3, "3ª Fase");

        f1.adicionarDisciplina(disciplinas.get(0));
        f1.adicionarDisciplina(disciplinas.get(1));
        f2.adicionarDisciplina(disciplinas.get(3));

        faseCRUD.salvarFase(f1);
        faseCRUD.salvarFase(f2);

        f3.setNome("32ª Fase Atualizada");
        faseCRUD.atualizarFase(f3);

        faseCRUD.deletarFase(3);

        System.out.println("\nListando disciplinas da fase 2:");
        for (Disciplina d : f2.listarDisciplinas()) {
            System.out.println(d.getId() + " - " + d.getNome());
        }

        System.out.println("\nListando todas as fases após operações:");
        for (Fase f : faseCRUD.listarTodasFases()) {
            System.out.println(f.getNumero() + " - " + f.getNome());
        }
    }

    public static void testarCursos(CursoCRUD cursoCRUD, List<Curso> cursos) {
        System.out.println("\n----- Testando Cursos -----");

        for (Curso c : cursos) {
            cursoCRUD.salvarCurso(c);
        }

        for (Curso c : cursoCRUD.listarTodosCursos()) {
            System.out.println("Curso: " + c.getNome());
            System.out.println("Fases: " + c.getFases().size());

            long ofertadas = c.getFases().stream()
                    .flatMap(f -> f.listarDisciplinas().stream())
                    .filter(Disciplina::isOfertada)
                    .count();
            System.out.println("Disciplinas ofertadas: " + ofertadas);

            System.out.println("Alunos vinculados ao curso:");
            for (Aluno aluno : c.getAlunos()) {
                System.out.println("- " + aluno.getNome() + " (Curso: " +
                        (aluno.getCurso() != null ? aluno.getCurso().getNome() : "Nenhum") + ")");
            }

            List<Disciplina> todasDisciplinas = c.getFases().stream()
                    .flatMap(f -> f.listarDisciplinas().stream())
                    .filter(Disciplina::isOfertada)
                    .toList();

            List<Aluno> alunos = c.getAlunos();

            for (int i = 0; i < Math.min(todasDisciplinas.size(), alunos.size()); i++) {
                Disciplina disciplina = todasDisciplinas.get(i);
                Aluno aluno = alunos.get(i);
                Matricula m = c.matricularAluno(aluno, disciplina);
                if (m != null) {
                    System.out.printf("Matrícula realizada: Aluno %s na disciplina %s (ID %d)\n",
                            aluno.getNome(), disciplina.getNome(), m.getId());
                } else {
                    System.out.printf("Falha ao matricular aluno %s na disciplina %s\n",
                            aluno.getNome(), disciplina.getNome());
                }
            }

            for (Aluno aluno : alunos) {
                for (Disciplina d : todasDisciplinas) {
                    SituacaoAlunoEnum situacao = c.getSituacaoPorDisciplina(aluno, d);
                    if (situacao != null) {
                        System.out.printf("Aluno %s - Disciplina %s: Situação %s\n",
                                aluno.getNome(), d.getNome(), situacao);
                    }
                }
            }

            System.out.println();
        }
    }

    public static void testarMatriculas(MatriculaCRUD matriculaCRUD, List<Matricula> matriculas) {
        System.out.println("\n----- Testando Matrículas -----");

        for (Matricula m : matriculaCRUD.listarTodasMatriculas()) {
            matriculaCRUD.deletarMatricula(m.getId());
        }

        Random random = new Random();

        for (Matricula m : matriculas) {
            Disciplina disciplina = m.getDisciplina();
            List<Professor> professores = disciplina.listarProfessores();

            if (!disciplina.isOfertada()) {
                System.out.printf(
                        "Matrícula ID: %d - Disciplina %d não está ofertada, registros não foram realizados.%n",
                        m.getId(), disciplina.getId());
                continue;
            }

            if (professores.isEmpty()) {
                System.out.printf("Matrícula ID: %d - Disciplina %d sem professor, registros não foram realizados.%n",
                        m.getId(), disciplina.getId());
                continue;
            }

            Professor prof = professores.get(random.nextInt(professores.size()));

            for (int i = 0; i < 3; i++) {
                double nota = 5 + random.nextDouble() * 5;
                m.registrarAvaliacao(new Avaliacao(nota, prof));
            }

            for (int i = 0; i < 10; i++) {
                boolean presente = random.nextInt(100) < 60;
                m.registrarFrequencia(new Frequencia(LocalDate.now().minusDays(i), presente, prof));
            }

            m.atualizarSituacao();
            matriculaCRUD.salvarMatricula(m);
        }

        for (Matricula m : matriculaCRUD.listarTodasMatriculas()) {
            System.out.println("Matrícula ID: " + m.getId());
            System.out.println("Aluno: " + m.getAluno().getId());
            System.out.println("Disciplina: " + m.getDisciplina().getId());
            System.out.printf("Média: %.2f\n", m.calcularMedia());
            System.out.printf("Frequência: %.2f%%\n", m.calcularFrequencia());
            System.out.println("Situação Final: " + m.getSituacaoFinal());
            System.out.println();
        }
    }

}
