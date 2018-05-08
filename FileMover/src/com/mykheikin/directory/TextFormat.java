package com.mykheikin.directory;

public enum TextFormat {

    TXT("txt"), RTF("rtf"), DOC("doc"), DOCX("docx"), HTML("html"), PDF("pdf"), ODT("odt");

    private String extension;

    TextFormat(String extension) {
        this.extension = extension;
    }

    @Override
    public String toString() {
        return extension.toLowerCase();
    }
}
