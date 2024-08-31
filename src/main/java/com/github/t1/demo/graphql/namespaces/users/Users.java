package com.github.t1.demo.graphql.namespaces.users;

import jakarta.annotation.PostConstruct;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Map.Entry.comparingByKey;

@Name("User")
@GraphQLApi
public class Users {
    private static final AtomicInteger nextId = new AtomicInteger(1);
    private static final Map<Integer, User> MAP = new ConcurrentHashMap<>();

    @PostConstruct
    void init() {
        add(User.builder().name("Jane Doe").born(LocalDate.of(1952, 10, 21)).build());
        add(User.builder().name("John Doe").born(LocalDate.of(1977, 3, 1)).build());
        add(User.builder().name("Jill Doe").born(LocalDate.of(2002, 1, 12)).build());
    }

    @Query
    public User get(int id) {return Optional.ofNullable(MAP.get(id)).orElseThrow(() -> new UserNotFoundException(id));}

    @Query
    public List<User> all() {return MAP.entrySet().stream().sorted(comparingByKey()).map(Entry::getValue).toList();}

    @Mutation
    public User add(User user) {
        MAP.put(nextId.getAndIncrement(), user);
        return user;
    }

    private static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(int id) {super("user with id " + id + " not found");}
    }
}
