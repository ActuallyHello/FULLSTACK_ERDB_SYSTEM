package com.happyfxmas.erdbsystem.modules.tasks.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.happyfxmas.erdbsystem.externals.TestDataLoader;
import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.TestDataDTO;
import com.happyfxmas.erdbsystem.modules.tasks.exception.service.ConvertEntityToJsonException;
import com.happyfxmas.erdbsystem.modules.tasks.service.TaskDataGeneratorService;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.Task;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.enums.Complexity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service
public class TaskDataGeneratorServiceImpl implements TaskDataGeneratorService {

    private final TestDataLoader testDataLoader;
    private final ObjectMapper objectMapper;

    @Override
    public TestDataDTO generateDataForTask(Task task) {
        TestDataDTO testData = new TestDataDTO();
        for (var denormalizeModel : task.getDenormalizeModelList()) {
            var mapView = getMapFromJsonView(denormalizeModel.getView());
            List<String> entities = new ArrayList<>();
            List<String> attributes = new ArrayList<>();
            collectEntitiesAndAttributesDependsOnComplexityFromView(
                    mapView,
                    task.getComplexity(),
                    entities,
                    attributes);
            List<List<String>> data = testDataLoader.loadData(
                    denormalizeModel.getModel().getTitle(),
                    attributes,
                    task.getTestDataAmount());
            testData.getEntities().addAll(entities);
            testData.getAttributes().addAll(attributes);
            testData.getTestData().addAll(data);
        }
        return testData;
    }

    private void collectEntitiesAndAttributesDependsOnComplexityFromView(Map<String, List<Map<String, String>>> mapView,
                                                                         Complexity complexity,
                                                                         List<String> entities,
                                                                         List<String> attributes) {
        switch (complexity) {
            case EASY -> {
                mapView.forEach((key, value) -> {
                    entities.add(key);
                    attributes.addAll(value.stream().map(attributeMap -> attributeMap.get("title")).toList());
                });
            }
            case NORMAL -> {
                mapView.values().forEach(value -> attributes.addAll(
                        value.stream()
                                .map(attributeMap -> attributeMap.get("title"))
                                .toList()));
            }
            case DIFFICULT -> {
                mapView.values().forEach(value -> attributes.addAll(
                        value.stream()
                                .filter(attributeMap -> !attributeMap.get("type").equalsIgnoreCase("PK"))
                                .map(attributeMap -> attributeMap.get("title"))
                                .toList()));
            }
            default -> {
                log.error("NO SUCH COMPLEXITY={} TO GENERATE TEST DATA!", complexity);
                throw new IllegalArgumentException("Error when generating test data for task! " +
                        "Complexity with value=%s does not exist!".formatted(complexity.getComplexity()));
            }
        }
    }

    private Map<String, List<Map<String, String>>> getMapFromJsonView(String jsonView) {
        try {
            return objectMapper.readValue(jsonView, new TypeReference<>() {});
        } catch (JsonProcessingException exception) {
            log.error("ERROR WHEN PARSING JSON FIELD FROM MODEL: {}", exception.getMessage());
            throw new ConvertEntityToJsonException("Error when parsing json field!", exception);
        }
    }
}
