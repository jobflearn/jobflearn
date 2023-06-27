package kr.binarybard.hireo.api.docs;

import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriHost = "jobflearn.binarybard.kr", uriPort = 443, uriScheme = "https")
public class RestDocsConfiguration {
}
