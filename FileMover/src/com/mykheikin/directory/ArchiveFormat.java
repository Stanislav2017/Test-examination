package com.mykheikin.directory;

public enum ArchiveFormat {

    SEVEN_Z("7z"), ACE("ace"), RAR("rar"), ISO("iso"), ZIP("zip"), TAR("tar");

    private String extension;

    ArchiveFormat(String extension) {
        this.extension = extension;
    }

    @Override
    public String toString() {
        return extension.toLowerCase();
    }
}
