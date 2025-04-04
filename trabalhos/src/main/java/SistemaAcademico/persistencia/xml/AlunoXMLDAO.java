package SistemaAcademico.persistencia.xml;

import SistemaAcademico.classes.Aluno;
import SistemaAcademico.persistencia.IPersistencia;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlunoXMLDAO implements IPersistencia<Aluno> {

    private static final String FILE_PATH = "Saida/alunos.xml";
    private Map<Integer, Aluno> banco = new HashMap<>();

    public AlunoXMLDAO() {
        carregar();
    }

    @Override
    public void salvar(Aluno aluno) {
        banco.put(aluno.getId(), aluno);
        salvarEmArquivo();
    }

    @Override
    public Aluno buscarPorId(int id) {
        return banco.get(id);
    }

    @Override
    public void atualizar(Aluno aluno) {
        banco.put(aluno.getId(), aluno);
        salvarEmArquivo();
    }

    @Override
    public void deletar(int id) {
        banco.remove(id);
        salvarEmArquivo();
    }

    @Override
    public List<Aluno> listarTodos() {
        return new ArrayList<>(banco.values());
    }

    private void salvarEmArquivo() {
        try {
            File dir = new File("saida");
            if (!dir.exists())
                dir.mkdirs();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("alunos");
            doc.appendChild(root);

            for (Aluno a : banco.values()) {
                Element alunoElem = doc.createElement("aluno");

                Element idElem = doc.createElement("id");
                idElem.appendChild(doc.createTextNode(String.valueOf(a.getId())));

                Element nomeElem = doc.createElement("nome");
                nomeElem.appendChild(doc.createTextNode(a.getNome()));

                alunoElem.appendChild(idElem);
                alunoElem.appendChild(nomeElem);
                root.appendChild(alunoElem);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(FILE_PATH));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void carregar() {
        File file = new File(FILE_PATH);
        if (!file.exists())
            return;

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(file);
            NodeList nodeList = doc.getElementsByTagName("aluno");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element e = (Element) nodeList.item(i);
                int id = Integer.parseInt(e.getElementsByTagName("id").item(0).getTextContent());
                String nome = e.getElementsByTagName("nome").item(0).getTextContent();
                banco.put(id, new Aluno(id, nome));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
