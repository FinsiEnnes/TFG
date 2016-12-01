package es.udc.rs.app.client.dto;


public class AssignmentPersonDTO {
	
	private Long id;
	private Long idTask;
	private Long idHistoryPerson;
	private boolean conclude;
	private Integer totalHours;
	private Integer totalExtraHours;
	private Integer totalCost;
	
	
	public AssignmentPersonDTO() {
	}
	
	public AssignmentPersonDTO(Long id, Long idTask, Long idHistoryPerson,
			boolean conclude, Integer totalHours, Integer totalExtraHours,
			Integer totalCost) {
		this.id = id;
		this.idTask = idTask;
		this.idHistoryPerson = idHistoryPerson;
		this.conclude = conclude;
		this.totalHours = totalHours;
		this.totalExtraHours = totalExtraHours;
		this.totalCost = totalCost;
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
	public Long getIdHistoryPerson() {
		return idHistoryPerson;
	}
	public void setIdHistoryPerson(Long idHistoryPerson) {
		this.idHistoryPerson = idHistoryPerson;
	}
	public boolean isConclude() {
		return conclude;
	}
	public void setConclude(boolean conclude) {
		this.conclude = conclude;
	}
	public Integer getTotalHours() {
		return totalHours;
	}
	public void setTotalHours(Integer totalHours) {
		this.totalHours = totalHours;
	}
	public Integer getTotalExtraHours() {
		return totalExtraHours;
	}
	public void setTotalExtraHours(Integer totalExtraHours) {
		this.totalExtraHours = totalExtraHours;
	}
	public Integer getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(Integer totalCost) {
		this.totalCost = totalCost;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idHistoryPerson == null) ? 0 : idHistoryPerson.hashCode());
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
		AssignmentPersonDTO other = (AssignmentPersonDTO) obj;
		if (idHistoryPerson == null) {
			if (other.idHistoryPerson != null)
				return false;
		} else if (!idHistoryPerson.equals(other.idHistoryPerson))
			return false;
		if (idTask == null) {
			if (other.idTask != null)
				return false;
		} else if (!idTask.equals(other.idTask))
			return false;
		return true;
	}

}
