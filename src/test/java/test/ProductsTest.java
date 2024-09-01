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
class ProductsTest {
    @TestHTTPResource("/graphql")
    URI baseUri;

    @GraphQLClientApi
    interface Api {
        @Query("products") AllProducts products();

        @Query("products") GetProduct products(@NestedParameter("get") int id);
    }

    record AllProducts(List<Product> all) {}

    record GetProduct(Product get) {}

    record Product(String name) {}

    Api api;

    @BeforeEach
    void setUp() {api = TypesafeGraphQLClientBuilder.newBuilder().endpoint(baseUri).build(Api.class);}


    @Test void shouldGetAllProducts() {
        var all = api.products().all;

        then(all).containsExactly(
                new Product("Oxi"),
                new Product("Juli"),
                new Product("Senn"));
    }

    @Test void shouldGetOxi() {
        var oxi = api.products(1).get;

        then(oxi).isEqualTo(new Product("Oxi"));
    }
}
