package com.happyfxmas.erdbsystem.modules.ermodels.api.dto.response;

import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.ModelDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ModelWithPersonDTO extends ModelDTO {
    private PersonDTO personDTO;
}
