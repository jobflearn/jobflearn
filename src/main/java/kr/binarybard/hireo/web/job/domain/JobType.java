package kr.binarybard.hireo.web.job.domain;

import lombok.Getter;

@Getter
public enum JobType {
	FULLTIME("정규직"),
	PARTTIME("계약직"),
	INTERNSHIP("인턴");

	private final String name;

	JobType(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
