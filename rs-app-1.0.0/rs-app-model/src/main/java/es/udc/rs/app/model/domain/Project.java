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

import org.hibernate.annotations.Type;

@Entity
@Table(name = "Project")
public class Project {

	private Long id;
	private String name;
	private String description;
	private Date stateDate;
	private boolean inner;
	private String comment;
	private Province province;
	private Customer customer;
	private Integer budget;
	private String nifContact;
	private String nameContact;
	private String surnameContact;
	private String emailContact;
	private Integer daysPlan;
	private Integer daysReal;
	private Date iniPlan;
	private Date iniReal;
	private Date endPlan;
	private Date endReal;
	private Integer hoursPlan;
	private Integer hoursReal;
	private Integer costPlan;
	private Integer costReal;
	private Integer profitPlan;
	private Integer profitReal;
	private Integer loss;
	private Integer progress;
	
	public Project() {
		
	}
	public Project(Long id, String name, String description, Date stateDate,
			boolean inner, String comment, Province province,
			Customer customer, Integer budget, String nifContact,
			String nameContact, String surnameContact, String emailContact,
			Integer daysPlan, Integer daysReal, Date iniPlan, Date iniReal,
			Date endPlan, Date endReal, Integer hoursPlan, Integer hoursReal,
			Integer costPlan, Integer costReal, Integer profitPlan,
			Integer profitReal, Integer loss, Integer progress) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.stateDate = stateDate;
		this.inner = inner;
		this.comment = comment;
		this.province = province;
		this.customer = customer;
		this.budget = budget;
		this.nifContact = nifContact;
		this.nameContact = nameContact;
		this.surnameContact = surnameContact;
		this.emailContact = emailContact;
		this.daysPlan = daysPlan;
		this.daysReal = daysReal;
		this.iniPlan = iniPlan;
		this.iniReal = iniReal;
		this.endPlan = endPlan;
		this.endReal = endReal;
		this.hoursPlan = hoursPlan;
		this.hoursReal = hoursReal;
		this.costPlan = costPlan;
		this.costReal = costReal;
		this.profitPlan = profitPlan;
		this.profitReal = profitReal;
		this.loss = loss;
		this.progress = progress;
	}

	public Project(String name, Date stateDate, boolean inner, Province province, Date iniPlan) {
		this.name = name;
		this.stateDate = stateDate;
		this.inner = inner;
		this.province = province;
		this.iniPlan = iniPlan;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idProject")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "nameProject", nullable = true, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "descProject")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "stateDateProject", nullable = true)
	@Type(type = "date")
	public Date getStateDate() {
		return stateDate;
	}

	public void setStateDate(Date stateDate) {
		this.stateDate = stateDate;
	}

	@Column(name = "innerProject", nullable = true)
	public boolean isInner() {
		return inner;
	}

	public void setInner(boolean inner) {
		this.inner = inner;
	}

	@Column(name = "commentProject")
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@ManyToOne
	@JoinColumn(name = "idProvince", foreignKey = @ForeignKey(name = "fk_project_country"))
	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	@ManyToOne
	@JoinColumn(name = "idCustomer", foreignKey = @ForeignKey(name = "fk_project_customer"))
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Column(name = "budgetProject")
	public Integer getBudget() {
		return budget;
	}

	public void setBudget(Integer budget) {
		this.budget = budget;
	}

	@Column(name = "nifContact")
	public String getNifContact() {
		return nifContact;
	}

	public void setNifContact(String nifContact) {
		this.nifContact = nifContact;
	}

	@Column(name = "nameContact")
	public String getNameContact() {
		return nameContact;
	}

	public void setNameContact(String nameContact) {
		this.nameContact = nameContact;
	}

	@Column(name = "surnameContact")
	public String getSurnameContact() {
		return surnameContact;
	}

	public void setSurnameContact(String surnameContact) {
		this.surnameContact = surnameContact;
	}

	@Column(name = "emailContact")
	public String getEmailContact() {
		return emailContact;
	}

	public void setEmailContact(String emailContact) {
		this.emailContact = emailContact;
	}

	@Column(name = "daysPlanProject")
	public Integer getDaysPlan() {
		return daysPlan;
	}

	public void setDaysPlan(Integer daysPlan) {
		this.daysPlan = daysPlan;
	}

	@Column(name = "daysRealProject")
	public Integer getDaysReal() {
		return daysReal;
	}

	public void setDaysReal(Integer daysReal) {
		this.daysReal = daysReal;
	}

	@Column(name = "iniPlanProject", nullable = true)
	@Type(type = "date")
	public Date getIniPlan() {
		return iniPlan;
	}

	public void setIniPlan(Date iniPlan) {
		this.iniPlan = iniPlan;
	}

	@Column(name = "iniRealProject")
	@Type(type = "date")
	public Date getIniReal() {
		return iniReal;
	}

	public void setIniReal(Date iniReal) {
		this.iniReal = iniReal;
	}

	@Column(name = "endPlanProject")
	@Type(type = "date")
	public Date getEndPlan() {
		return endPlan;
	}

	public void setEndPlan(Date endPlan) {
		this.endPlan = endPlan;
	}

	@Column(name = "endRealProject")
	@Type(type = "date")
	public Date getEndReal() {
		return endReal;
	}

	public void setEndReal(Date endReal) {
		this.endReal = endReal;
	}

	@Column(name = "hoursPlanProject")
	public Integer getHoursPlan() {
		return hoursPlan;
	}

	public void setHoursPlan(Integer hoursPlan) {
		this.hoursPlan = hoursPlan;
	}

	@Column(name = "hoursRealProject")
	public Integer getHoursReal() {
		return hoursReal;
	}

	public void setHoursReal(Integer hoursReal) {
		this.hoursReal = hoursReal;
	}

	@Column(name = "costPlanProject")
	public Integer getCostPlan() {
		return costPlan;
	}

	public void setCostPlan(Integer costPlan) {
		this.costPlan = costPlan;
	}

	@Column(name = "costRealProject")
	public Integer getCostReal() {
		return costReal;
	}

	public void setCostReal(Integer costReal) {
		this.costReal = costReal;
	}

	@Column(name = "profitPlanProject")
	public Integer getProfitPlan() {
		return profitPlan;
	}

	public void setProfitPlan(Integer profitPlan) {
		this.profitPlan = profitPlan;
	}

	@Column(name = "profitRealProject")
	public Integer getProfitReal() {
		return profitReal;
	}

	public void setProfitReal(Integer profitReal) {
		this.profitReal = profitReal;
	}

	@Column(name = "lossProject")
	public Integer getLoss() {
		return loss;
	}
	
	public void setLoss(Integer loss) {
		this.loss = loss;
	}
	
	@Column(name = "progressProject")
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
		Project other = (Project) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + ", description="
				+ description + ", stateDate=" + stateDate + ", inner=" + inner
				+ ", comment=" + comment + ", province=" + province
				+ ", customer=" + customer + ", budget=" + budget
				+ ", nifContact=" + nifContact + ", nameContact=" + nameContact
				+ ", surnameContact=" + surnameContact + ", emailContact="
				+ emailContact + ", daysPlan=" + daysPlan + ", daysReal="
				+ daysReal + ", iniPlan=" + iniPlan + ", iniReal=" + iniReal
				+ ", endPlan=" + endPlan + ", endReal=" + endReal
				+ ", hoursPlan=" + hoursPlan + ", hoursReal=" + hoursReal
				+ ", costPlan=" + costPlan + ", costReal=" + costReal
				+ ", profitPlan=" + profitPlan + ", profitReal=" + profitReal
				+ ", progress=" + progress
				+ "]";
	}

}
