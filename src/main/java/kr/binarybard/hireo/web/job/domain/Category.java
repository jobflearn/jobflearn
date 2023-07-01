package kr.binarybard.hireo.web.job.domain;

import lombok.Getter;

@Getter
public enum Category {
	WEB_SOFT("웹 & 소프트웨어"),
	DATA_SCI("데이터 과학"),
	SALE_MAR("영업 & 마케팅"),
	GRAP_DES("그래픽 & 디자인"),
	EDU_TRAI("교육 & 훈련");

	private final String name;

	Category(String name) {
		this.name = name;
	}
}
