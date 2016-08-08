package es.udc.rs.app.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "TaskIncident", 
	   uniqueConstraints = { @UniqueConstraint(columnNames = { "idTask", "idIncident" }) })
public class TaskIncident {
	
	private Long id;
	private Task task;
	private Incident incident;
	private Integer loss;
	
	// Primary key: id.
	// Not null attribute: task, incident, loss.
	// Unique attribute: (incident, loss).
	// Business key: incident, loss.
	
	public TaskIncident() {
		
	}
	
	public TaskIncident(Long id, Task task, Incident incident, Integer loss) {
		this.id = id;
		this.task = task;
		this.incident = incident;
		this.loss = loss;
	}
	
	public TaskIncident(Task task, Incident incident, Integer loss) {
		this.task = task;
		this.incident = incident;
		this.loss = loss;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idTaskIncident")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "idTask", foreignKey = @ForeignKey(name = "fk_taskincident_task"))
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@ManyToOne
	@JoinColumn(name = "idIncident", foreignKey = @ForeignKey(name = "fk_taskincident_incident"))
	public Incident getIncident() {
		return incident;
	}

	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	@Column(name = "lossIncident", nullable = true)
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
				+ ((incident == null) ? 0 : incident.getId().hashCode());
		result = prime * result + ((task == null) ? 0 : task.getId().hashCode());
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
		TaskIncident other = (TaskIncident) obj;
		if (incident == null) {
			if (other.incident != null)
				return false;
		} else if (!incident.equals(other.incident))
			return false;
		if (task == null) {
			if (other.task != null)
				return false;
		} else if (!task.equals(other.task))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TaskIncident [id=" + id + ", idTask=" + task.getId() + ", idIncident="
				+ incident.getId() + ", loss=" + loss + "]";
	}

}
