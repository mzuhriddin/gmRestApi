package com.example.gmrestapi.entity;

import com.example.gmrestapi.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NotNull
@Entity
@Data
@NoArgsConstructor
public class GM extends AbsEntity {
    private String corpName;

    private String director;

    @OneToOne
    private Address address;

}
