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
@Table(name = "Milestone", 
	   uniqueConstraints = { @UniqueConstraint(columnNames = { "idPhase", "nameMilestone" }) })
public class Milestone {
	
	private Long id;
	private Phase phase;
	private String name;
	private Date datePlan;
	private Date dateReal;
	private String comment;
	
	// Primary key: id.
	// Not null attribute: phase, name, datePlan.
	// Unique attribute: (phase, name).
	// Business key: phase, name.
	
	public Milestone() {
		
	}
	
	public Milestone(Long id, Phase phase, String name, Date datePlan, Date dateReal, String comment) {
		this.id = id;
		this.phase = phase;
		this.name = name;
		this.datePlan = datePlan;
		this.dateReal = dateReal;
		this.comment = comment;
	}
	
	public Milestone(Phase phase, String name, Date datePlan, Date dateReal, String comment) {
		this.phase = phase;
		this.name = name;
		this.datePlan = datePlan;
		this.dateReal = dateReal;
		this.comment = comment;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idMilestone")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "idPhase", foreignKey = @ForeignKey(name = "fk_milestone_phase"))
	public Phase getPhase() {
		return phase;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	@Column(name = "nameMilestone", nullable = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "datePlanMilestone", nullable = true)
	@Type(type = "date")
	public Date getDatePlan() {
		return datePlan;
	}

	public void setDatePlan(Date datePlan) {
		this.datePlan = datePlan;
	}

	@Column(name = "dateRealMilestone")
	@Type(type = "date")
	public Date getDateReal() {
		return dateReal;
	}

	public void setDateReal(Date dateReal) {
		this.dateReal = dateReal;
	}
	
	@Column(name = "commentMilestone")
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phase == null) ? 0 : phase.getId().hashCode());
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
		Milestone other = (Milestone) obj;
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
		return "Milestone [id=" + id + ", idPhase=" + phase.getId() + ", name=" + name
				+ ", datePlan=" + datePlan + ", dateReal=" + dateReal + "]";
	}
	
}
