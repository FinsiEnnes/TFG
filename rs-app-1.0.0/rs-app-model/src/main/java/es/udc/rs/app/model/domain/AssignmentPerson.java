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
@Table(name = "AssignmentPerson", 
	   uniqueConstraints = { @UniqueConstraint(columnNames = { "idTask", "idHPerson" }) })
public class AssignmentPerson {

	private Long id;
	private Task task;
	private HistoryPerson historyPerson;
	private boolean conclude;
	private Integer totalHours;
	private Integer totalExtraHours;
	private Integer totalCost;
	
	// Primary key: id.
	// Not null attribute: task, historyPerson, conclude.
	// Unique attribute: (task, historyPerson).
	// Business key: task, historyPerson.
	
	public AssignmentPerson() {
		
	}
	
	public AssignmentPerson(Long id, Task task, HistoryPerson historyPerson, boolean conclude, 
							Integer totalHours, Integer totalExtraHours, Integer totalCost) {
		this.id = id;
		this.task = task;
		this.historyPerson = historyPerson;
		this.conclude = conclude;
		this.totalHours = totalHours;
		this.totalExtraHours = totalExtraHours;
		this.totalCost = totalCost;
	}
	
	public AssignmentPerson(Task task, HistoryPerson historyPerson, boolean conclude, 
							Integer totalHours, Integer totalExtraHours, Integer totalCost) {
		this.task = task;
		this.historyPerson = historyPerson;
		this.conclude = conclude;
		this.totalHours = totalHours;
		this.totalExtraHours = totalExtraHours;
		this.totalCost = totalCost;
	}
	
	public AssignmentPerson(Task task, HistoryPerson historyPerson, boolean conclude) {
		this.task = task;
		this.historyPerson = historyPerson;
		this.conclude = conclude;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idAssigPerson")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "idTask", foreignKey = @ForeignKey(name = "fk_assigperson_task"))
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@ManyToOne
	@JoinColumn(name = "idHPerson", foreignKey = @ForeignKey(name = "fk_assigperson_person"))
	public HistoryPerson getHistoryPerson() {
		return historyPerson;
	}

	public void setHistoryPerson(HistoryPerson historyPerson) {
		this.historyPerson = historyPerson;
	}

	@Column(name = "concludeAssigPerson", nullable = true)
	public boolean isConclude() {
		return conclude;
	}

	public void setConclude(boolean conclude) {
		this.conclude = conclude;
	}

	@Column(name = "totalHoursAssigPerson")
	public Integer getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(Integer totalHours) {
		this.totalHours = totalHours;
	}

	@Column(name = "totalExtraHoursAssigPerson")
	public Integer getTotalExtraHours() {
		return totalExtraHours;
	}

	public void setTotalExtraHours(Integer totalExtraHours) {
		this.totalExtraHours = totalExtraHours;
	}
	
	@Column(name = "totalCostAssigPerson")
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
				+ ((historyPerson == null) ? 0 : historyPerson.getId().hashCode());
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
		AssignmentPerson other = (AssignmentPerson) obj;
		if (historyPerson == null) {
			if (other.historyPerson != null)
				return false;
		} else if (!historyPerson.equals(other.historyPerson))
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
		return "AssignmentPerson [id=" + id + ", idTask=" + task.getId()
				+ ", idHistoryPerson=" + historyPerson.getId() + ", conclude=" + conclude
				+ ", totalHours=" + totalHours + ", totalExtraHours="
				+ totalExtraHours + ", totalCost=" + totalCost + "]";
	}
	
}
