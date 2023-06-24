package kr.binarybard.hireo.api.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.binarybard.hireo.api.file.dto.FileResponse;
import kr.binarybard.hireo.api.file.service.FileService;
import kr.binarybard.hireo.web.fixture.MemberFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = MemberFixture.TEST_EMAIL)
class FileApiControllerTest extends RestDocsConfiguration {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private FileService fileService;

	@Mock
	private MultipartFile file;

	@BeforeEach
	void setup() {
		LocalDateTime now = LocalDateTime.now();
		Resource mockResource = mock(Resource.class);

		when(fileService.store(any())).thenReturn(new FileResponse("testFile.jpg", 12345, "image/jpeg", now));
		when(fileService.storeAsHash(any())).thenReturn(new FileResponse("hashedTestFile.jpg", 12345, "image/jpeg", now));
		when(fileService.load(anyString())).thenReturn(mockResource);
		when(mockResource.getFilename()).thenReturn("testFile.jpg");
	}

	@Test
	@DisplayName("파일 업로드 성공")
	void uploadFileSuccess() throws Exception {
		// Act & Assert
		mockMvc.perform(multipart("/api/files/upload")
				.file("file", "testFileContent".getBytes())
				.contentType(MediaType.MULTIPART_FORM_DATA))
			.andExpect(status().isOk())
			.andDo(document("upload-file",
				requestParts(
					partWithName("file").description("업로드 파일")
				),
				responseFields(
					fieldWithPath("fileName").description("파일 이름"),
					fieldWithPath("fileSize").description("파일 크기"),
					fieldWithPath("contentType").description("파일 유형"),
					fieldWithPath("uploadTimestamp").description("파일 업로드 시간")
				)
			));
	}

	@Test
	@DisplayName("해시를 이용한 파일 업로드 성공")
	void uploadFileAsHashSuccess() throws Exception {
		// Act & Assert
		mockMvc.perform(multipart("/api/files/upload/hash")
				.file("file", "testFileContent".getBytes())
				.contentType(MediaType.MULTIPART_FORM_DATA))
			.andExpect(status().isOk())
			.andDo(document("upload-file-hash",
				requestParts(
					partWithName("file").description("업로드 파일")
				),
				responseFields(
					fieldWithPath("fileName").description("파일 이름"),
					fieldWithPath("fileSize").description("파일 크기"),
					fieldWithPath("contentType").description("파일 유형"),
					fieldWithPath("uploadTimestamp").description("파일 업로드 시간")
				)
			));
	}

	@Test
	@DisplayName("파일 로드 성공")
	void loadFileSuccess() throws Exception {
		// Act & Assert
		mockMvc.perform(get("/api/files/load/testFile.jpg")
				.accept(MediaType.APPLICATION_OCTET_STREAM))
			.andExpect(status().isOk())
			.andDo(document("load-file",
				responseHeaders(
					headerWithName("Content-Disposition").description("파일 이름 헤더")
				)
			));
	}
}
