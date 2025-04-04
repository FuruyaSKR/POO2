package SistemaAcademico.crud;

import java.util.List;

import SistemaAcademico.classes.Professor;
import SistemaAcademico.persistencia.IPersistencia;

public class ProfessorCRUD {

    private IPersistencia<Professor> persistencia;

    public ProfessorCRUD(IPersistencia<Professor> persistencia) {
        this.persistencia = persistencia;
    }

    public void salvarProfessor(Professor professor) {
        persistencia.salvar(professor);
    }

    public Professor buscarProfessor(int id) {
        return persistencia.buscarPorId(id);
    }

    public void atualizarProfessor(Professor professor) {
        persistencia.atualizar(professor);
    }

    public void deletarProfessor(int id) {
        persistencia.deletar(id);
    }

    public List<Professor> listarTodosProfessores() {
        return persistencia.listarTodos();
    }

}
