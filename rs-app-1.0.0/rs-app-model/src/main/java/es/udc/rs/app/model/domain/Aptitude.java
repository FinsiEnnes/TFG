package es.udc.rs.app.model.domain;

import javax.persistence.*;

@Entity
@Table(name = "Aptitude", uniqueConstraints = { @UniqueConstraint(columnNames = { "idPerson", "nameApt" }) })
public class Aptitude {
	
	private Long id;
	private Person person;
	private String name;
	private AptitudeType type;
	private Integer value;
	private String comment;
	
	// Primary key: id.
	// Not null attribute: person, name, type.
	// Unique attribute: (person, name).
	// Business key: person, name.
	
	public Aptitude() {
		
	}
	
	public Aptitude(Long id, Person person, String name, AptitudeType type, Integer value, String comment) {
		this.id = id;
		this.person = person;
		this.name = name;
		this.type = type;
		this.value = value;
		this.comment = comment;
	}
	
	public Aptitude(Person person, String name, AptitudeType type, Integer value, String comment) {
		this.person = person;
		this.name = name;
		this.type = type;
		this.value = value;
		this.comment = comment;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idApt")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "idPerson", foreignKey = @ForeignKey(name = "fk_aptitude_idperson"))
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Column(name = "nameApt", nullable = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne
	@JoinColumn(name = "idAptType", foreignKey = @ForeignKey(name = "fk_aptitude_idAptType"))
	public AptitudeType getType() {
		return type;
	}

	public void setType(AptitudeType type) {
		this.type = type;
	}

	@Column(name = "valueApt")
	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Column(name = "commentApt")
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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Aptitude other = (Aptitude) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
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
		return "Aptitude [id=" + id + ", idPerson=" + person.getId() + ", name=" + name
				+ ", type=" + type + ", value=" + value + ", comment="
				+ comment + "]";
	}	

}
