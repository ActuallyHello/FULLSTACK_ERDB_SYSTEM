package com.happyfxmas.erdbsystem.modules.persons.api.dto.response;

import com.happyfxmas.erdbsystem.modules.persons.api.dto.GroupDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class StudentWithGroupDTO {
    private Long id;
    private GroupDTO groupDTO;
}
