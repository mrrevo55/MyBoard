package com.revo.myboard.user.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.revo.myboard.user.Data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * Created By Revo
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class DataDTO {

	@NotEmpty
	private String description;
	@Min(1)
	@Max(99)
	private int age;
	@NotEmpty
	private String city;
	@NotEmpty
	private String page;
	@Size(min = 7, max = 9)
	private String sex;

	public static DataDTO mapFromData(Data data) {
		return DataDTO.builder().age(data.getAge()).city(data.getCity()).description(data.getDescription())
				.page(data.getPage()).sex(data.getSex().toString()).build();
	}

}
