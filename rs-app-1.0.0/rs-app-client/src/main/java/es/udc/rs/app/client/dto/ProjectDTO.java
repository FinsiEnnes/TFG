package es.udc.rs.app.client.dto;


public class ProjectDTO {
	
	private Long id;
	private String name;
	private String description;
	private String stateDate;
	private String type;
	private String comment;
	private Long idProvince;
	private String province;
	private String country;
	private Long idCustomer;
	private Integer budget;
	private String nifContact;
	private String nameContact;
	private String surnameContact;
	private String emailContact;
	private Integer daysPlan;
	private Integer daysReal;
	private Integer daysVar;
	private String iniPlan;
	private String iniReal;
	private String iniVar;
	private String endPlan;
	private String endReal;
	private String endVar;
	private Integer hoursPlan;
	private Integer hoursReal;
	private Integer hoursVar;
	private Integer costPlan;
	private Integer costReal;
	private Integer costVar;
	private Integer profitPlan;
	private Integer profitReal;
	private Integer profitVar;
	private Integer loss;
	private Integer progress;
	
	
	public ProjectDTO() {
		
	}
		
	public ProjectDTO(Long id, String name, String description,
			String stateDate, String type, String comment, Long idProvince,
			String province, String country, Long idCustomer, Integer budget,
			String nifContact, String nameContact, String surnameContact,
			String emailContact, Integer daysPlan, Integer daysReal, Integer daysVar,
			String iniPlan, String iniReal, String iniVar, String endPlan,
			String endReal, String endVar, Integer hoursPlan,
			Integer hoursReal, Integer hoursVar, Integer costPlan,
			Integer costReal, Integer costVar, Integer profitPlan,
			Integer profitReal, Integer profifVar, Integer loss, Integer progress) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.stateDate = stateDate;
		this.type = type;
		this.comment = comment;
		this.idProvince = idProvince;
		this.province = province;
		this.country = country;
		this.idCustomer = idCustomer;
		this.budget = budget;
		this.nifContact = nifContact;
		this.nameContact = nameContact;
		this.surnameContact = surnameContact;
		this.emailContact = emailContact;
		this.daysPlan = daysPlan;
		this.daysReal = daysReal;
		this.daysVar = daysVar;
		this.iniPlan = iniPlan;
		this.iniReal = iniReal;
		this.iniVar = iniVar;
		this.endPlan = endPlan;
		this.endReal = endReal;
		this.endVar = endVar;
		this.hoursPlan = hoursPlan;
		this.hoursReal = hoursReal;
		this.hoursVar = hoursVar;
		this.costPlan = costPlan;
		this.costReal = costReal;
		this.costVar = costVar;
		this.profitPlan = profitPlan;
		this.profitReal = profitReal;
		this.profitVar = profifVar;
		this.progress = progress;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStateDate() {
		return stateDate;
	}
	public void setStateDate(String stateDate) {
		this.stateDate = stateDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Long getIdProvince() {
		return idProvince;
	}
	public void setIdProvince(Long idProvince) {
		this.idProvince = idProvince;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Long getIdCustomer() {
		return idCustomer;
	}
	public void setIdCustomer(Long idCustomer) {
		this.idCustomer = idCustomer;
	}
	public Integer getBudget() {
		return budget;
	}
	public void setBudget(Integer budget) {
		this.budget = budget;
	}
	public String getNifContact() {
		return nifContact;
	}
	public void setNifContact(String nifContact) {
		this.nifContact = nifContact;
	}
	public String getNameContact() {
		return nameContact;
	}
	public void setNameContact(String nameContact) {
		this.nameContact = nameContact;
	}
	public String getSurnameContact() {
		return surnameContact;
	}
	public void setSurnameContact(String surnameContact) {
		this.surnameContact = surnameContact;
	}
	public String getEmailContact() {
		return emailContact;
	}
	public void setEmailContact(String emailContact) {
		this.emailContact = emailContact;
	}
	public Integer getDaysVar() {
		return daysVar;
	}
	public void setDaysVar(Integer daysVar) {
		this.daysVar = daysVar;
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
	public String getIniVar() {
		return iniVar;
	}
	public void setIniVar(String iniVar) {
		this.iniVar = iniVar;
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
	public String getEndVar() {
		return endVar;
	}
	public void setEndVar(String endVar) {
		this.endVar = endVar;
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
	public Integer getHoursVar() {
		return hoursVar;
	}
	public void setHoursVar(Integer hoursVar) {
		this.hoursVar = hoursVar;
	}
	public void setHoursReal(Integer hoursReal) {
		this.hoursReal = hoursReal;
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
	public Integer getCostVar() {
		return costVar;
	}
	public void setCostVar(Integer costVar) {
		this.costVar = costVar;
	}
	public Integer getProfitPlan() {
		return profitPlan;
	}
	public void setProfitPlan(Integer profitPlan) {
		this.profitPlan = profitPlan;
	}
	public Integer getProfitReal() {
		return profitReal;
	}
	public void setProfitReal(Integer profitReal) {
		this.profitReal = profitReal;
	}
	public Integer getProfitVar() {
		return profitVar;
	}
	public void setProfitVar(Integer profitVar) {
		this.profitVar = profitVar;
	}
	public Integer getLoss() {
		return loss;
	}
	public void setLoss(Integer loss) {
		this.loss = loss;
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
		ProjectDTO other = (ProjectDTO) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
