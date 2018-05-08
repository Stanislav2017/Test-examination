package com.mykheikin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FileService {

    void deleteFilesWithExtension(Path sourceDir, String extension) throws IOException;

    void move(Path sourceDir, Path destinationDir, String extension) throws IOException;

    void copy(Path sourceDir, Path destinationDir, String extension) throws IOException;

    void copy(Path sourceDir, Path destinationDir, String extension, Long timeStart, Long timeFinish) throws IOException;

}
