package kr.binarybard.hireo.common;

public interface BaseMapper<D, E> {
	D toDto(E e);
	E toEntity(D d);
}
