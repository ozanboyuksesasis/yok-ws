package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.enums.DosyaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class FileService {

    @Value("${upload.dir}")
    private String uploadDir;

    @Value("${upload.anasayfa-story-hızlı-baglantı.story}")
    private String storyDir;

    @Value("${upload.anasayfa-story-hızlı-baglantı.hızlıbaglantı}")
    private String hizliBaglantiDir;

    public Path saveFile(MultipartFile file, String fileName) throws IOException {
        byte[] bytes = file.getBytes();
        Path path = Paths.get(uploadDir + File.separator + fileName);
        Files.write(path, bytes);
        return path;
    }

    public Path saveDosya(MultipartFile file, String fileName, DosyaType dosyaType) throws IOException {
        String selected;
        if (dosyaType.equals(DosyaType.HIZLIBAGLANTI)) {
            selected = hizliBaglantiDir;
        } else if (dosyaType.equals(DosyaType.STORIE)) {
            selected = storyDir;
        } else {
            throw new IllegalArgumentException("Geçersiz dosya türü.");
        }

        byte[] bytes = file.getBytes();
        Path path = Paths.get(uploadDir + File.separator + fileName);
        Files.write(path, bytes);
        return path;
    }

    public String getFileAsBase64(String filename) throws IOException {
        Path path = Paths.get(uploadDir).resolve(filename).normalize();
        if (Files.exists(path)) {
            byte[] fileContent = Files.readAllBytes(path);
            return Base64.getEncoder().encodeToString(fileContent);
        } else {
            throw new RuntimeException("File not found: " + filename);
        }
    }
}
