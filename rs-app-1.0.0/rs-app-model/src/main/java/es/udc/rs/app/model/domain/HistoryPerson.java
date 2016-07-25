package es.udc.rs.app.model.domain;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "HistoryPerson")
public class HistoryPerson {
	
	private Long id;
	private Person person;
	private ProfessionalCategory profcatg;
	private Date ini;
	private Date end;
	private Integer sal;
	private Integer salExtra;
	private String comment;
	
	// Primary key: id.
	// Not null attribute: person, profcatg, ini, sal, salExtra, comment.
	// Business key: All except id. 
	
	public HistoryPerson() {
		
	}
	
	public HistoryPerson(Long id, Person person, ProfessionalCategory profcatg, 
			Date ini, Date end, Integer sal, Integer salExtra, String comment) {
		this.id = id;
		this.person = person;
		this.profcatg = profcatg;
		this.ini = ini;
		this.end = end;
		this.sal = sal;
		this.salExtra = salExtra;
	}
	
	public HistoryPerson(Person person, ProfessionalCategory profcatg, 
			Date ini, Date end, Integer sal, Integer salExtra, String comment) {
		this.person = person;
		this.profcatg = profcatg;
		this.ini = ini;
		this.end = end;
		this.sal = sal;
		this.salExtra = salExtra;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idHPerson")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "idPerson", foreignKey = @ForeignKey(name = "fk_historyperson_idperson"))
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@ManyToOne
	@JoinColumn(name = "idProfCatg", foreignKey = @ForeignKey(name = "fk_historyperson_idprofcatg"))
	public ProfessionalCategory getProfcatg() {
		return profcatg;
	}

	public void setProfcatg(ProfessionalCategory profcatg) {
		this.profcatg = profcatg;
	}

	@Column(name = "iniHPerson", nullable = true)
	@Type(type = "date")
	public Date getIni() {
		return ini;
	}

	public void setIni(Date ini) {
		this.ini = ini;
	}

	@Column(name = "endHPerson")
	@Type(type = "date")
	public Date getEnd() {
		return end;
	}
	
	public void setEnd(Date end) {
		this.end = end;
	}

	@Column(name = "salHPerson", nullable = true)
	public Integer getSal() {
		return sal;
	}

	public void setSal(Integer sal) {
		this.sal = sal;
	}

	@Column(name = "salExtraHPerson", nullable = true)
	public Integer getSalExtra() {
		return salExtra;
	}

	public void setSalExtra(Integer salExtra) {
		this.salExtra = salExtra;
	}

	@Column(name = "commentHPerson", nullable = true)
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
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((ini == null) ? 0 : ini.hashCode());
		result = prime * result + ((person == null) ? 0 : person.getId().hashCode());
		result = prime * result
				+ ((profcatg == null) ? 0 : profcatg.getId().hashCode());
		result = prime * result + ((sal == null) ? 0 : sal.hashCode());
		result = prime * result
				+ ((salExtra == null) ? 0 : salExtra.hashCode());
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
		HistoryPerson other = (HistoryPerson) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
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
		if (profcatg == null) {
			if (other.profcatg != null)
				return false;
		} else if (!profcatg.equals(other.profcatg))
			return false;
		if (sal == null) {
			if (other.sal != null)
				return false;
		} else if (!sal.equals(other.sal))
			return false;
		if (salExtra == null) {
			if (other.salExtra != null)
				return false;
		} else if (!salExtra.equals(other.salExtra))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HistoryPerson [id=" + id + ", idPerson=" + person.getId() + ", idProfcatg="
				+ profcatg.getId() + ", ini=" + ini + ", end=" + end + ", sal=" + sal
				+ ", salExtra=" + salExtra + ", comment=" + comment + "]";
	}
}
