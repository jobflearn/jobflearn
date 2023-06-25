package kr.binarybard.hireo.api.file.service;

import kr.binarybard.hireo.api.file.dto.FileResponse;
import kr.binarybard.hireo.common.exceptions.ErrorCode;
import kr.binarybard.hireo.common.exceptions.FileProcessingException;
import kr.binarybard.hireo.common.exceptions.InvalidValueException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Service
public class FileService {
	private final Path root;

	public FileService(@Value("${file.upload-dir}") String uploadDir) {
		this.root = Paths.get(uploadDir);
		try {
			Files.createDirectories(root);
		} catch (IOException ex) {
			throw new FileProcessingException(ErrorCode.FILE_STORAGE_FAILED, "디렉토리 생성 실패: " + root);
		}
	}

	public FileResponse store(MultipartFile file) {
		String fileName = validateFileName(file.getOriginalFilename());
		validateFileSize(file.getSize());
		Path filePath = saveFile(file, fileName);
		return buildFileResponse(filePath, file.getSize(), file.getContentType());
	}


	public FileResponse storeAsHash(MultipartFile file) {
		try {
			validateFileSize(file.getSize());
			byte[] fileBytes = file.getBytes();
			String hashedName = hashFile(fileBytes);
			Path filePath = saveFile(file, hashedName);
			return buildFileResponse(filePath, file.getSize(), file.getContentType());
		} catch (IOException ex) {
			throw new FileProcessingException(ErrorCode.FILE_STORAGE_FAILED, "파일명: " + file.getOriginalFilename());
		}
	}

	private FileResponse buildFileResponse(Path filePath, long fileSize, String contentType) {
		return FileResponse.builder()
			.fileName(filePath.getFileName().toString())
			.fileSize(fileSize)
			.contentType(contentType)
			.uploadTimestamp(LocalDateTime.now())
			.build();
	}

	private Path saveFile(MultipartFile file, String fileName) {
		Path targetLocation = root.resolve(fileName);
		try {
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ex) {
			throw new FileProcessingException(ErrorCode.FILE_UPLOAD_FAILED, "파일명: " + fileName);
		}
		return targetLocation;
	}

	public Resource load(String fileName) {
		Path filePath = root.resolve(fileName);
		Resource resource;
		try {
			resource = new UrlResource(filePath.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			throw new FileProcessingException(ErrorCode.FILE_NOT_FOUND, "파일명: " + fileName);
		} catch (MalformedURLException ex) {
			throw new FileProcessingException(ErrorCode.FILE_NOT_FOUND, "파일명: " + fileName);
		}
	}

	private String validateFileName(String originalFilename) {
		if (originalFilename == null || originalFilename.isEmpty()) {
			throw new InvalidValueException(ErrorCode.INVALID_INPUT_VALUE, "파일 이름이 유효하지 않음");
		}
		return StringUtils.cleanPath(originalFilename);
	}

	private void validateFileSize(long fileSize) {
		if (fileSize == 0) {
			throw new FileProcessingException(ErrorCode.FILE_SIZE_ZERO);
		}
	}

	private String hashFile(byte[] fileData) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hashedBytes = md.digest(fileData);
			return bytesToHex(hashedBytes);
		} catch (NoSuchAlgorithmException e) {
			throw new FileProcessingException(ErrorCode.FILE_HASHING_FAILED);
		}
	}

	private String bytesToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder(2 * bytes.length);
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}
}
