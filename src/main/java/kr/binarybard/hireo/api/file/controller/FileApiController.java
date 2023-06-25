package kr.binarybard.hireo.api.file.controller;

import kr.binarybard.hireo.api.file.dto.FileResponse;
import kr.binarybard.hireo.api.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class FileApiController {

	private final FileService fileService;

	@PostMapping({"/api/files/upload", "/files/upload"})
	public ResponseEntity<FileResponse> uploadFile(MultipartFile file) {
		return ResponseEntity.ok().body(fileService.store(file));
	}

	@PostMapping({"/api/files/upload/hash", "/files/upload/hash"})
	public ResponseEntity<FileResponse> uploadFileAsHash(MultipartFile file) {
		return ResponseEntity.ok().body(fileService.storeAsHash(file));
	}

	@GetMapping({"/api/files/load/{fileName}", "/attachments/{fileName}"})
	public ResponseEntity<Resource> loadFile(@PathVariable String fileName) {
		var resource = fileService.load(fileName);
		return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
			.body(resource);
	}
}
