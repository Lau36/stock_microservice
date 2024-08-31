package com.example.stock_microservice.infrastructure.adapter.output.persistence.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="item", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private Integer amount;


    @Column(name = "price")
    private BigDecimal price;


    @Column(name = "id_category")
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "item_category",
            joinColumns = @JoinColumn(name = "id_article", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_category", referencedColumnName = "id")
    )
    private List<CategoryEntity> categories;

    @ManyToOne
    @JoinColumn(name = "id_brand", referencedColumnName = "id")
    private BrandEntity brand;

}
