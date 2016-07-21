package es.udc.rs.app.model.domain;


import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "Person")
public class Person {
	
	private Long id;
	private String name;
	private String surname1;
	private String surname2;
	private String nif;
	private String email;
	private Date hiredate;
	private String comment;

	// Primary key: id.
	// Not null attribute: name, surname1, surname2, nif, email, hiredate.
	// Unique attribute: nif, email.
	// Business key: nif. 
	
	public Person() {
	}
	
	public Person(Long id, String name, String surname1, String surname2, String nif,
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
	
	public Person(String name, String surname1, String surname2, String nif,
			String email, Date hiredate, String comment) {
		this.name = name;
		this.surname1 = surname1;
		this.surname2 = surname2;
		this.nif = nif;
		this.email = email;
		this.hiredate = hiredate;
		this.comment = comment;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idPerson")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "namePerson", nullable = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "surname1Person", nullable = true)
	public String getSurname1() {
		return surname1;
	}

	public void setSurname1(String surname1) {
		this.surname1 = surname1;
	}

	@Column(name = "surname2Person", nullable = true)
	public String getSurname2() {
		return surname2;
	}

	public void setSurname2(String surname2) {
		this.surname2 = surname2;
	}
	
	@Column(name = "nifPerson", nullable = true, unique = true)
	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	@Column(name = "emailPerson", nullable = true, unique = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "hiredatePerson", nullable = true)
	@Type(type = "date")
	public Date getHiredate() {
		return hiredate;
	}

	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}

	@Column(name = "commentPerson")
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
		Person other = (Person) obj;
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
