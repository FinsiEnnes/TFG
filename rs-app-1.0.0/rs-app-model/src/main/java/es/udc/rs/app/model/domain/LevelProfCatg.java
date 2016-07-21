package es.udc.rs.app.model.domain;

import javax.persistence.*;

@Entity
@Table(name = "LevelProfCatg")
public class LevelProfCatg {
	
	private String id;
	private String name;
	
	public LevelProfCatg() {
		
	}
	
	public LevelProfCatg(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public LevelProfCatg(String name) {
		this.name = name;
	}

	@Id
	@Column(name = "idLevelProfCatg")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "nameLevelProfCatg", nullable = true, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		LevelProfCatg other = (LevelProfCatg) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		return "LevelProfCatg [id=" + id + ", name=" + name + "]";
	}
	
}
