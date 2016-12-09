package es.udc.rs.app.client.dto;


public class TaskIncidentDTO {

	private Long id;
	private Long idIncident;
	private Long idTask;
	private String damage;
	private String reason;
	private String result;
	private Integer loss;
	
	
	public TaskIncidentDTO() {
	}


	public TaskIncidentDTO(Long id, Long idIncident, Long idTask,
			String damage, String reason, String result, Integer loss) {
		this.id = id;
		this.idIncident = idIncident;
		this.idTask = idTask;
		this.damage = damage;
		this.reason = reason;
		this.result = result;
		this.loss = loss;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getIdIncident() {
		return idIncident;
	}


	public void setIdIncident(Long idIncident) {
		this.idIncident = idIncident;
	}


	public Long getIdTask() {
		return idTask;
	}


	public void setIdTask(Long idTask) {
		this.idTask = idTask;
	}


	public String getDamage() {
		return damage;
	}


	public void setDamage(String damage) {
		this.damage = damage;
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public Integer getLoss() {
		return loss;
	}


	public void setLoss(Integer loss) {
		this.loss = loss;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idIncident == null) ? 0 : idIncident.hashCode());
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
		TaskIncidentDTO other = (TaskIncidentDTO) obj;
		if (idIncident == null) {
			if (other.idIncident != null)
				return false;
		} else if (!idIncident.equals(other.idIncident))
			return false;
		if (idTask == null) {
			if (other.idTask != null)
				return false;
		} else if (!idTask.equals(other.idTask))
			return false;
		return true;
	}
	
}
