package com.happyfxmas.erdbsystem.modules.ermodels.api.dto.response;

import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.ModelDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.ModelEntityDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.RelationDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ModelDetailDTO extends ModelDTO {
    private List<ModelEntityDTO> modelEntityDTOList;
    private List<RelationDTO> relationDTOList;
    private PersonDTO personDTO;
}
