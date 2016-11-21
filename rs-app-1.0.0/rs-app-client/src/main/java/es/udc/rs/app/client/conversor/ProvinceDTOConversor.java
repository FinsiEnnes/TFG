package es.udc.rs.app.client.conversor;

import java.util.ArrayList;
import java.util.List;

import es.udc.rs.app.client.dto.ProvinceDTO;
import es.udc.rs.app.model.domain.Province;

public class ProvinceDTOConversor {

	public static List<ProvinceDTO> toProvincesDTOExceptOne(List<Province> provinces, Long idProvince) {

		List<ProvinceDTO> provincesDTO = new ArrayList<ProvinceDTO>();

		for (Province p : provinces) {
			if (p.getId()!=idProvince) {
				provincesDTO.add(toProvinceDTO(p));
			}	
		}
		return provincesDTO;
	}
	
	
	public static List<ProvinceDTO> toProvincesDTO(List<Province> provinces) {

		List<ProvinceDTO> provincesDTO = new ArrayList<ProvinceDTO>();

		for (Province p : provinces) {
			provincesDTO.add(toProvinceDTO(p));
		}
		return provincesDTO;
	}
	
	public static ProvinceDTO toProvinceDTO(Province province) {
		
		ProvinceDTO provinceDTO = new ProvinceDTO();
		
		// Create the object ProvinceDTO
		provinceDTO.setId(province.getId());
		provinceDTO.setName(province.getName());
		provinceDTO.setIdCountry(province.getCountry().getId());
		provinceDTO.setCountry(province.getCountry().getName());
		
		return provinceDTO;
	}
}
