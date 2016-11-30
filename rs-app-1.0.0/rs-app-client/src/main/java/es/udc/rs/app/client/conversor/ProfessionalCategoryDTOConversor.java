package es.udc.rs.app.client.conversor;

import java.util.ArrayList;
import java.util.List;

import es.udc.rs.app.client.dto.ProfessionalCategoryDTO;
import es.udc.rs.app.model.domain.ProfessionalCategory;

public class ProfessionalCategoryDTOConversor {

	public static List<ProfessionalCategoryDTO> toProfessionalCategoryDTOList(List<ProfessionalCategory> pcs) {
		
		List<ProfessionalCategoryDTO> pcsDTO = new ArrayList<ProfessionalCategoryDTO>();
		
		for (ProfessionalCategory pc : pcs) {
			pcsDTO.add(toProfessionalCategoryDTO(pc));
		}
		
		return pcsDTO;	
	}
	
	
	public static ProfessionalCategoryDTO toProfessionalCategoryDTO(ProfessionalCategory pc) {
		
		ProfessionalCategoryDTO pcDTO = new ProfessionalCategoryDTO();
		
		pcDTO.setId(pc.getId());
		pcDTO.setName(pc.getName());
		pcDTO.setMinExp(pc.getMinExp());
		pcDTO.setLevel(pc.getLevel().getName());
		pcDTO.setSal(pc.getSal());
		
		// The extra salary could be null so
		String extra = ((pc.getSalExtra()==null) ? "N/A" : String.valueOf(pc.getSalExtra()));
		pcDTO.setSalExtra(extra);
		
		return pcDTO;
	}
}
