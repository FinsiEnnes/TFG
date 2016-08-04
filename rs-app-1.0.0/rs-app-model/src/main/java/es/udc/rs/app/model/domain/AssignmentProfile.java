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
@Table(name = "AssignmentProfile", 
	   uniqueConstraints = { @UniqueConstraint(columnNames = { "idTask", "idProfCatg" }) })
public class AssignmentProfile {
	
	private Long id;
	private Task task;
	private ProfessionalCategory profCatg;
	private Integer units;
	private Integer hoursPerPerson;
	private Integer cost;
	
	// Primary key: id.
	// Not null attribute: task, profCatg, units, hoursPerPerson.
	// Unique attribute: (task, profCatg, units).
	// Business key: task, profCatg, units.
	
	public AssignmentProfile() {
		
	}
	
	public AssignmentProfile(Long id, Task task, ProfessionalCategory profCatg,
			Integer units, Integer hoursPerPerson, Integer cost) {
		this.id = id;
		this.task = task;
		this.profCatg = profCatg;
		this.units = units;
		this.hoursPerPerson = hoursPerPerson;
		this.cost = cost;
	}
	
	public AssignmentProfile(Task task, ProfessionalCategory profCatg, 
			Integer units, Integer hoursPerPerson, Integer cost) {
		this.task = task;
		this.profCatg = profCatg;
		this.units = units;
		this.hoursPerPerson = hoursPerPerson;
		this.cost = cost;
	}
	
	public AssignmentProfile(Task task, ProfessionalCategory profCatg, Integer units, Integer hoursPerPerson) {
		this.task = task;
		this.profCatg = profCatg;
		this.units = units;
		this.hoursPerPerson = hoursPerPerson;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idAssigProf")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "idTask", foreignKey = @ForeignKey(name = "fk_assigprof_task"))
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@ManyToOne
	@JoinColumn(name = "idProfCatg", foreignKey = @ForeignKey(name = "fk_assigprof_idprofcatg"))
	public ProfessionalCategory getProfCatg() {
		return profCatg;
	}

	public void setProfCatg(ProfessionalCategory profCatg) {
		this.profCatg = profCatg;
	}

	@Column(name = "unitsAssigProf", nullable = true)
	public Integer getUnits() {
		return units;
	}

	public void setUnits(Integer units) {
		this.units = units;
	}

	@Column(name = "hoursPerPersonAssigProf", nullable = true)
	public Integer getHoursPerPerson() {
		return hoursPerPerson;
	}

	public void setHoursPerPerson(Integer hoursPerPerson) {
		this.hoursPerPerson = hoursPerPerson;
	}

	@Column(name = "costAssigProf", nullable = true)
	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((hoursPerPerson == null) ? 0 : hoursPerPerson.hashCode());
		result = prime * result
				+ ((profCatg == null) ? 0 : profCatg.getId().hashCode());
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
		AssignmentProfile other = (AssignmentProfile) obj;
		if (hoursPerPerson == null) {
			if (other.hoursPerPerson != null)
				return false;
		} else if (!hoursPerPerson.equals(other.hoursPerPerson))
			return false;
		if (profCatg == null) {
			if (other.profCatg != null)
				return false;
		} else if (!profCatg.equals(other.profCatg))
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
		return "AssignmentProfile [id=" + id + ", idTask=" + task.getId() + ", idProfCatg="
				+ profCatg.getId() + ", units=" + units + ", hoursPerPerson="
				+ hoursPerPerson + ", cost=" + cost + "]";
	}

}
