package es.udc.rs.app.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Material", 
	   uniqueConstraints = { @UniqueConstraint(columnNames = { "nameMaterial", "costMaterial" }) })
public class Material {
	
	private Long id;
    private String name;
    private String description;
    private Integer cost;
    private boolean inner;
	
	// Primary key: id.
	// Not null attribute: name, cost, inner.
	// Unique attribute: (name, cost).
	// Business key: name, cost.
    
    public Material() {
    	
    }
    
    public Material(Long id, String name, String description, Integer cost, boolean inner) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.inner = inner;
	}
    
    public Material(String name, String description, Integer cost, boolean inner) {
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.inner = inner;
	}

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idMaterial")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "nameMaterial", nullable = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "descMaterial")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "costMaterial", nullable = true)
	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	@Column(name = "innerMaterial", nullable = true)
	public boolean isInner() {
		return inner;
	}

	public void setInner(boolean inner) {
		this.inner = inner;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
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
		Material other = (Material) obj;
		if (cost == null) {
			if (other.cost != null)
				return false;
		} else if (!cost.equals(other.cost))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Material [id=" + id + ", name=" + name + ", description="
				+ description + ", cost=" + cost + ", inner=" + inner + "]";
	}
	    
}
