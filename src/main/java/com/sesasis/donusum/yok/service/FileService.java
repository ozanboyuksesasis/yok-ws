package com.sesasis.donusum.yok.service;

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

	public Path saveFile(MultipartFile file,String fileName) throws IOException {
		byte[] bytes = file.getBytes();
		Path path = Paths.get(uploadDir + File.separator + fileName);
		Files.write(path, bytes);
		return path;
	}

	public String getFileAsBase64(String filename) throws IOException {
		Path path = Paths.get(uploadDir).resolve(filename).normalize();
		byte[] fileContent = Files.readAllBytes(path);
		return Base64.getEncoder().encodeToString(fileContent);
	}
}
