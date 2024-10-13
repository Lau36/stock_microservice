package com.example.stock_microservice.infrastructure.adapter.output.persistence.entity;

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

    @Column(name = "category_name", nullable = false, unique = true, length = 50)
    private String categoryName;

    @Column(name = "category_description", nullable = false, length = 90)
    private String categoryDescription;
}
