package SistemaAcademico.persistencia.json;

import SistemaAcademico.classes.Professor;
import SistemaAcademico.persistencia.IPersistencia;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ProfessorJSONDAO implements IPersistencia<Professor> {

    private static final String FILE_PATH = "Saida/professores.xml";
    private Map<Integer, Professor> banco = new HashMap<>();
    private Gson gson = new Gson();

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
        File diretorio = new File("Saida");
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(banco, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregar() {
        File file = new File(FILE_PATH);
        if (!file.exists())
            return;

        try (Reader reader = new FileReader(file)) {
            Type type = new TypeToken<Map<Integer, Professor>>() {
            }.getType();
            Map<Integer, Professor> dados = gson.fromJson(reader, type);
            if (dados != null) {
                banco = dados;
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar JSON. O arquivo pode estar corrompido. Excluindo...");
            file.delete();
        }
    }

}
