package com.dizio1.fittracker.food.client;

import com.dizio1.fittracker.food.config.UsdaProperties;
import com.dizio1.fittracker.food.dto.FoodSearchResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class FoodClient {

    private final WebClient client;
    private final UsdaProperties props;

    public FoodClient(WebClient client, UsdaProperties props) {
        this.client = client;
        this.props = props;
    }


    public Mono<FoodSearchResponse> searchFood(String query, int pageSize, int pageNumber) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("fdc/v1/foods/search")
                        .queryParam("api_key",props.apiKey())
                        .queryParam("query", query)
                        .queryParam("pageSize", pageSize)
                        .queryParam("pageNumber", pageNumber)
                        .build())
                .retrieve()
                .bodyToMono(FoodSearchResponse.class);
    }
}
