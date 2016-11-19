package es.udc.rs.app.client.dto;


public class HistoryPersonDTO {

	private Long id;
	private Long idPerson;
	private String namePerson;
	private Long idProfcatg;
	private String ini;
	private String end;
	private Integer sal;
	private Integer salExtra;
	private String comment;
	
		
	public HistoryPersonDTO() {
		
	}

	public HistoryPersonDTO(Long id, Long idPerson, String namePerson,
			Long idProfcatg, String ini, String end, Integer sal,
			Integer salExtra, String comment) {
		this.id = id;
		this.idPerson = idPerson;
		this.namePerson = namePerson;
		this.idProfcatg = idProfcatg;
		this.ini = ini;
		this.end = end;
		this.sal = sal;
		this.salExtra = salExtra;
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
	
	public String getNamePerson() {
		return namePerson;
	}
	public void setNamePerson(String namePerson) {
		this.namePerson = namePerson;
	}
	
	public Long getIdProfcatg() {
		return idProfcatg;
	}
	public void setIdProfcatg(Long idProfcatg) {
		this.idProfcatg = idProfcatg;
	}
	
	public String getIni() {
		return ini;
	}
	public void setIni(String ini) {
		this.ini = ini;
	}
	
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	
	public Integer getSal() {
		return sal;
	}
	public void setSal(Integer sal) {
		this.sal = sal;
	}
	
	public Integer getSalExtra() {
		return salExtra;
	}
	public void setSalExtra(Integer salExtra) {
		this.salExtra = salExtra;
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
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result
				+ ((idPerson == null) ? 0 : idPerson.hashCode());
		result = prime * result
				+ ((idProfcatg == null) ? 0 : idProfcatg.hashCode());
		result = prime * result + ((ini == null) ? 0 : ini.hashCode());
		result = prime * result
				+ ((namePerson == null) ? 0 : namePerson.hashCode());
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
		HistoryPersonDTO other = (HistoryPersonDTO) obj;
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
		if (idPerson == null) {
			if (other.idPerson != null)
				return false;
		} else if (!idPerson.equals(other.idPerson))
			return false;
		if (idProfcatg == null) {
			if (other.idProfcatg != null)
				return false;
		} else if (!idProfcatg.equals(other.idProfcatg))
			return false;
		if (ini == null) {
			if (other.ini != null)
				return false;
		} else if (!ini.equals(other.ini))
			return false;
		if (namePerson == null) {
			if (other.namePerson != null)
				return false;
		} else if (!namePerson.equals(other.namePerson))
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
	
}
