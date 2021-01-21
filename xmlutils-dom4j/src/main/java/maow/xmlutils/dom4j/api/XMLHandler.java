package maow.xmlutils.dom4j.api;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class XMLHandler {
    private final Element element;

    public XMLHandler(Element element) {
        this.element = element;
    }

    public XMLHandler(Document document) {
        this.element = document.getRootElement();
    }

    public XMLHandler forOne(String name, Consumer<XMLHandler> consumer) {
        final Element child = element.element(name);
        if (child != null) {
            final XMLHandler handler = new XMLHandler(child);
            consumer.accept(handler);
        }
        return this;
    }

    public XMLHandler forOne(String name, int index, Consumer<XMLHandler> consumer) {
        final Element child = element.elements(name).get(index);
        if (child != null) {
            final XMLHandler handler = new XMLHandler(child);
            consumer.accept(handler);
        }
        return this;
    }

    public XMLHandler forEach(String name, Consumer<XMLHandler> consumer) {
        final List<Element> children = element.elements(name);
        for (Element child : children) {
            final XMLHandler handler = new XMLHandler(child);
            consumer.accept(handler);
        }
        return this;
    }

    public XMLHandler doIf(Predicate<Element> predicate, Consumer<XMLHandler> consumer) {
        if (predicate.test(element)) {
            consumer.accept(this);
        }
        return this;
    }

    public List<Element> getChildren(String name) {
        return element.elements(name);
    }

    public List<Attribute> getAttributes() {
        return element.attributes();
    }

    public List<Attribute> getAttributes(String key) {
        return element.attributes()
                .stream()
                .filter(attribute ->
                        attribute.getName().equals(key)
                )
                .collect(Collectors.toList());
    }

    public List<Attribute> getAttributes(String key, String value) {
        return element.attributes()
                .stream()
                .filter(attribute ->
                        attribute.getName().equals(key) && attribute.getValue().equals(value)
                )
                .collect(Collectors.toList());
    }

    public Element getElement() {
        return element;
    }
}
