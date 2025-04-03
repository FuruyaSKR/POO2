package SistemaAcademico.persistencia.json;

import SistemaAcademico.classes.Professor;
import SistemaAcademico.persistencia.IPersistencia;

import java.io.*;
import java.util.HashMap;

public class ProfessorJSONDAO implements IPersistencia<Professor> {

    private static final String FILE_PATH = "professores.json";
    private HashMap<Integer, Professor> banco = new HashMap<>();

    public ProfessorJSONDAO() {
        carregar();
    }

    @Override
    public void salvar(Professor professor) {
        banco.put(professor.getId(), professor);
        salvarEmArquivo();
    }

    @Override
    public Professor buscarPorId(int id) {
        return banco.get(id);
    }

    @Override
    public void atualizar(Professor professor) {
        banco.put(professor.getId(), professor);
        salvarEmArquivo();
    }

    @Override
    public void deletar(int id) {
        banco.remove(id);
        salvarEmArquivo();
    }

    private void salvarEmArquivo() {
        try (FileWriter writer = new FileWriter(FILE_PATH);
                BufferedWriter bw = new BufferedWriter(writer)) {

            for (Professor p : banco.values()) {
                bw.write(p.getId() + ";" + p.getNome());
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregar() {
        File file = new File(FILE_PATH);
        if (!file.exists())
            return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                int id = Integer.parseInt(partes[0]);
                String nome = partes[1];
                banco.put(id, new Professor(id, nome));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
