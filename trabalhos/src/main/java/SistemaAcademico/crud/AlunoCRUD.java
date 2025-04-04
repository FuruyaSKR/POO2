package SistemaAcademico.crud;

import java.util.List;

import SistemaAcademico.classes.Aluno;
import SistemaAcademico.persistencia.IPersistencia;

public class AlunoCRUD {

    private IPersistencia<Aluno> persistencia;

    public AlunoCRUD(IPersistencia<Aluno> persistencia) {
        this.persistencia = persistencia;
    }

    public void salvarAluno(Aluno aluno) {
        persistencia.salvar(aluno);
    }

    public Aluno buscarAluno(int id) {
        return persistencia.buscarPorId(id);
    }

    public void atualizarAluno(Aluno aluno) {
        persistencia.atualizar(aluno);
    }

    public void deletarAluno(int id) {
        persistencia.deletar(id);
    }

    public List<Aluno> listarTodosAlunos() {
        return persistencia.listarTodos();
    }

}
