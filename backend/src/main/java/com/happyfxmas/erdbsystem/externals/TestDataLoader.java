package com.happyfxmas.erdbsystem.externals;

import com.happyfxmas.erdbsystem.modules.ermodels.store.models.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TestDataLoader {
    List<List<String>> loadData(String modelTitle, List<String> attributes, Integer testDataAmount);

    void uploadTestDataFileToModel(Model model, MultipartFile file);
}
