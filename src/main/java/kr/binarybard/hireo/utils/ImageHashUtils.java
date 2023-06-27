package kr.binarybard.hireo.utils;

import java.security.MessageDigest;

import org.springframework.web.multipart.MultipartFile;

import kr.binarybard.hireo.common.exceptions.ErrorCode;
import kr.binarybard.hireo.common.exceptions.FileProcessingException;

public class ImageHashUtils {
	private ImageHashUtils() {
		throw new IllegalStateException("인스턴스화 할 수 없습니다.");
	}

	public static String hashImageFileWithMD5(MultipartFile file) {
		try {
			return getHashedFileName(file, "MD5");
		} catch (Exception e) {
			throw new FileProcessingException(ErrorCode.FILE_UPLOAD_FAILED);
		}
	}

	private static String getHashedFileName(MultipartFile file, String algorithm) throws Exception {
		MessageDigest digest = MessageDigest.getInstance(algorithm);
		byte[] hashedBytes = digest.digest(file.getBytes());
		return byteToHex(hashedBytes) + getFileExtension(file.getOriginalFilename());
	}

	private static String getFileExtension(String originalFilename) {
		int dotIndex = originalFilename.lastIndexOf(".");
		if (dotIndex > 0) {
			return originalFilename.substring(dotIndex);
		}
		return "";
	}

	private static String byteToHex(byte[] hashedBytes) {
		StringBuilder hexString = new StringBuilder();
		for (byte b : hashedBytes) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}

}
