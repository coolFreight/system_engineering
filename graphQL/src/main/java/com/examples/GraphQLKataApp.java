package com.examples;

import com.examples.database.QueryPracticeRepository;
import com.examples.database.TestUserMapper;
import com.examples.endpoints.GraphQLResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.glassfish.grizzly.websockets.WebSocketAddOn;
import org.glassfish.grizzly.websockets.WebSocketApplication;
import org.glassfish.grizzly.websockets.WebSocketEngine;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

public class GraphQLKataApp extends WebSocketApplication {
    public static final String BASE_URI = "http://localhost:8001/graphQL";
    private static final String QUERY = "" +
            "    subscription StockCodeSubscription {\n" +
            "        stockQuotes(stockCode:\"IBM') {\n" +
            "            dateTime\n" +
            "            stockCode\n" +
            "            stockPrice\n" +
            "            stockPriceChange\n" +
            "        }\n" +
            "    }\n";

    private GraphQL graphQL;
    private final static StockTickerPublisher STOCK_TICKER_PUBLISHER = new StockTickerPublisher();

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
        provider.setMapper(mapper);

        PGSimpleDataSource source = new PGSimpleDataSource();
        source.setServerName("localhost");
        int[] ports = new int[2];
        ports[0] = 6000;
        source.setPortNumbers(ports);
        source.setDatabaseName("query_practice");
        source.setUser("postgres");
        source.setPassword("mysecretpassword");


        var dao = Jdbi.create(source)
                .installPlugin(new PostgresPlugin())
                .installPlugin(new SqlObjectPlugin())
                .registerRowMapper(new TestUserMapper())
                .onDemand(QueryPracticeRepository.class);



        var graphqlResource =  new GraphQLResource(dao);

        final ResourceConfig rc = new ResourceConfig()
                .packages("com.examples.endpoints")
                .register(provider);

        rc.register(new AbstractBinder(){
            @Override
            protected void configure() {
                // map this service to this contract
                bind(graphqlResource).to(GraphQLResource.class);
            }
        });
        var server =  GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);


        final WebSocketAddOn addon = new WebSocketAddOn();
        server.getListeners().stream().forEach(l -> l.registerAddOn(addon));
        GraphQLKataApp graphql = new GraphQLKataApp();
        graphql.startGraphQLKata();
        WebSocketEngine.getEngine().register("","/subscribe", graphql);
    }

    public void startGraphQLKata() throws IOException {
        var url = Resources.getResource("katas.graphql");
        var sdl = Resources.toString(url, StandardCharsets.UTF_8);
        SchemaParser schemaParser = new SchemaParser();

        var typeDefinitionRegistry = schemaParser.parse(sdl);
        GraphQLSchema graphQLSchema = buildSchema(typeDefinitionRegistry);
        graphQL = GraphQL
                .newGraphQL(graphQLSchema)
                .build();
    }

    private GraphQLSchema buildSchema(TypeDefinitionRegistry tdr){
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        var rtw = RuntimeWiring
                .newRuntimeWiring()
                .type(newTypeWiring("Subscription").dataFetcher("stockQuotes", stockQuotesSubscriptionFetcher()))
                .build();
        return schemaGenerator.makeExecutableSchema(tdr, rtw);
    }

    private DataFetcher stockQuotesSubscriptionFetcher() {
        return e -> {
            List<String> arg = e.getArgument("stockCodes");
            List<String> stockCodesFilter = arg == null ? Collections.emptyList() : arg;
            if(stockCodesFilter.isEmpty()) {
                return STOCK_TICKER_PUBLISHER.getPublisher();
            } else {
                return STOCK_TICKER_PUBLISHER.getPublisher(stockCodesFilter);
            }
        };
    }
}
