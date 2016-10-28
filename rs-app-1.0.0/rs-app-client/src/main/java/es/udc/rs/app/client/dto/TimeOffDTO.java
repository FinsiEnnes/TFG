package es.udc.rs.app.client.dto;

import javax.persistence.Column;

public class TimeOffDTO {
	
	private Long id;
	private Long idPerson;
	private String ini;
	private String end;
	private String reason;

	public TimeOffDTO() {
	}
	
	public TimeOffDTO(Long id, Long idPerson, String ini, String end, String reason) {
		super();
		this.idPerson = idPerson;
		this.ini = ini;
		this.end = end;
		this.reason = reason;
	}
	
	public TimeOffDTO(Long idPerson, String ini, String end, String reason) {
		super();
		this.idPerson = idPerson;
		this.ini = ini;
		this.end = end;
		this.reason = reason;
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
		result = prime * result
				+ ((idPerson == null) ? 0 : idPerson.hashCode());
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
		TimeOffDTO other = (TimeOffDTO) obj;
		if (idPerson == null) {
			if (other.idPerson != null)
				return false;
		} else if (!idPerson.equals(other.idPerson))
			return false;
		if (ini == null) {
			if (other.ini != null)
				return false;
		} else if (!ini.equals(other.ini))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TimeOff [id=" + id + ", idPerson=" + idPerson + ", ini=" + ini
				+ ", end=" + end + ", reason=" + reason + "]";
	}

}
