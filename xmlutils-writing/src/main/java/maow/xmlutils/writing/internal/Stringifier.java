package maow.xmlutils.writing.internal;

import maow.xmlutils.writing.api.model.ElementSpec;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class Stringifier {
    private final boolean prettyPrint;
    private final int indent;

    private StringBuilder sb;

    public Stringifier(boolean prettyPrint, int indent) {
        this.prettyPrint = prettyPrint;
        this.indent = indent;
    }

    private void reset() {
        this.sb = new StringBuilder();
    }

    public String fromElement(ElementSpec element) throws TransformerException, ParserConfigurationException, IOException, SAXException {
        reset();
        writeElement(element);
        return prettyPrint ? getPrettyPrinted() : sb.toString();
    }

    private void writeElement(ElementSpec element) {
        writeStartTag(element);
        writeText(element);
        writeChildren(element);
        writeEndTag(element);
    }

    private void writeStartTag(ElementSpec element) {
        sb
                .append('<')
                .append(element.getName());
        writeAttributes(element);
        if (isNotEmpty(element)) {
            sb.append('>');
        }
    }

    private void writeAttributes(ElementSpec element) {
        for (String key : element.getAttributes().keySet()) {
            final String value = element.getAttributes().get(key);
            sb
                    .append(' ')
                    .append(key)
                    .append("=\"")
                    .append(value)
                    .append('"');
        }
    }

    private void writeText(ElementSpec element) {
        if (hasText(element)) {
            sb.append(element.getText());
        }
    }

    private void writeChildren(ElementSpec element) {
        if (hasChildren(element)) {
            for (ElementSpec child : element.getChildren()) {
                writeElement(child);
            }
        }
    }

    private void writeEndTag(ElementSpec element) {
        if (isNotEmpty(element)) {
            sb
                    .append("</")
                    .append(element.getName())
                    .append('>');
        } else {
            sb.append("/>");
        }
    }

    private Node getDocument() throws ParserConfigurationException, IOException, SAXException {
        final String xml = sb.toString();
        final DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        final InputSource is = new InputSource(new StringReader(xml));
        return db.parse(is);
    }

    private String getPrettyPrinted() throws TransformerException, ParserConfigurationException, IOException, SAXException {
        final Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", String.valueOf(indent));
        final StreamResult result = new StreamResult(new StringWriter());
        final DOMSource source = new DOMSource(getDocument());
        transformer.transform(source, result);
        return result.getWriter().toString();
    }

    private boolean isNotEmpty(ElementSpec element) {
        return hasText(element) || hasChildren(element);
    }

    private boolean hasText(ElementSpec element) {
        return element.getText() != null && !element.getText().equals("");
    }

    private boolean hasChildren(ElementSpec element) {
        return !element.getChildren().isEmpty();
    }
}