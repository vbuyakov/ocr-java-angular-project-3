package com.buyakov.ja.chatop.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {
    @Value("${spring.application.storage.images}")
    private String rootDir;

    public String store(MultipartFile file, String subFolder ) throws IOException {
            Path dir = Paths.get(rootDir + subFolder);
            Files.createDirectories(dir);

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path target = dir.resolve(fileName);

            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
    }
}
