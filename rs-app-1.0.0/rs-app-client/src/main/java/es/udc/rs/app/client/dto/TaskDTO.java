package es.udc.rs.app.client.dto;


public class TaskDTO {

	private Long id;
	private Long idPhase;
	private String namePhase;
	private String name;
	private String comment;
	private String idState;
	private String state;
	private String idPriority;
	private String priority;
	private Long idResponsible;
	private String nameResponsible;
	private Integer daysPlan;
	private Integer daysReal;
	private String iniPlan;
	private String iniReal;
	private Integer hoursPlan;
	private Integer hoursReal;
	private String endPlan;
	private String endReal;
	private Integer costPlan;
	private Integer costReal;
	private Integer progress;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getIdPhase() {
		return idPhase;
	}
	public void setIdPhase(Long idPhase) {
		this.idPhase = idPhase;
	}
	
	public String getNamePhase() {
		return namePhase;
	}
	public void setNamePhase(String namePhase) {
		this.namePhase = namePhase;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getIdState() {
		return idState;
	}
	public void setIdState(String idState) {
		this.idState = idState;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
		
	public String getIdPriority() {
		return idPriority;
	}
	public void setIdPriority(String idPriority) {
		this.idPriority = idPriority;
	}
	
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	public Long getIdResponsible() {
		return idResponsible;
	}
	public void setIdResponsible(Long idResponsible) {
		this.idResponsible = idResponsible;
	}
	
	public String getNameResponsible() {
		return nameResponsible;
	}
	public void setNameResponsible(String nameResponsible) {
		this.nameResponsible = nameResponsible;
	}
	
	public Integer getDaysPlan() {
		return daysPlan;
	}
	public void setDaysPlan(Integer daysPlan) {
		this.daysPlan = daysPlan;
	}
	
	public Integer getDaysReal() {
		return daysReal;
	}
	public void setDaysReal(Integer daysReal) {
		this.daysReal = daysReal;
	}
	
	public String getIniPlan() {
		return iniPlan;
	}
	public void setIniPlan(String iniPlan) {
		this.iniPlan = iniPlan;
	}
	
	public String getIniReal() {
		return iniReal;
	}
	public void setIniReal(String iniReal) {
		this.iniReal = iniReal;
	}
	
	public Integer getHoursPlan() {
		return hoursPlan;
	}
	public void setHoursPlan(Integer hoursPlan) {
		this.hoursPlan = hoursPlan;
	}
	
	public Integer getHoursReal() {
		return hoursReal;
	}
	public void setHoursReal(Integer hoursReal) {
		this.hoursReal = hoursReal;
	}
	
	public String getEndPlan() {
		return endPlan;
	}
	public void setEndPlan(String endPlan) {
		this.endPlan = endPlan;
	}
	
	public String getEndReal() {
		return endReal;
	}
	public void setEndReal(String endReal) {
		this.endReal = endReal;
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
	
	public Integer getProgress() {
		return progress;
	}
	public void setProgress(Integer progress) {
		this.progress = progress;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPhase == null) ? 0 : idPhase.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		TaskDTO other = (TaskDTO) obj;
		if (idPhase == null) {
			if (other.idPhase != null)
				return false;
		} else if (!idPhase.equals(other.idPhase))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
