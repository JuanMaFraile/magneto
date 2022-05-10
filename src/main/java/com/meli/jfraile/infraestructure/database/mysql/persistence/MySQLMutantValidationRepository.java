package com.meli.jfraile.infraestructure.database.mysql.persistence;

import com.meli.jfraile.infraestructure.database.mysql.model.MutantValidationEntity;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.GenericRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MySQLMutantValidationRepository extends GenericRepository<MutantValidationEntity, String> {

    @Query(value = "SELECT dna, result " +
            "FROM mutant_validation " +
            "WHERE result = :result")
    List<MutantValidationEntity> findByResult (Boolean result);

    Optional<MutantValidationEntity> findById (String id);

    int countByResult(boolean result);

    MutantValidationEntity save (MutantValidationEntity mutantValidationEntity);

}
