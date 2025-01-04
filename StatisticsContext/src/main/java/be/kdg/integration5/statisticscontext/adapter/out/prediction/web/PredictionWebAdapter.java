package be.kdg.integration5.statisticscontext.adapter.out.prediction.web;

import be.kdg.integration5.statisticscontext.adapter.out.exception.PredictionsRequestFailedException;
import be.kdg.integration5.statisticscontext.domain.Player;
import be.kdg.integration5.statisticscontext.domain.Predictions;
import be.kdg.integration5.statisticscontext.port.out.FetchPredictionPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Map;

@Component
public class PredictionWebAdapter implements FetchPredictionPort {

    private final WebClient webClient;
    private final PredictionsWebConverter predictionsWebConverter;

    @Value("${predictions.url}")
    private String predictionsUrl;

    public PredictionWebAdapter(WebClient.Builder webClientBuilder, PredictionsWebConverter predictionsWebConverter) {
        this.predictionsWebConverter = predictionsWebConverter;
        this.webClient = WebClient.builder().build();
    }

    @Override
    public Map<Player, Predictions> fetchPredictions(List<Player> players) {
        PredictionsRequest predictionsRequest = predictionsWebConverter.toRequest(players);

        PredictionsResponse response;
        try {
            response =  webClient.post()
                    .uri(predictionsUrl)
                    .bodyValue(predictionsRequest)
                    .retrieve()
                    .bodyToMono(PredictionsResponse.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new PredictionsRequestFailedException("Predictions API returned an error: " + e.getMessage());
        } catch (WebClientRequestException e) {
            throw new PredictionsRequestFailedException("Predictions API is unreachable: " + e.getMessage());
        }
        if (response == null)
            throw new PredictionsRequestFailedException("Could not convert predictions response to DTO.");

        return predictionsWebConverter.toMap(response, players);
    }
}
