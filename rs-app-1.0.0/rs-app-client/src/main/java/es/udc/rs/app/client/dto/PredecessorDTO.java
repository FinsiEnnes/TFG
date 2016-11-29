package es.udc.rs.app.client.dto;


public class PredecessorDTO {
	
	private Long id;
	private Long task;
	private Long taskPred;
	private String taskPredName;
	private String linkType;
	
	public PredecessorDTO() {
	}
	
	public PredecessorDTO(Long id, Long task, Long taskPred, String taskPredName, String linkType) {
		this.id = id;
		this.task = task;
		this.taskPred = taskPred;
		this.taskPredName = taskPredName;
		this.linkType = linkType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTask() {
		return task;
	}

	public void setTask(Long task) {
		this.task = task;
	}

	public Long getTaskPred() {
		return taskPred;
	}

	public void setTaskPred(Long taskPred) {
		this.taskPred = taskPred;
	}

	public String getTaskPredName() {
		return taskPredName;
	}

	public void setTaskPredName(String taskPredName) {
		this.taskPredName = taskPredName;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((task == null) ? 0 : task.hashCode());
		result = prime * result
				+ ((taskPred == null) ? 0 : taskPred.hashCode());
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
		PredecessorDTO other = (PredecessorDTO) obj;
		if (task == null) {
			if (other.task != null)
				return false;
		} else if (!task.equals(other.task))
			return false;
		if (taskPred == null) {
			if (other.taskPred != null)
				return false;
		} else if (!taskPred.equals(other.taskPred))
			return false;
		return true;
	}

}
