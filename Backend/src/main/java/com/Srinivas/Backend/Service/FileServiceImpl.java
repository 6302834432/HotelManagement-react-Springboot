package com.Srinivas.Backend.Service;

import com.Srinivas.Backend.Service.interfac.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {

    @Value("${server.port}")
    private int serverPort;

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        // File Name
        String fileName = file.getOriginalFilename();

        // Full Path
        Path fullPath = Paths.get(path, fileName);

        // Check if directory exists, if not create it
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();  // creates the directory along with any necessary but nonexistent parent directories
        }

        // Copy the file to the target location
        Files.copy(file.getInputStream(), fullPath);

        // Construct the image URL
        String imageUrl = "http://localhost:" + serverPort + "/images/" + fileName;

        return imageUrl;
    }
}
