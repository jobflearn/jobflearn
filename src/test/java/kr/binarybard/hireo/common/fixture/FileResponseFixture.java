package kr.binarybard.hireo.common.fixture;

import java.time.LocalDateTime;

import kr.binarybard.hireo.api.file.dto.FileResponse;

public class FileResponseFixture {
	private static final String TEST_FILE_NAME = "6105d6cc76af400325e94d588ce511be5bfdbb73b437dc51eca43917d7a43e3d.png";
	private static final long TEST_FILE_SIZE = 64L;
	private static final String TEST_CONTENT_TYPE = "application/x-www-form-urlencoded";
	private static final LocalDateTime TEST_UPLOAD_TIMESTAMP = LocalDateTime.now();

	public static FileResponse createFileResponse() {
		return FileResponse.builder()
			.fileName(TEST_FILE_NAME)
			.fileSize(TEST_FILE_SIZE)
			.contentType(TEST_CONTENT_TYPE)
			.uploadTimestamp(TEST_UPLOAD_TIMESTAMP)
			.build();
	}

}
