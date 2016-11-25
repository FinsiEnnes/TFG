package es.udc.rs.app.client.dto;


public class FreeDayDTO {

	private Long id;
	private String name;
	private Integer weekDay;
	private String weekDayString;
	private String ini;
	private String end;
	
	
	public FreeDayDTO() {
	}
	
	public FreeDayDTO(Long id, String name, Integer weekDay, String weekDayString, String ini, String end) {
		this.id = id;
		this.name = name;
		this.weekDay = weekDay;
		this.weekDayString = weekDayString;
		this.ini = ini;
		this.end = end;
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

	public Integer getWeekDay() {
		return weekDay;
	}

	public String getWeekDayString() {
		return weekDayString;
	}

	public void setWeekDayString(String weekDayString) {
		this.weekDayString = weekDayString;
	}

	public void setWeekDay(Integer weekDay) {
		this.weekDay = weekDay;
	}

	public String getIni() {
		return ini;
	}

	public void setIni(String ini) {
		this.ini = ini;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
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
		FreeDayDTO other = (FreeDayDTO) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
