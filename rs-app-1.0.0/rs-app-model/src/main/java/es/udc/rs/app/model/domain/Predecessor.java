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
@Table(name = "Predecessor", 
	   uniqueConstraints = { @UniqueConstraint(columnNames = { "idTask", "idTaskPred" }) })
public class Predecessor {
	
	private Long id;
	private Task task;
	private Task taskPred;
	private TaskLinkType linkType;
	
	// Primary key: id.
	// Not null attribute: task, taskPred, linkType.
	// Unique attribute: (task, taskPred).
	// Business key: task, taskPred.
	
	public Predecessor() {
		
	}
	
	public Predecessor(Long id, Task task, Task taskPred, TaskLinkType linkType) {
		this.id = id;
		this.task = task;
		this.taskPred = taskPred;
		this.linkType = linkType;
	}
	
	public Predecessor(Task task, Task taskPred, TaskLinkType linkType) {
		this.task = task;
		this.taskPred = taskPred;
		this.linkType = linkType;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idPredecessor")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "idTask", foreignKey = @ForeignKey(name = "fk_predecessor_task"))
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@ManyToOne
	@JoinColumn(name = "idTaskPred", foreignKey = @ForeignKey(name = "fk_predecessor_taskPred"))
	public Task getTaskPred() {
		return taskPred;
	}

	public void setTaskPred(Task taskPred) {
		this.taskPred = taskPred;
	}

	@ManyToOne
	@JoinColumn(name = "idTaskLinkType", foreignKey = @ForeignKey(name = "fk_predecessor_linktype"))
	public TaskLinkType getLinkType() {
		return linkType;
	}

	public void setLinkType(TaskLinkType linkType) {
		this.linkType = linkType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((task == null) ? 0 : task.getId().hashCode());
		result = prime * result
				+ ((taskPred == null) ? 0 : taskPred.getId().hashCode());
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
		Predecessor other = (Predecessor) obj;
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

	@Override
	public String toString() {
		return "Predecessor [id=" + id + ", idTask=" + task.getId() + ", idTaskPred="
				+ taskPred.getId() + ", idTaskLinkType=" + linkType.getId() + "]";
	}

}
