package kr.binarybard.hireo.api.file.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FileResponse {
	private String fileName;
	private long fileSize;
	private String contentType;
	private LocalDateTime uploadTimestamp;

	@Builder
	public FileResponse(String fileName, long fileSize, String contentType, LocalDateTime uploadTimestamp) {
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.contentType = contentType;
		this.uploadTimestamp = uploadTimestamp;
	}
}
