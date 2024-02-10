package com.happyfxmas.erdbsystem.modules.tasks.service;


import com.happyfxmas.erdbsystem.modules.ermodels.store.models.Model;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.DenormalizeModel;

import java.util.List;
import java.util.Optional;

public interface DenormalizeModelService {

    DenormalizeModel create(Model model);

    Optional<DenormalizeModel> getByModelWithTasks(Model model);
}
