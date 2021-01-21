package maow.xmlutils.writing.api.util;

import maow.xmlutils.writing.api.model.AttributeSpec;
import maow.xmlutils.writing.internal.Stringifier;
import maow.xmlutils.writing.api.model.ElementSpec;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WritingUtils {
    public static String stringify(ElementSpec element, boolean prettyPrint, int indent) {
        final Stringifier stringifier = new Stringifier(prettyPrint, indent);
        try {
            return stringifier.fromElement(element);
        } catch (TransformerException | ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String stringify(ElementSpec element, boolean prettyPrint) {
        return stringify(element, prettyPrint, 4);
    }

    public static String stringify(ElementSpec element, int indent) {
        return stringify(element, true, indent);
    }

    public static String stringify(ElementSpec element) {
        return stringify(element, true, 4);
    }

    public static String stringify(AttributeSpec attribute) {
        return attribute.getKey() +
                "=\"" +
                attribute.getValue() +
                '"';
    }

    public static void write(String filepath, ElementSpec element, boolean prettyPrint, int indent) {
        final Path path = Paths.get(filepath);
        final String xml = stringify(element, prettyPrint, indent);
        try (final BufferedWriter bw = Files.newBufferedWriter(path)) {
            bw.write(xml);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(String filepath, ElementSpec element, boolean prettyPrint) {
        write(filepath, element, prettyPrint, 4);
    }

    public static void write(String filepath, ElementSpec element, int indent) {
        write(filepath, element, true, indent);
    }

    public static void write(String filepath, ElementSpec element) {
        write(filepath, element, true, 4);
    }
}
