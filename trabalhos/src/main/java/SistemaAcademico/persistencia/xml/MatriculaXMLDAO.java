package SistemaAcademico.persistencia.xml;

import SistemaAcademico.classes.Matricula;
import SistemaAcademico.persistencia.IPersistencia;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.*;

public class MatriculaXMLDAO implements IPersistencia<Matricula> {
    private static final String FILE_PATH = "Saida/matriculas.xml";
    private final Map<Integer, Matricula> mapa = new HashMap<>();

    @Override
    public void salvar(Matricula m) {
        mapa.put(m.getId(), m);
        salvarArquivo();
    }

    @Override
    public Matricula buscarPorId(int id) {
        return mapa.get(id);
    }

    @Override
    public void atualizar(Matricula m) {
        mapa.put(m.getId(), m);
        salvarArquivo();
    }

    @Override
    public void deletar(int id) {
        mapa.remove(id);
        salvarArquivo();
    }

    @Override
    public List<Matricula> listarTodos() {
        return new ArrayList<>(mapa.values());
    }

    private void salvarArquivo() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element root = doc.createElement("matriculas");
            doc.appendChild(root);

            for (Matricula m : mapa.values()) {
                Element matricula = doc.createElement("matricula");
                root.appendChild(matricula);

                Element id = doc.createElement("id");
                id.appendChild(doc.createTextNode(String.valueOf(m.getId())));
                matricula.appendChild(id);

                Element alunoId = doc.createElement("aluno_id");
                alunoId.appendChild(doc.createTextNode(String.valueOf(m.getAluno().getId())));
                matricula.appendChild(alunoId);

                Element disciplinaId = doc.createElement("disciplina_id");
                disciplinaId.appendChild(doc.createTextNode(String.valueOf(m.getDisciplina().getId())));
                matricula.appendChild(disciplinaId);

                Element situacao = doc.createElement("situacaoFinal");
                situacao.appendChild(doc.createTextNode(m.getSituacaoFinal().toString()));
                matricula.appendChild(situacao);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(new File(FILE_PATH)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
