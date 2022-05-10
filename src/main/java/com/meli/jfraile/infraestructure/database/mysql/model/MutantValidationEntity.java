package com.meli.jfraile.infraestructure.database.mysql.model;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Introspected
@Table(name = "mutant_validation")
public class MutantValidationEntity {

    @Id private String id;

    @Column(name = "result")
    private Boolean result;

}
