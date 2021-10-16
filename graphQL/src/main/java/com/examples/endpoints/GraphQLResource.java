package com.examples.endpoints;

import com.examples.database.QueryPracticeRepository;
import com.examples.database.TestUser;
import graphql.GraphQL;
import org.slf4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import static org.slf4j.LoggerFactory.getLogger;

@Path("/")
public class GraphQLResource {
    private static final Logger LOGGER = getLogger(GraphQLResource.class);

    private QueryPracticeRepository repo;

    public GraphQLResource(QueryPracticeRepository repo) {
        this.repo = repo;
    }

    private GraphQL graphQL;
    private static final String QUERY = "" +
            "    subscription StockCodeSubscription {\n" +
            "        stockQuotes(stockCode:\"IBM') {\n" +
            "            dateTime\n" +
            "            stockCode\n" +
            "            stockPrice\n" +
            "            stockPriceChange\n" +
            "        }\n" +
            "    }\n";


    public GraphQLResource(){}

    public GraphQLResource(GraphQL graphQL) {
        this.graphQL = graphQL;
    }

    @POST
    @Path("query")
    @Consumes(MediaType.APPLICATION_JSON)
    public TestUser createTinyUrl(String createTinyUrl) {

        System.out.println("Hi there this many users " + repo.getTestUsers().size());
        return null;
    }
}
