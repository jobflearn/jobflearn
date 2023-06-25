package kr.binarybard.hireo.api.file.service;

import kr.binarybard.hireo.api.file.dto.FileResponse;
import kr.binarybard.hireo.common.exceptions.FileProcessingException;
import kr.binarybard.hireo.common.exceptions.InvalidValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {

	private FileService fileService;

	@Mock
	private MultipartFile file;

	@TempDir
	Path tempDir;

	@BeforeEach
	void setUp() {
		fileService = new FileService(tempDir.toString());
	}

	@Test
	@DisplayName("파일 업로드는 정상적으로 처리되어야 한다.")
	void testStoreFile() throws IOException {
		// given
		String originalFilename = "testFile.txt";
		String contentType = "text/plain";
		long fileSize = 20L;

		when(file.getOriginalFilename()).thenReturn(originalFilename);
		when(file.getContentType()).thenReturn(contentType);
		when(file.getSize()).thenReturn(fileSize);
		when(file.getInputStream()).thenReturn(new ByteArrayInputStream("test content".getBytes()));

		// when
		FileResponse response = fileService.store(file);

		// then
		assertThat(response.getFileName()).isEqualTo(originalFilename);
		assertThat(response.getFileSize()).isEqualTo(fileSize);
		assertThat(response.getContentType()).isEqualTo(contentType);
	}

	@Test
	@DisplayName("파일 해시명으로 업로드는 정상적으로 처리되어야 한다.")
	void testStoreFileAsHash() throws IOException {
		// given
		String originalFilename = "testFile.txt";
		String contentType = "text/plain";
		long fileSize = 20L;

		when(file.getOriginalFilename()).thenReturn(originalFilename);
		when(file.getContentType()).thenReturn(contentType);
		when(file.getSize()).thenReturn(fileSize);
		when(file.getBytes()).thenReturn("test content".getBytes());
		when(file.getInputStream()).thenReturn(new ByteArrayInputStream("test content".getBytes()));

		// when
		FileResponse response = fileService.storeAsHash(file);

		// then
		assertThat(response.getFileName()).isNotEqualTo(originalFilename);
		assertThat(response.getFileSize()).isEqualTo(fileSize);
		assertThat(response.getContentType()).isEqualTo(contentType);
	}

	@Test
	@DisplayName("파일 로드는 정상적으로 처리되어야 한다.")
	void testLoadFile() throws IOException {
		// given
		String fileName = "testfile.txt";

		Files.createFile(tempDir.resolve(fileName));

		// when
		Resource resource = fileService.load(fileName);

		// then
		assertThat(resource).isNotNull();
	}

	@Test
	@DisplayName("파일 이름이 유효하지 않을 때 예외를 발생시켜야 한다.")
	void testStoreFileInvalidName() {
		// given
		when(file.getOriginalFilename()).thenReturn(null);

		// expected
		assertThatThrownBy(() -> fileService.store(file))
			.isInstanceOf(InvalidValueException.class);
	}

	@Test
	@DisplayName("파일이 없을 때 로드 시도하면 예외가 발생해야 한다.")
	void testLoadFileNotFound() {
		// expected
		String nonExistingFileName = "nonExistingFile.txt";

		assertThatThrownBy(() -> fileService.load(nonExistingFileName))
			.isInstanceOf(FileProcessingException.class);
	}

	@Test
	@DisplayName("파일 크기가 0인 경우 예외가 발생해야 한다.")
	void testStoreFileWithZeroSize() {
		// given
		String originalFilename = "emptyFile.txt";

		when(file.getOriginalFilename()).thenReturn(originalFilename);
		when(file.getSize()).thenReturn(0L);

		// expected
		assertThatThrownBy(() -> fileService.store(file))
			.isInstanceOf(FileProcessingException.class);
	}
}

