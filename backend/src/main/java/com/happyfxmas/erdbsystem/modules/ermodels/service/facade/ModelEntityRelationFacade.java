package com.happyfxmas.erdbsystem.modules.ermodels.service.facade;

import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.ModelDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.ModelEntityDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.RelationDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.response.ModelDetailDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.response.ModelWithPersonDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.api.mapper.AttributeDTOMapper;
import com.happyfxmas.erdbsystem.modules.ermodels.api.mapper.ModelDetailDTOMapper;
import com.happyfxmas.erdbsystem.modules.ermodels.api.mapper.ModelEntityDTOMapper;
import com.happyfxmas.erdbsystem.modules.ermodels.api.mapper.ModelWithPersonDTOMapper;
import com.happyfxmas.erdbsystem.modules.ermodels.api.mapper.RelationDTOMapper;
import com.happyfxmas.erdbsystem.modules.ermodels.service.ModelEntityAttributeService;
import com.happyfxmas.erdbsystem.modules.ermodels.service.ModelService;
import com.happyfxmas.erdbsystem.modules.ermodels.service.RelationService;
import com.happyfxmas.erdbsystem.modules.ermodels.store.models.Model;
import com.happyfxmas.erdbsystem.modules.ermodels.store.models.ModelEntity;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.PersonDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class ModelEntityRelationFacade {
    private final ModelService modelService;
    private final ModelEntityAttributeService modelEntityAttributeService;
    private final RelationService relationService;

    public Model createModelWithRelatedEntities(Model model,
                                                Person person,
                                                List<ModelEntityDTO> modelEntityDTOList,
                                                List<RelationDTO> relationDTOList) {
        person.addModel(model);
        model = modelService.create(model);
        var modelEntityList =
                modelEntityAttributeService.createEntitiesWithAttributes(modelEntityDTOList, model);
        var relationList =
                relationService.collectRelationsBetweenEntities(relationDTOList, modelEntityList);
        relationService.createEntitiesRelations(relationList);
        return model;
    }

    public List<ModelWithPersonDTO> getAllModelsWithPersonDTOPageable(Integer page,
                                                                      Integer size,
                                                                      Boolean includeStudents,
                                                                      Boolean includeTaskResults) {
        page = page == null ? 0 : page;
        size = size == null ? 100 : size;
        var pageable = PageRequest.of(page, size, Sort.by("id", "title"));
        if (includeStudents == null) includeStudents = false;
        if (includeTaskResults == null) includeTaskResults = false;
        var modelList = modelService.getAll(pageable, includeStudents, includeTaskResults);
        return modelList.stream()
                .map(model -> ModelWithPersonDTOMapper.makeDTO(
                        model,
                        PersonDTOMapper.makeDTO(model.getPerson())))
                .toList();
    }

    public ModelDetailDTO getModelDetailDTOByModel(Model model) {
        var personDTO = PersonDTOMapper.makeDTO(model.getPerson());
        var modelEntityDTOList = modelEntityAttributeService.getAllByModel(model).stream()
                .map(modelEntity -> {
                    var attributeDTOList = modelEntity.getAttributeList().stream()
                            .map(AttributeDTOMapper::makeDTO)
                            .toList();
                    return ModelEntityDTOMapper.makeDTO(modelEntity, attributeDTOList);
                }).toList();
        var modelEntityIdList = modelEntityDTOList.stream()
                .map(ModelEntityDTO::getId)
                .toList();
        var relationDTOList = relationService.getRelationsByEntityIds(modelEntityIdList).stream()
                .map(RelationDTOMapper::makeDTO)
                .toList();
        return ModelDetailDTOMapper.makeDTO(model, personDTO, modelEntityDTOList, relationDTOList);
    }

    public void deleteModelWithRelations(Model model) {
        relationService.deleteRelationsFromModel(model);
        modelService.deleteModel(model);
    }
}
