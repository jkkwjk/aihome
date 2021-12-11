package com.jkk.aihome.entity.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class AutoDTO {
	private Integer id;

	private String cron;

	private List<String> events;

	private String code;

	public AutoDTO(Integer id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AutoDTO autoDTO = (AutoDTO) o;
		return id.equals(autoDTO.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
