package es.udc.rs.app.client.dto;

public class AptitudeDTO {

	private Long id;
	private Long idPerson;
	private String name;
	private String type;
	private Integer value;
	private String comment;

	public AptitudeDTO() {

	}

	public AptitudeDTO(Long id, Long idPerson, String name, String type, Integer value, String comment) {
		this.id = id;
		this.idPerson = idPerson;
		this.name = name;
		this.type = type;
		this.value = value;
		this.comment = comment;
	}

	public AptitudeDTO(Long idPerson, String name, String type, Integer value, String comment) {
		this.idPerson = idPerson;
		this.name = name;
		this.type = type;
		this.value = value;
		this.comment = comment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(Long idPerson) {
		this.idPerson = idPerson;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
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
		result = prime * result
				+ ((idPerson == null) ? 0 : idPerson.hashCode());
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
		AptitudeDTO other = (AptitudeDTO) obj;
		if (idPerson == null) {
			if (other.idPerson != null)
				return false;
		} else if (!idPerson.equals(other.idPerson))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Aptitude [id=" + id + ", idPerson=" + idPerson + ", name=" + name
				+ ", type=" + type + ", value=" + value + ", comment="
				+ comment + "]";
	}	

}
