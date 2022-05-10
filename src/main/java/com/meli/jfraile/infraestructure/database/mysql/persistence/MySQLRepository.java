package com.meli.jfraile.infraestructure.database.mysql.persistence;

import com.meli.jfraile.infraestructure.database.mysql.model.MutantValidationEntity;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

public class MySQLRepository {

    @Inject private MySQLMutantValidationRepository mutantValidationRepo;

    public List<MutantValidationEntity> getMutantValidationByResult (Boolean result) {
        return mutantValidationRepo.findByResult(result);
    }

    public Integer getMutantValidationCountByResult (Boolean result) {
        return mutantValidationRepo.countByResult(result);
    }

    public MutantValidationEntity saveToMutantValidation (MutantValidationEntity mutantValidationEntity){
        return mutantValidationRepo.save(mutantValidationEntity);
    }

    public MutantValidationEntity getMutantValidationById (String id) {
        Optional<MutantValidationEntity> validation = mutantValidationRepo.findById(id);
        return validation.isPresent() ? validation.get() : null;
    }

}
