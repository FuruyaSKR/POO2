package SistemaAcademico.crud;

import SistemaAcademico.classes.Fase;
import SistemaAcademico.persistencia.IPersistencia;

import java.util.List;

public class FaseCRUD {
    private final IPersistencia<Fase> faseDAO;

    public FaseCRUD(IPersistencia<Fase> faseDAO) {
        this.faseDAO = faseDAO;
    }

    public void salvarFase(Fase fase) {
        faseDAO.salvar(fase);
    }

    public Fase buscarFase(int numero) {
        return faseDAO.buscarPorId(numero);
    }

    public void atualizarFase(Fase fase) {
        faseDAO.atualizar(fase);
    }

    public void deletarFase(int numero) {
        faseDAO.deletar(numero);
    }

    public List<Fase> listarTodasFases() {
        return faseDAO.listarTodos();
    }
}
