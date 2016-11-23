package es.udc.rs.app.client.dto;


public class HistoryPersonDTO {

	private Long id;
	private Long idPerson;
	private String namePerson;
	private Long idProfCatg;
	private String nameProfCatg;
	private String levelProfCatg;
	private String ini;
	private String end;
	private Integer sal;
	private Integer salExtra;
	private String comment;
	
		
	public HistoryPersonDTO() {
		
	}

	public HistoryPersonDTO(Long id, Long idPerson, String namePerson,
			Long idProfCatg, String nameProfCatg, String levelProfCatg, String ini, String end, Integer sal,
			Integer salExtra, String comment) {
		this.id = id;
		this.idPerson = idPerson;
		this.namePerson = namePerson;
		this.idProfCatg = idProfCatg;
		this.nameProfCatg = nameProfCatg;
		this.levelProfCatg = levelProfCatg;
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
	
	public Long getIdProfCatg() {
		return idProfCatg;
	}
	
	public void setIdProfCatg(Long idProfCatg) {
		this.idProfCatg = idProfCatg;
	}
	
	public String getNameProfCatg() {
		return nameProfCatg;
	}

	public void setNameProfCatg(String nameProfCatg) {
		this.nameProfCatg = nameProfCatg;
	}

	public String getLevelProfCatg() {
		return levelProfCatg;
	}

	public void setLevelProfCatg(String levelProfCatg) {
		this.levelProfCatg = levelProfCatg;
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
		result = prime * result
				+ ((idPerson == null) ? 0 : idPerson.hashCode());
		result = prime * result
				+ ((idProfCatg == null) ? 0 : idProfCatg.hashCode());
		result = prime * result + ((ini == null) ? 0 : ini.hashCode());
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
		if (idPerson == null) {
			if (other.idPerson != null)
				return false;
		} else if (!idPerson.equals(other.idPerson))
			return false;
		if (idProfCatg == null) {
			if (other.idProfCatg != null)
				return false;
		} else if (!idProfCatg.equals(other.idProfCatg))
			return false;
		if (ini == null) {
			if (other.ini != null)
				return false;
		} else if (!ini.equals(other.ini))
			return false;
		return true;
	}
	
}
