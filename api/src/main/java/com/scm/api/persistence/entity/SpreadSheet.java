package com.scm.api.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Data
@Getter
@Setter
public class SpreadSheet {
    
    @Id
    private Integer id;
    
    @Column
    private String name;
    
    @Column
    private String columns;

}
