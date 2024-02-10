package com.happyfxmas.erdbsystem.modules.tasks.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.happyfxmas.erdbsystem.modules.ermodels.store.models.Model;
import com.happyfxmas.erdbsystem.modules.ermodels.store.models.ModelEntity;
import com.happyfxmas.erdbsystem.modules.ermodels.store.models.enums.AttributeType;
import com.happyfxmas.erdbsystem.modules.tasks.exception.service.ConvertEntityToJsonException;
import com.happyfxmas.erdbsystem.modules.tasks.exception.service.DenormalizeModelCreationException;
import com.happyfxmas.erdbsystem.modules.tasks.service.DenormalizeModelService;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.DenormalizeModel;
import com.happyfxmas.erdbsystem.modules.tasks.store.repos.DenormalizeModelRepo;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class DenormalizeModelServiceImpl implements DenormalizeModelService {

    private final DenormalizeModelRepo denormalizeModelRepo;

    @Override
    @Transactional
    public DenormalizeModel create(Model model) {
        String viewJson = convertModelEntitiesToJsonString(model.getModelEntityList(), model.getId());
        var denormalizeModel = DenormalizeModel.builder()
                .view(viewJson)
                .model(model)
                .build();
        try {
            denormalizeModel = denormalizeModelRepo.saveAndFlush(denormalizeModel);
            log.info("CREATED DENORMALIZE MODEL WITH ID={}", denormalizeModel.getId());
            return denormalizeModel;
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN CREATING DENORMALIZE MODEL: {}", exception.getMessage());
            throw new DenormalizeModelCreationException("Error when creating denormalize model! [DatabaseException]", exception);
        }
    }

    @Override
    public Optional<DenormalizeModel> getByModelWithTasks(Model model) {
        var denormalizeModel = denormalizeModelRepo.findByModelIdWithTasks(model);
        log.debug("GET DENORMALIZE MODEL WITH ID={}", model.getId());
        return denormalizeModel;
    }

    private String convertModelEntitiesToJsonString(List<ModelEntity> modelEntityList, Long modelId) {
        Map<String, List<Map<String, String>>> view = modelEntityList.stream()
                .collect(Collectors.toMap(
                        ModelEntity::getTitle,
                        value -> value.getAttributeList().stream()
                                .filter(attribute -> attribute.getAttributeType() != AttributeType.FOREIGN_KEY)
                                .map(attribute -> Map.of(
                                        "title", attribute.getTitle(),
                                        "type", attribute.getAttributeType().getValue()))
                                .toList()));
        view = view.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1) // if entity has more than one PK field after filter
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(view);
        } catch (JsonProcessingException e) {
            log.error("ERROR WHEN CONVERTING MODEL (ID={}) ENTITIES AND ATTRIBUTES TO JSON", modelId);
            throw new ConvertEntityToJsonException("Error when converting model entities to json!", e);
        }
    }
}
