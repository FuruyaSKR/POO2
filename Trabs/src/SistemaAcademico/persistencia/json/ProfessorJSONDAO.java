package SistemaAcademico.persistencia.json;

public class ProfessorJSONDAO {

    private int ID;
    private String Nome;

    public ProfessorJSONDAO(int ID, String Nome) {
        this.ID = ID;
        this.Nome = Nome;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNome() {
        return this.Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }
}
