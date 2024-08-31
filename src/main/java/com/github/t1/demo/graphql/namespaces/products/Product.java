package com.github.t1.demo.graphql.namespaces.products;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter @SuperBuilder @NoArgsConstructor
public class Product {
    @NonNull private String name;
    @NonNull private String description;
}
