package SistemaAcademico.persistencia.json;

import SistemaAcademico.classes.Fase;
import SistemaAcademico.persistencia.IPersistencia;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class FaseJSONDAO implements IPersistencia<Fase> {
    private static final String FILE_PATH = "saida/fases.json";
    private final Gson gson = new Gson();
    private Map<Integer, Fase> mapa = new HashMap<>();

    public FaseJSONDAO() {
        carregar();
    }

    @Override
    public void salvar(Fase fase) {
        mapa.put(fase.getNumero(), fase);
        salvarArquivo();
    }

    @Override
    public Fase buscarPorId(int id) {
        return mapa.get(id);
    }

    @Override
    public void atualizar(Fase fase) {
        mapa.put(fase.getNumero(), fase);
        salvarArquivo();
    }

    @Override
    public void deletar(int id) {
        mapa.remove(id);
        salvarArquivo();
    }

    @Override
    public List<Fase> listarTodos() {
        return new ArrayList<>(mapa.values());
    }

    private void carregar() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type type = new TypeToken<Map<Integer, Fase>>() {
            }.getType();
            mapa = gson.fromJson(reader, type);
            if (mapa == null)
                mapa = new HashMap<>();
        } catch (IOException e) {
            mapa = new HashMap<>();
        }
    }

    private void salvarArquivo() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(mapa, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
