package com.singletonapps.springbootrecipe.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Recipe {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;

    @OneToMany(cascade = ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients;

    @Lob
    private Byte[] image;

    @OneToOne(cascade = ALL)
    private Note note;

    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;
}
