package maow.xmlutils.writing.api.model;

import maow.xmlutils.writing.api.util.WritingUtils;

public class AttributeSpec {
    private final String key;
    private final String value;

    private AttributeSpec(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static AttributeSpec create(String key, String value) {
        return new AttributeSpec(key, value);
    }

    public static AttributeSpec create(String key) {
        return new AttributeSpec(key, "");
    }

    @Override
    public String toString() {
        return WritingUtils.stringify(this);
    }
}
