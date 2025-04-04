package SistemaAcademico.crud;

import SistemaAcademico.classes.Curso;
import SistemaAcademico.persistencia.IPersistencia;

import java.util.List;

public class CursoCRUD {
    private final IPersistencia<Curso> persistencia;

    public CursoCRUD(IPersistencia<Curso> persistencia) {
        this.persistencia = persistencia;
    }

    public void salvarCurso(Curso curso) {
        persistencia.salvar(curso);
    }

    public Curso buscarCurso(int id) {
        return persistencia.buscarPorId(id);
    }

    public void atualizarCurso(Curso curso) {
        persistencia.atualizar(curso);
    }

    public void deletarCurso(int id) {
        persistencia.deletar(id);
    }

    public List<Curso> listarTodosCursos() {
        return persistencia.listarTodos();
    }
}
