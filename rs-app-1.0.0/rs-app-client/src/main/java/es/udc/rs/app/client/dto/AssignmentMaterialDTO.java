package es.udc.rs.app.client.dto;

public class AssignmentMaterialDTO {

	private Long id;
	private Long idTask;
	private Long idMaterial;
	private String name;
	private Integer cost;
	private String type;
	private boolean plan;
	private boolean real;
	private Integer unitsPlan;
	private Integer unitsReal;
	private Integer costPlan;
	private Integer costReal;
	
	
	public AssignmentMaterialDTO() {
	}
	
	
	public AssignmentMaterialDTO(Long id, Long idTask, Long idMaterial,
			String name, Integer cost, String type, boolean plan, boolean real,
			Integer unitsPlan, Integer unitsReal, Integer costPlan,
			Integer costReal) {
		this.id = id;
		this.idTask = idTask;
		this.idMaterial = idMaterial;
		this.name = name;
		this.cost = cost;
		this.type = type;
		this.plan = plan;
		this.real = real;
		this.unitsPlan = unitsPlan;
		this.unitsReal = unitsReal;
		this.costPlan = costPlan;
		this.costReal = costReal;
	}



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdTask() {
		return idTask;
	}
	public void setIdTask(Long idTask) {
		this.idTask = idTask;
	}
	public Long getIdMaterial() {
		return idMaterial;
	}
	public void setIdMaterial(Long idMaterial) {
		this.idMaterial = idMaterial;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCost() {
		return cost;
	}
	public void setCost(Integer cost) {
		this.cost = cost;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isPlan() {
		return plan;
	}
	public void setPlan(boolean plan) {
		this.plan = plan;
	}
	public boolean isReal() {
		return real;
	}
	public void setReal(boolean real) {
		this.real = real;
	}
	public Integer getUnitsPlan() {
		return unitsPlan;
	}
	public void setUnitsPlan(Integer unitsPlan) {
		this.unitsPlan = unitsPlan;
	}
	public Integer getUnitsReal() {
		return unitsReal;
	}
	public void setUnitsReal(Integer unitsReal) {
		this.unitsReal = unitsReal;
	}
	public Integer getCostPlan() {
		return costPlan;
	}
	public void setCostPlan(Integer costPlan) {
		this.costPlan = costPlan;
	}
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
				+ ((idMaterial == null) ? 0 : idMaterial.hashCode());
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
		AssignmentMaterialDTO other = (AssignmentMaterialDTO) obj;
		if (idMaterial == null) {
			if (other.idMaterial != null)
				return false;
		} else if (!idMaterial.equals(other.idMaterial))
			return false;
		if (idTask == null) {
			if (other.idTask != null)
				return false;
		} else if (!idTask.equals(other.idTask))
			return false;
		return true;
	}

}
