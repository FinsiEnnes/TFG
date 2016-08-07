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
	   uniqueConstraints = { @UniqueConstraint(columnNames = { "idTask", "idMaterial" }) })
public class AssignmentMaterial {
	
	private Long id;
	private Task task;
	private Material material;
	private boolean plan;
	private boolean real;
	private Integer unitsPlan;
	private Integer unitsReal;
	private Integer costPlan;
	private Integer costReal;
	
	// Primary key: id.
	// Not null attribute: task, material, plan, real.
	// Unique attribute: (task, material).
	// Business key: task, material.
	
	public AssignmentMaterial() {
		
	}
	
	public AssignmentMaterial(Long id, Task task, Material material,
			boolean plan, boolean real, Integer unitsPlan, Integer unitsReal,
			Integer costPlan, Integer costReal) {
		this.id = id;
		this.task = task;
		this.material = material;
		this.plan = plan;
		this.real = real;
		this.unitsPlan = unitsPlan;
		this.unitsReal = unitsReal;
		this.costPlan = costPlan;
		this.costReal = costReal;
	}
	
	public AssignmentMaterial(Task task, Material material,
			boolean plan, boolean real, Integer unitsPlan, Integer unitsReal,
			Integer costPlan, Integer costReal) {
		this.task = task;
		this.material = material;
		this.plan = plan;
		this.real = real;
		this.unitsPlan = unitsPlan;
		this.unitsReal = unitsReal;
		this.costPlan = costPlan;
		this.costReal = costReal;
	}
	
	public AssignmentMaterial(Task task, Material material) {
		this.task = task;
		this.material = material;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idAssigMat")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "idTask", foreignKey = @ForeignKey(name = "fk_assigMat_task"))
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@ManyToOne
	@JoinColumn(name = "idMaterial", foreignKey = @ForeignKey(name = "fk_assigMat_idMaterial"))
	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	@Column(name = "planAssigMat", nullable = true)
	public boolean isPlan() {
		return plan;
	}

	public void setPlan(boolean plan) {
		this.plan = plan;
	}

	@Column(name = "realAssigMat", nullable = true)
	public boolean isReal() {
		return real;
	}

	public void setReal(boolean real) {
		this.real = real;
	}

	@Column(name = "unitsPlanAssigMat")
	public Integer getUnitsPlan() {
		return unitsPlan;
	}

	public void setUnitsPlan(Integer unitsPlan) {
		this.unitsPlan = unitsPlan;
	}

	@Column(name = "unitsRealAssigMat")
	public Integer getUnitsReal() {
		return unitsReal;
	}

	public void setUnitsReal(Integer unitsReal) {
		this.unitsReal = unitsReal;
	}

	@Column(name = "costPlanAssigMat")
	public Integer getCostPlan() {
		return costPlan;
	}

	public void setCostPlan(Integer costPlan) {
		this.costPlan = costPlan;
	}

	@Column(name = "costRealAssigMat")
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
		result = prime * result
				+ ((material == null) ? 0 : material.getId().hashCode());
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
		AssignmentMaterial other = (AssignmentMaterial) obj;
		if (material == null) {
			if (other.material != null)
				return false;
		} else if (!material.equals(other.material))
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
		return "AssignmentMaterial [id=" + id + ", idTask=" + task.getId()
				+ ", idMaterial=" + material.getId() + ", plan=" + plan + ", real="
				+ real + ", unitsPlan=" + unitsPlan + ", unitsReal="
				+ unitsReal + ", costPlan=" + costPlan + ", costReal="
				+ costReal + "]";
	}

}
