package SistemaAcademico.persistencia.xml;

import SistemaAcademico.classes.Professor;
import SistemaAcademico.persistencia.IPersistencia;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ProfessorXMLDAO implements IPersistencia<Professor> {

    private static final String FILE_PATH = "Saida/professores.xml";
    private Map<Integer, Professor> banco = new HashMap<>();

    public ProfessorXMLDAO() {
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
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("professores");
            doc.appendChild(root);

            for (Professor p : banco.values()) {
                Element profElem = doc.createElement("professor");

                Element idElem = doc.createElement("id");
                idElem.appendChild(doc.createTextNode(String.valueOf(p.getId())));

                Element nomeElem = doc.createElement("nome");
                nomeElem.appendChild(doc.createTextNode(p.getNome()));

                profElem.appendChild(idElem);
                profElem.appendChild(nomeElem);
                root.appendChild(profElem);
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
            NodeList nodeList = doc.getElementsByTagName("professor");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) node;
                    int id = Integer.parseInt(e.getElementsByTagName("id").item(0).getTextContent());
                    String nome = e.getElementsByTagName("nome").item(0).getTextContent();
                    banco.put(id, new Professor(id, nome));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
