package com.sesasis.donusum.yok.core.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class GeneralUtils {
	public static <T> boolean valueNullOrEmpty(final T value) {
		return value == null ||
				(value instanceof String && ((String) ((String) value).trim()).isEmpty()) ||
				(value instanceof Collection && ((Collection<?>) value).isEmpty()) ||
				(value instanceof byte[] && ((byte[]) value).length == 0);
	}

	public static String convertYoutubeLink(String url) {
		if (url == null || url.isEmpty())
			return null;
		if (url.contains("embed")) {
			return url;
		}
		//
		https:
//youtu.be/zP-bcTvTlDk?si=NBBKv0aY57qsdxNz

		if (url.contains("si")) {
			String[] arr = url.split("\\?");
			String str = arr[0];
			String[] arr2 = str.split("/");
			String videoId = arr2[3];
			return "https://www.youtube.com/embed/" + videoId;
		}

		String[] arr = url.split("=");
		String videoId = arr[1];
		return "https://www.youtube.com/embed/" + videoId;
	}

	public static BufferedImage generateEAN13BarcodeImage(String barcodeText) {
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(
					new String(barcodeText.getBytes("UTF-8"), "UTF-8"),
					BarcodeFormat.QR_CODE, 300, 150);
			return MatrixToImageWriter.toBufferedImage(bitMatrix);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getUID(int digitNumber) {
		StringBuilder sb = new StringBuilder(digitNumber);
		for (int i = 0; i < digitNumber; i++) {
			sb.append((char) (Math.random() * 10 + '0'));
		}
		return sb.toString();
	}

	public static byte[] toByteArray(BufferedImage img, String imageFileType) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(img, imageFileType, baos);
			return baos.toByteArray();
		} catch (Throwable e) {
			throw new RuntimeException();
		}
	}

	public static Long getNewQrCode() {
		ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
		return threadLocalRandom.nextLong(10000000000L, 100000000000L);
	}

	public static String generateFileName(MultipartFile multiPart) {
		String contentType = multiPart.getContentType();
		if (contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
			contentType = "application/docx";
		}
		return UUID.randomUUID().toString().replaceAll("-", "") + "." + contentType.split("/")[1];
	}

	public static byte[] filePathFromResources(String uploadDir, String filename) {
		try {
			Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource.getInputStream().readAllBytes();
			} else {
				return null;
			}
		} catch (MalformedURLException e) {
			return null;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
