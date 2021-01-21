package maow.xmlutils.writing.api.model;

import maow.xmlutils.writing.api.util.WritingUtils;

import java.util.*;

public class ElementSpec {
    private final String name;
    private final String text;
    private final Map<String, String> attributes;
    private final List<ElementSpec> children;

    private ElementSpec(String name, String text, Map<String, String> attributes, List<ElementSpec> children) {
        this.name = name;
        this.text = text;
        this.attributes = attributes;
        this.children = children;
    }

    public static Builder builder(String name) {
        return new Builder(name);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public List<ElementSpec> getChildren() {
        return children;
    }

    public static class Builder {
        private String name;
        private String text = "";
        private final Map<String, String> attributes = new LinkedHashMap<>();
        private final List<ElementSpec> children = new ArrayList<>();

        private Builder(String name) {
            this.name = name;
        }

        private Builder() {
            this.name = "";
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setText(String text) {
            if (children.isEmpty()) {
                this.text = text;
            }
            return this;
        }

        public Builder addAttributes(AttributeSpec... attributes) {
            for (AttributeSpec attribute : attributes) {
                this.attributes.put(
                        attribute.getKey(),
                        attribute.getValue()
                );
            }
            return this;
        }

        public Builder addChildren(ElementSpec... elements) {
            if (text.isEmpty()) {
                Collections.addAll(children, elements);
            }
            return this;
        }

        public ElementSpec build() {
            return new ElementSpec(name, text, attributes, children);
        }
    }

    @Override
    public String toString() {
        return WritingUtils.stringify(this, false);
    }

    public String toPrettyString(int indent) {
        return WritingUtils.stringify(this, indent);
    }

    public String toPrettyString() {
        return toPrettyString(4);
    }
}