package com.example.stock_microservice.infraestructure.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "category", uniqueConstraints = @UniqueConstraint(columnNames = "categoryName"))
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "categoryName", nullable = false, unique = true, length = 50)
    private String categoryName;

    @Column(name = "categoryDescription", nullable = false, length = 90)
    private String categoryDescription;
}
