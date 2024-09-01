package com.github.t1.demo.graphql.namespaces.products;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.eclipse.microprofile.graphql.NonNull;

@Getter @Setter @SuperBuilder @NoArgsConstructor
public class Product {
    @NonNull private String name;
    @NonNull private String description;
}
