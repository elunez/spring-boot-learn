package com.ydyno.entity;

import lombok.Data;
import javax.persistence.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author Zheng Jie
* @date 2019-10-19
*/
@Entity
@Data
@Table(name="job")
public class Job implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "enabled",nullable = false)
    private Boolean enabled;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "sort",nullable = false)
    private Long sort;
}