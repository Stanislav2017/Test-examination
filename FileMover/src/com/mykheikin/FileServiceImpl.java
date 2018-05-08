package com.mykheikin;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class FileServiceImpl implements FileService {

    private Integer fileRepeat = 1;

    @Override
    public void deleteFilesWithExtension(Path sourceDir, String extension) throws IOException {
        List<File> files = this.findFilesInDirWithExtension(sourceDir, extension);
        if (!files.isEmpty()) {
            files.stream().forEachOrdered(FileUtils::deleteQuietly);
        }
    }

    @Override
    public void move(Path sourceDir, Path destinationDir, String extension) throws IOException {
        List<File> files = this.findFilesInDirWithExtension(sourceDir, extension);
        if (!files.isEmpty()) {
            for (File file : files) {
                File destinationFile = this.createDestinationFile(destinationDir.toFile(), file);
                FileUtils.moveFile(file, destinationFile);
            }
        }
    }

    @Override
    public void copy(Path sourceDir, Path destinationDir, String extension) throws IOException {
        List<File> files = this.findFilesInDirWithExtension(sourceDir, extension);
        if (!files.isEmpty()) {
            for (File file : files) {
                File destinationFile = this.createDestinationFile(destinationDir.toFile(), file);
                FileUtils.copyFile(file, destinationFile);
            }
        }
    }

    @Override
    public void copy(
            Path sourceDir,
            Path destinationDir,
            String extension,
            Long timeStart,
            Long timeFinish)
            throws IOException
    {
        List<File> files = this.findFilesInDirCreatedBetween(sourceDir, extension, timeStart, timeFinish);
        if (!files.isEmpty()) {
            for (File file : files) {
                File destinationFile = this.createDestinationFile(destinationDir.toFile(), file);
                FileUtils.copyFile(file, destinationFile);
            }
        }
    }

    public List<File> findFilesInDirWithExtension(Path sourceDir, String extension) throws IOException {
        List<File> files = Files.walk(sourceDir, FileVisitOption.FOLLOW_LINKS)
                .filter((p) -> !p.toFile().isDirectory() && this.isPathWithExtension(p, extension))
                .map(Path::toFile)
                .collect(Collectors.toList());
        return files;
    }

    private List<File> findFilesInDirCreatedBetween(
            Path sourceDir,
            String extension,
            Long startTime,
            Long finishTime)
            throws IOException
    {
        List<File> files = Files.walk(sourceDir, FileVisitOption.FOLLOW_LINKS)
                .filter((p) ->
                        !p.toFile().isDirectory()
                                && this.isPathWithExtension(p, extension)
                                && this.isPathCreatedBetween(p, startTime, finishTime))
                .map(Path::toFile)
                .collect(Collectors.toList());
        return files;
    }

    private List<File> getFileByCreatorNameAndExtension(
            Path sourceDir,
            String creatorName,
            String extension)
            throws IOException
    {
        List<File> files = Files.walk(sourceDir, FileVisitOption.FOLLOW_LINKS)
                .filter((p) ->
                        this.isPathAuthorName(p, creatorName)
                                && this.isFile(p)
                                && this.isPathWithExtension(p, extension))
                .map(Path::toFile)
                .collect(Collectors.toList());
        return files;
    }

    private boolean isFile(Path destinationPath) {
        return destinationPath.toFile().isFile();
    }

    private boolean isPathWithExtension(Path destinationPath, String searchExtension) {
        String extension = FilenameUtils.getExtension(destinationPath.toFile().getName());
        return searchExtension.equals(extension);
    }

    private boolean isPathCreatedBetween(Path destinationPath, Long startTime, Long finshTime) {
        BasicFileAttributes attr = null;
        try {
            attr = Files.readAttributes(destinationPath, BasicFileAttributes.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Long createdTime = attr.creationTime().to(TimeUnit.MICROSECONDS);
        return startTime <= createdTime && createdTime <= finshTime;
    }

    private boolean isPathAuthorName(Path destinationPath, String searchAuthorName) {
        PosixFileAttributes attr = null;
        try {
            attr = Files.readAttributes(destinationPath, PosixFileAttributes.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String authorName = attr.owner().getName();
        return searchAuthorName.equals(authorName);
    }

    private File createDestinationFile(File destinatioDir, File sourceFile) {
        File dirFile = new File(destinatioDir, sourceFile.getName());
        while (dirFile.exists()) {
            String fileName = this.getNewDestFile(sourceFile);
            dirFile = new File(destinatioDir, fileName);
            this.fileRepeat++;
        }
        this.fileRepeat = 1;
        return dirFile;
    }

    private String getNewDestFile(File sourceFile) {
        String name = FilenameUtils.getBaseName(sourceFile.getName());
        String extension = FilenameUtils.getExtension(sourceFile.getName());
        String newDestFile = name + "_" + this.fileRepeat + "." + extension;
        return newDestFile;
    }
}
