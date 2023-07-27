package kr.binarybard.hireo.web.company.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.binarybard.hireo.api.file.dto.FileResponse;
import kr.binarybard.hireo.common.CurrentUser;
import kr.binarybard.hireo.web.company.dto.CompanyRegister;
import kr.binarybard.hireo.web.company.dto.CompanyResponse;
import kr.binarybard.hireo.web.company.service.CompanyService;
import kr.binarybard.hireo.web.review.dto.ReviewResponse;
import kr.binarybard.hireo.web.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {
	private final CompanyService companyService;
	private final ReviewService reviewService;

	@GetMapping("/{id:\\d+}")
	public ResponseEntity<Map<String, Object>> profile(
		@PathVariable("id") Long id,
		@RequestParam(defaultValue = "1") int page
	) {
		CompanyResponse foundCompany = companyService.findOne(id);
		Page<ReviewResponse> reviews = reviewService.getReviewsByCompanyId(id, page);
		Map<String, Object> companyProfile = new HashMap<>();
		companyProfile.put("foundCompany", foundCompany);
		companyProfile.put("reviews", reviews);
		return ResponseEntity.ok(companyProfile);
	}

	@PostMapping("/new")
	public ResponseEntity<Void> registerCompany(@CurrentUser User user,
		@RequestBody CompanyRegister companyRegister) {
		return ResponseEntity
			.created(
				URI.create("/companies/" + companyService.registerCompany(companyRegister, user))
			)
			.build();
	}

	@PutMapping("/{id:\\d+}")
	public ResponseEntity<FileResponse> registerCompanyLogo(@PathVariable Long id, MultipartFile logo) {
		return ResponseEntity.ok(companyService.uploadLogo(id, logo));
	}
}
