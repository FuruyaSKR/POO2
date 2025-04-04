package SistemaAcademico.persistencia.xml;

import SistemaAcademico.classes.Curso;
import SistemaAcademico.persistencia.IPersistencia;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import java.io.File;
import java.util.*;

public class CursoXMLDAO implements IPersistencia<Curso> {
    private static final String FILE_PATH = "Saida/cursos.xml";
    private final Map<Integer, Curso> mapa = new HashMap<>();

    @Override
    public void salvar(Curso curso) {
        mapa.put(curso.getId(), curso);
        salvarArquivo();
    }

    @Override
    public Curso buscarPorId(int id) {
        return mapa.get(id);
    }

    @Override
    public void atualizar(Curso curso) {
        mapa.put(curso.getId(), curso);
        salvarArquivo();
    }

    @Override
    public void deletar(int id) {
        mapa.remove(id);
        salvarArquivo();
    }

    @Override
    public List<Curso> listarTodos() {
        return new ArrayList<>(mapa.values());
    }

    private void salvarArquivo() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element root = doc.createElement("cursos");
            doc.appendChild(root);

            for (Curso c : mapa.values()) {
                Element curso = doc.createElement("curso");
                root.appendChild(curso);

                Element id = doc.createElement("id");
                id.appendChild(doc.createTextNode(String.valueOf(c.getId())));
                curso.appendChild(id);

                Element nome = doc.createElement("nome");
                nome.appendChild(doc.createTextNode(c.getNome()));
                curso.appendChild(nome);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(new File(FILE_PATH)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
