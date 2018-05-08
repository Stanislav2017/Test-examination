package com.mykheikin.directory;

public enum VideoFormat {

    AVI("avi"), FLV("flv"), MP4("mp4");

    private String extension;

    VideoFormat(String extension) {
        this.extension = extension;
    }
}
