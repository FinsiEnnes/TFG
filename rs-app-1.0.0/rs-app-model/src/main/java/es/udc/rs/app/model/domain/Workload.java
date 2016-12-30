package es.udc.rs.app.model.domain;

import java.util.Date;

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

import org.hibernate.annotations.Type;

@Entity
@Table(name = "Workload", 
	   uniqueConstraints = { @UniqueConstraint(columnNames = { "dayDateWorkload", "idTask", "idHPerson"}) })
public class Workload {
	
	private Long id;
	private Task task;
	private HistoryPerson historyPerson;
	private Date dayDate;
	private Integer hours;
	private Integer extraHours;
	
	// Primary key: id.
	// Not null attribute: task, historyPerson, dayDate, hours, extraHours.
	// Unique attribute: (task, historyPerson, dayDate).
	// Business key: task, historyPerson, dayDate.
	
	public Workload() {
		
	}
	
	public Workload(Long id, Task task, HistoryPerson historyPerson,
			Date dayDate, Integer hours, Integer extraHours) {
		this.id = id;
		this.task = task;
		this.historyPerson = historyPerson;
		this.dayDate = dayDate;
		this.hours = hours;
		this.extraHours = extraHours;
	}
	
	public Workload(Task task, HistoryPerson historyPerson, Date dayDate, Integer hours, Integer extraHours) {
		this.task = task;
		this.historyPerson = historyPerson;
		this.dayDate = dayDate;
		this.hours = hours;
		this.extraHours = extraHours;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idWorkload")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "idTask", foreignKey = @ForeignKey(name = "fk_workload_task"))
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@ManyToOne
	@JoinColumn(name = "idHPerson", foreignKey = @ForeignKey(name = "fk_workload_person"))
	public HistoryPerson getHistoryPerson() {
		return historyPerson;
	}

	public void setHistoryPerson(HistoryPerson historyPerson) {
		this.historyPerson = historyPerson;
	}

	@Column(name = "dayDateWorkload", nullable = true)
	@Type(type = "date")
	public Date getDayDate() {
		return dayDate;
	}

	public void setDayDate(Date dayDate) {
		this.dayDate = dayDate;
	}

	@Column(name = "hoursWorkload", nullable = true)
	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	@Column(name = "extraHoursWorkload", nullable = true)
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
		Workload other = (Workload) obj;
		if (dayDate == null) {
			if (other.dayDate != null)
				return false;
		} else if (!dayDate.equals(other.dayDate))
			return false;
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
		return "Workload [id=" + id + ", idTask=" + task.getId() + ", idHistoryPerson="
				+ historyPerson.getId() + ", dayDate=" + dayDate + ", hours=" + hours
				+ ", extraHours=" + extraHours + "]";
	}

}
