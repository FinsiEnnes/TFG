package es.udc.rs.app.client.dto;

import java.util.Date;

public class PersonDTO {

	private Long id;
	private String name;
	private String surname1;
	private String surname2;
	private String nif;
	private String email;
	private Date hiredate;
	private String comment;
	
	public PersonDTO() {
	}
	
	public PersonDTO(Long id, String name, String surname1, String surname2, String nif,
			String email, Date hiredate, String comment) {
		this.id = id;
		this.name = name;
		this.surname1 = surname1;
		this.surname2 = surname2;
		this.nif = nif;
		this.email = email;
		this.hiredate = hiredate;
		this.comment = comment;
	}
	
	public PersonDTO(String name, String surname1, String surname2, String nif,
			String email, Date hiredate, String comment) {
		this.name = name;
		this.surname1 = surname1;
		this.surname2 = surname2;
		this.nif = nif;
		this.email = email;
		this.hiredate = hiredate;
		this.comment = comment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname1() {
		return surname1;
	}

	public void setSurname1(String surname1) {
		this.surname1 = surname1;
	}

	public String getSurname2() {
		return surname2;
	}

	public void setSurname2(String surname2) {
		this.surname2 = surname2;
	}
	
	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getHiredate() {
		return hiredate;
	}

	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((hiredate == null) ? 0 : hiredate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((nif == null) ? 0 : nif.hashCode());
		result = prime * result
				+ ((surname1 == null) ? 0 : surname1.hashCode());
		result = prime * result
				+ ((surname2 == null) ? 0 : surname2.hashCode());
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
		PersonDTO other = (PersonDTO) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (hiredate == null) {
			if (other.hiredate != null)
				return false;
		} else if (!hiredate.equals(other.hiredate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nif == null) {
			if (other.nif != null)
				return false;
		} else if (!nif.equals(other.nif))
			return false;
		if (surname1 == null) {
			if (other.surname1 != null)
				return false;
		} else if (!surname1.equals(other.surname1))
			return false;
		if (surname2 == null) {
			if (other.surname2 != null)
				return false;
		} else if (!surname2.equals(other.surname2))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", surname1=" + surname1
				+ ", surname2=" + surname2 + ", email=" + email + ", hiredate="
				+ hiredate + ", comment=" + comment + "]";
	}
}
