package kr.binarybard.hireo.api.file.controller;

import kr.binarybard.hireo.api.file.dto.FileResponse;
import kr.binarybard.hireo.api.file.service.FileService;
import kr.binarybard.hireo.common.AcceptanceTest;
import kr.binarybard.hireo.web.fixture.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = MemberFixture.TEST_EMAIL)
class FileApiControllerTest extends AcceptanceTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FileService fileService;

	@Test
	@DisplayName("일반 파일 업로드 요청이 성공적으로 처리되어야 한다.")
	void testUploadFile() throws Exception {
		// given
		MockMultipartFile file = new MockMultipartFile("file", "filename.txt",
			"text/plain", "some content".getBytes());
		FileResponse mockResponse = FileResponse.builder()
			.fileName("filename.txt")
			.fileSize(file.getSize())
			.contentType(file.getContentType())
			.uploadTimestamp(LocalDateTime.now())
			.build();

		when(fileService.store(any(MultipartFile.class))).thenReturn(mockResponse);

		// expected
		mockMvc.perform(multipart("/api/files/upload")
				.file(file))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.fileName", is("filename.txt")));
	}

	@Test
	@DisplayName("해시 파일명으로 파일 업로드 요청이 성공적으로 처리되어야 한다.")
	void testUploadFileAsHash() throws Exception {
		// given
		MockMultipartFile file = new MockMultipartFile("file", "filename.txt",
			"text/plain", "some content".getBytes());
		FileResponse mockResponse = FileResponse.builder()
			.fileName("hashedfilename.txt")
			.fileSize(file.getSize())
			.contentType(file.getContentType())
			.uploadTimestamp(LocalDateTime.now())
			.build();

		when(fileService.storeAsHash(any(MultipartFile.class))).thenReturn(mockResponse);

		// expected
		mockMvc.perform(multipart("/api/files/upload/hash")
				.file(file))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.fileName", is("hashedfilename.txt")));
	}

	@Test
	@DisplayName("주어진 파일명으로 파일 로드 요청이 성공적으로 처리되어야 한다.")
	void testLoadFile() throws Exception {
		// given
		String fileName = "testfile.txt";
		Resource resource = new ByteArrayResource("testfile content".getBytes());

		when(fileService.load(anyString())).thenReturn(resource);

		// expected
		mockMvc.perform(get("/api/files/load/" + fileName))
			.andExpect(status().isOk());
	}
}
