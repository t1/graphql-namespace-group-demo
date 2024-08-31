package com.github.t1.demo.graphql.namespaces.products;

import jakarta.annotation.PostConstruct;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Map.Entry.comparingByKey;

@Name("Product")
@GraphQLApi
public class Products {
    private static final AtomicInteger nextId = new AtomicInteger(1);
    private static final Map<Integer, Product> MAP = new ConcurrentHashMap<>();

    @PostConstruct
    void init() {
        add(Product.builder().name("Oxi").description("70ties retro style chair").build());
        add(Product.builder().name("Juli").description("Baroque table for eight people").build());
        add(Product.builder().name("Senn").description("Vintage farmhouse style cupboard").build());
    }

    @Query
    public Product get(int id) {return Optional.ofNullable(MAP.get(id)).orElseThrow(() -> new ProductNotFoundException(id));}

    @Query
    public List<Product> all() {return MAP.entrySet().stream().sorted(comparingByKey()).map(Entry::getValue).toList();}

    @Mutation
    public Product add(Product product) {
        MAP.put(nextId.getAndIncrement(), product);
        return product;
    }

    private static class ProductNotFoundException extends RuntimeException {
        public ProductNotFoundException(int id) {super("product with id " + id + " not found");}
    }
}
