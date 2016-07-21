package es.udc.rs.app.model.domain;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "TimeOff", uniqueConstraints = { @UniqueConstraint(columnNames = { "idPerson", "iniTimeOff" }) })
public class TimeOff {
	
	private Long id;
	private Person person;
	private Date ini;
	private Date end;
	private String reason;
	
	// Primary key: id.
	// Not null attribute: person, ini.
	// Unique attribute: (person, ini).
	// Business key: idPerson, ini.

	public TimeOff() {
	}
	
	public TimeOff(Long id, Person person, Date ini, Date end, String reason) {
		super();
		this.person = person;
		this.ini = ini;
		this.end = end;
		this.reason = reason;
	}
	
	public TimeOff(Person person, Date ini, Date end, String reason) {
		super();
		this.person = person;
		this.ini = ini;
		this.end = end;
		this.reason = reason;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idTimeOff")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "idPerson", foreignKey = @ForeignKey(name = "fk_timeoff_idperson"))
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Column(name = "iniTimeOff", nullable = true)
	@Type(type = "date")
	public Date getIni() {
		return ini;
	}

	public void setIni(Date ini) {
		this.ini = ini;
	}

	@Column(name = "endTimeOff")
	@Type(type = "date")
	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	
	@Column(name = "reasonTimeOff")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ini == null) ? 0 : ini.hashCode());
		result = prime * result + ((person == null) ? 0 : person.getId().hashCode());
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
		TimeOff other = (TimeOff) obj;
		if (ini == null) {
			if (other.ini != null)
				return false;
		} else if (!ini.equals(other.ini))
			return false;
		if (person == null) {
			if (other.person != null)
				return false;
		} else if (!person.equals(other.person))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TimeOff [id=" + id + ", idPerson=" + person.getId() + ", ini=" + ini
				+ ", end=" + end + ", reason=" + reason + "]";
	}
		
}
