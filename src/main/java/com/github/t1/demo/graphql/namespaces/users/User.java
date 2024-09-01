package com.github.t1.demo.graphql.namespaces.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.eclipse.microprofile.graphql.NonNull;

import java.time.LocalDate;
import java.time.Period;

@Getter @Setter @SuperBuilder @NoArgsConstructor
public class User {
    @NonNull private String name;
    @NonNull private LocalDate born;

    @SuppressWarnings("unused")
    public int getAge() {return Period.between(born, LocalDate.now()).getYears();}
}
