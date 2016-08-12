package es.udc.rs.app.model.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Blacklist")
public class Blacklist {
	
	private Long id;
	private String name;
	private Integer weekDay;
	private Date yearDay;
	private Integer yearMonth;

	// Primary key: id.
	// Not null attribute: all.
	// Unique attribute: name.
	// Business key: name, province, type, category, size.

}
