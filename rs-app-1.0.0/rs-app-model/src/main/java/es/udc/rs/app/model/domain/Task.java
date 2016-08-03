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

@Entity
@Table(name = "Task", 
	   uniqueConstraints = { @UniqueConstraint(columnNames = { "idPhase", "nameTask" }) })
public class Task {

	private Long id;
	private Phase phase;
	private String name;
	private String comment;
	private State state;
	private Priority priority;
	private HistoryPerson historyPerson;
	private Integer daysPlan;
	private Integer daysReal;
	private Date iniPlan;
	private Date iniReal;
	private Integer hoursPlan;
	private Integer hoursReal;
	private Date endPlan;
	private Date endReal;
	private Integer costPlan;
	private Integer costReal;
	
	// Primary key: id.
	// Not null attribute: phase, name, state, priority, historyPerson, daysPlan.
	// Unique attribute: (phase, name).
	// Business key: phase, name.
	
	public Task() {
		
	}
	
	public Task(Long id, Phase phase, String name, String comment, State state,
			Priority priority, HistoryPerson historyPerson, Integer daysPlan,
			Integer daysReal, Date iniPlan, Date iniReal, Integer hoursPlan,
			Integer hoursReal, Date endPlan, Date endReal, Integer costPlan,
			Integer costReal) {
		this.id = id;
		this.phase = phase;
		this.name = name;
		this.comment = comment;
		this.state = state;
		this.priority = priority;
		this.historyPerson = historyPerson;
		this.daysPlan = daysPlan;
		this.daysReal = daysReal;
		this.iniPlan = iniPlan;
		this.iniReal = iniReal;
		this.hoursPlan = hoursPlan;
		this.hoursReal = hoursReal;
		this.endPlan = endPlan;
		this.endReal = endReal;
		this.costPlan = costPlan;
		this.costReal = costReal;
	}

	public Task(Phase phase, String name, State state, Priority priority,
			HistoryPerson historyPerson, Integer daysPlan) {
		super();
		this.phase = phase;
		this.name = name;
		this.state = state;
		this.priority = priority;
		this.historyPerson = historyPerson;
		this.daysPlan = daysPlan;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idTask")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "idPhase", foreignKey = @ForeignKey(name = "fk_task_phase"))
	public Phase getPhase() {
		return phase;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	@Column(name = "nameTask", nullable = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "commentTask")
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@ManyToOne
	@JoinColumn(name = "idState", foreignKey = @ForeignKey(name = "fk_task_state"))
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	@ManyToOne
	@JoinColumn(name = "idPriority", foreignKey = @ForeignKey(name = "fk_task_priority"))
	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	@ManyToOne
	@JoinColumn(name = "idHPerson", foreignKey = @ForeignKey(name = "fk_task_person"))
	public HistoryPerson getHistoryPerson() {
		return historyPerson;
	}

	public void setHistoryPerson(HistoryPerson historyPerson) {
		this.historyPerson = historyPerson;
	}

	@Column(name = "daysPlanTask", nullable = true)
	public Integer getDaysPlan() {
		return daysPlan;
	}

	public void setDaysPlan(Integer daysPlan) {
		this.daysPlan = daysPlan;
	}

	@Column(name = "daysRealTask")
	public Integer getDaysReal() {
		return daysReal;
	}

	public void setDaysReal(Integer daysReal) {
		this.daysReal = daysReal;
	}

	@Column(name = "iniPlanTask")
	public Date getIniPlan() {
		return iniPlan;
	}

	public void setIniPlan(Date iniPlan) {
		this.iniPlan = iniPlan;
	}

	@Column(name = "iniRealTask")
	public Date getIniReal() {
		return iniReal;
	}

	public void setIniReal(Date iniReal) {
		this.iniReal = iniReal;
	}

	@Column(name = "hoursPlanTask")
	public Integer getHoursPlan() {
		return hoursPlan;
	}

	public void setHoursPlan(Integer hoursPlan) {
		this.hoursPlan = hoursPlan;
	}

	@Column(name = "hoursRealTask")
	public Integer getHoursReal() {
		return hoursReal;
	}

	public void setHoursReal(Integer hoursReal) {
		this.hoursReal = hoursReal;
	}

	@Column(name = "endPlanTask")
	public Date getEndPlan() {
		return endPlan;
	}

	public void setEndPlan(Date endPlan) {
		this.endPlan = endPlan;
	}

	@Column(name = "endRealTask")
	public Date getEndReal() {
		return endReal;
	}

	public void setEndReal(Date endReal) {
		this.endReal = endReal;
	}

	@Column(name = "costPlanTask")
	public Integer getCostPlan() {
		return costPlan;
	}

	public void setCostPlan(Integer costPlan) {
		this.costPlan = costPlan;
	}

	@Column(name = "costRealTask")
	public Integer getCostReal() {
		return costReal;
	}

	public void setCostReal(Integer costReal) {
		this.costReal = costReal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phase == null) ? 0 : phase.hashCode());
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
		Task other = (Task) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phase == null) {
			if (other.phase != null)
				return false;
		} else if (!phase.equals(other.phase))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", idPhase=" + phase.getId() + ", name=" + name
				+ ", comment=" + comment + ", idState=" + state.getId() + ", idPriority="
				+ priority.getId() + ", idHistoryPerson=" + historyPerson.getId() + ", daysPlan="
				+ daysPlan + ", daysReal=" + daysReal + ", iniPlan=" + iniPlan
				+ ", iniReal=" + iniReal + ", hoursPlan=" + hoursPlan
				+ ", hoursReal=" + hoursReal + ", endPlan=" + endPlan
				+ ", endReal=" + endReal + ", costPlan=" + costPlan
				+ ", costReal=" + costReal + "]";
	}

}
