package test;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.graphql.client.typesafe.api.GraphQLClientApi;
import io.smallrye.graphql.client.typesafe.api.NestedParameter;
import io.smallrye.graphql.client.typesafe.api.TypesafeGraphQLClientBuilder;
import org.eclipse.microprofile.graphql.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@QuarkusTest
class UsersTest {
    @TestHTTPResource("/graphql")
    URI baseUri;

    @GraphQLClientApi
    interface Api {
        @Query("User") AllUsers users();

        @Query("User") GetUser user(@NestedParameter("get") int id);

        @Query("User") GetUserWithAge userWithAge(@NestedParameter("get") int id);
    }

    record AllUsers(List<User> all) {}

    record GetUser(User get) {}

    record GetUserWithAge(UserWithAge get) {}

    record User(String name) {}

    record UserWithAge(String name, int age) {}

    Api api;

    @BeforeEach
    void setUp() {api = TypesafeGraphQLClientBuilder.newBuilder().endpoint(baseUri).build(Api.class);}


    @Test void shouldGetJane() {
        var user = api.user(1).get;

        then(user).isEqualTo(new User("Jane Doe"));
    }

    @Test void shouldGetJaneWithAge() {
        var user = api.userWithAge(1).get;

        then(user.name()).isEqualTo("Jane Doe");
        then(user.age()).isGreaterThan(70);
    }

    @Test void shouldGetAllUsers() {
        var all = api.users().all;

        then(all).containsExactly(
                new User("Jane Doe"),
                new User("John Doe"),
                new User("Jill Doe"));
    }
}
