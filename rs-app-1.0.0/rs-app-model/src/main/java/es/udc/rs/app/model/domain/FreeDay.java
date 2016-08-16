package es.udc.rs.app.model.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "FreeDay")
public class FreeDay {
	
	private Long id;
	private String name;
	private Integer weekDay;
	private Date ini;
	private Date end;

	// Primary key: id.
	// Not null attribute: name.
	// Unique attribute: name, weekDay.
	// Business key: name.
	
	public FreeDay() {
		
	}
	
	public FreeDay(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public FreeDay(String name, Integer weekDay, Date ini, Date end) {
		this.name = name;
		this.weekDay = weekDay;
		this.ini = ini;
		this.end = end;
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idFreeDay")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "nameFreeDay", nullable = true, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "weekDayFreeDay", unique = true)
	public Integer getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(Integer weekDay) {
		this.weekDay = weekDay;
	}

	@Column(name = "iniFreeDay")
	@Type(type = "date")
	public Date getIni() {
		return ini;
	}

	public void setIni(Date ini) {
		this.ini = ini;
	}

	@Column(name = "endFreeDay")
	@Type(type = "date")
	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FreeDay other = (FreeDay) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FreeDay [id=" + id + ", name=" + name + ", weekDay=" + weekDay
				+ ", ini=" + ini + ", end=" + end + "]";
	}

}
