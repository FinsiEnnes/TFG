package es.udc.rs.app.model.domain;

import javax.persistence.*;

@Entity
@Table(name = "ProfessionalCategory", 
	   uniqueConstraints = { @UniqueConstraint(columnNames = { "nameProfCatg", "idLevelProfCatg" }) })
public class ProfessionalCategory {
	private Long id;
	private String name;
	private Integer minExp;
	private LevelProfCatg level;
	private Integer sal;
	private Integer salExtra;
	
	// Primary key: id.
	// Not null attribute: name, minExp, level, sal.
	// Unique attribute: (name, level).
	// Business key: name, minExp, level, sal.
	
	public ProfessionalCategory() {
		
	}
	
	public ProfessionalCategory(Long id, String name, Integer minExp, 
			LevelProfCatg level, Integer sal, Integer salExtra) {
		this.id = id;
		this.name = name;
		this.minExp = minExp;
		this.level = level;
		this.sal = sal;
		this.salExtra = salExtra;
	}
	
	public ProfessionalCategory(String name, Integer minExp, 
			LevelProfCatg level, Integer sal, Integer salExtra) {
		this.name = name;
		this.minExp = minExp;
		this.level = level;
		this.sal = sal;
		this.salExtra = salExtra;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idProfCatg")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "nameProfCatg", nullable = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "minExpProfCatg", nullable = true)
	public Integer getMinExp() {
		return minExp;
	}

	public void setMinExp(Integer minExp) {
		this.minExp = minExp;
	}

	@ManyToOne
	@JoinColumn(name = "idLevelProfCatg", foreignKey = @ForeignKey(name = "fk_profcatg_level"))
	public LevelProfCatg getLevel() {
		return level;
	}

	public void setLevel(LevelProfCatg level) {
		this.level = level;
	}

	@Column(name = "salProfCatg", nullable = true)
	public Integer getSal() {
		return sal;
	}

	public void setSal(Integer sal) {
		this.sal = sal;
	}

	@Column(name = "salExtraProfCatg")
	public Integer getSalExtra() {
		return salExtra;
	}

	public void setSalExtra(Integer salExtra) {
		this.salExtra = salExtra;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((minExp == null) ? 0 : minExp.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((sal == null) ? 0 : sal.hashCode());
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
		ProfessionalCategory other = (ProfessionalCategory) obj;
		if (level == null) {
			if (other.level != null)
				return false;
		} else if (!level.equals(other.level))
			return false;
		if (minExp == null) {
			if (other.minExp != null)
				return false;
		} else if (!minExp.equals(other.minExp))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sal == null) {
			if (other.sal != null)
				return false;
		} else if (!sal.equals(other.sal))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProfessionalCategory [id=" + id + ", name=" + name
				+ ", minExp=" + minExp + ", level=" + level + ", sal=" + sal
				+ ", salExtra=" + salExtra + "]";
	}
}
