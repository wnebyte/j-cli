package util;

public final class Splitter {

    private String content;

    private String delimiter;

    private boolean isArray = true;

    public Splitter setContent(String content) {
        this.content = content;
        return this;
    }

    public Splitter setDelimiter(String delimiter) {
        this.delimiter = delimiter;
        return this;
    }

    public Splitter isArray(boolean value) {
        this.isArray = value;
        return this;
    }

    /*
    public String split() {
        if ((content == null) || (delimiter == null)) {
            throw new IllegalArgumentException(
                    "content and/or delimiter must not be null."
            );
        }
        return content.split(delimiter.concat("\\s"), 2)[1].split("\\s", 2)[0];
    }
     */

    public String split() {
        if (content == null) {
            throw new IllegalArgumentException(
                    "content must not be null"
            );
        }

        String split = content;

        if (delimiter != null) {
            split = content.split(delimiter.concat("\\s"), 2)[1];
        }

        if (split.startsWith("\"")) {
            split = split.replaceFirst("\"", "");
            return split.split("\"", 2)[0];
        }
        else if (split.startsWith("[")) {
            split = split.replaceFirst("\\[", "");
            return split.split("]", 2)[0];
        }
        else {
            return split.split("\\s", 2)[0];
        }
    }
}
