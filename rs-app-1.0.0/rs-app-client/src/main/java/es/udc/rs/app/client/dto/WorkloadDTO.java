package es.udc.rs.app.client.dto;

public class WorkloadDTO {
	
	private Long id;
	private Long idTask;
	private Long idHPerson;
	private String personName; 
	private String dayDate;
	private Integer hours;
	private Integer extraHours;
	
	
	
	public WorkloadDTO() {
	}

	public WorkloadDTO(Long id, Long idTask, Long idHPerson, String personName,
			String dayDate, Integer hours, Integer extraHours) {
		this.id = id;
		this.idTask = idTask;
		this.idHPerson = idHPerson;
		this.personName = personName;
		this.dayDate = dayDate;
		this.hours = hours;
		this.extraHours = extraHours;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdTask() {
		return idTask;
	}

	public void setIdTask(Long idTask) {
		this.idTask = idTask;
	}

	public Long getIdHPerson() {
		return idHPerson;
	}

	public void setIdHPerson(Long idHPerson) {
		this.idHPerson = idHPerson;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getDayDate() {
		return dayDate;
	}

	public void setDayDate(String dayDate) {
		this.dayDate = dayDate;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public Integer getExtraHours() {
		return extraHours;
	}

	public void setExtraHours(Integer extraHours) {
		this.extraHours = extraHours;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dayDate == null) ? 0 : dayDate.hashCode());
		result = prime * result
				+ ((idHPerson == null) ? 0 : idHPerson.hashCode());
		result = prime * result + ((idTask == null) ? 0 : idTask.hashCode());
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
		WorkloadDTO other = (WorkloadDTO) obj;
		if (dayDate == null) {
			if (other.dayDate != null)
				return false;
		} else if (!dayDate.equals(other.dayDate))
			return false;
		if (idHPerson == null) {
			if (other.idHPerson != null)
				return false;
		} else if (!idHPerson.equals(other.idHPerson))
			return false;
		if (idTask == null) {
			if (other.idTask != null)
				return false;
		} else if (!idTask.equals(other.idTask))
			return false;
		return true;
	}


}
