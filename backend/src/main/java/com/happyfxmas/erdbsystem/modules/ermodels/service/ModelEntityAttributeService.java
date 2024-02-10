package com.happyfxmas.erdbsystem.modules.ermodels.service;


import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.ModelEntityDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.store.models.Model;
import com.happyfxmas.erdbsystem.modules.ermodels.store.models.ModelEntity;

import java.util.List;

public interface ModelEntityAttributeService {
    List<ModelEntity> createEntitiesWithAttributes(List<ModelEntityDTO> modelEntityDTO, Model model);

    List<ModelEntity> getAllByModel(Model model);
}
