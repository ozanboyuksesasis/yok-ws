package com.sesasis.donusum.yok.dto;

import lombok.Data;

@Data
public class DashboardMenuDTO {

	private String name;
	private Long parentId;
	private int number;
	private String path;
	private String iconName;

}
