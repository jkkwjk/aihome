package com.jkk.aihome.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.GenericGenerators;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_app")
@Entity
@Data
public class App {
	@Id
	@GenericGenerator(name = , strategy = )
	private Integer id;

	private Integer appId;
}
