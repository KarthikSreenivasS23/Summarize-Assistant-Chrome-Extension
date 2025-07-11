package org.summarize.assistant.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.View;
import org.summarize.assistant.dto.GeminiApiRequest;
import org.summarize.assistant.dto.GeminiApiResponse;
import org.summarize.assistant.dto.SummarizeRequest;
import org.summarize.assistant.dto.SummarizeResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Service

public class SummarizeService {

    private final View error;
    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String apiKey;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private static final String systemInstructions = "You are an expert summarizer tasked with analyzing and summarizing web content, articles, or text. Your goal is to produce a concise, clear, and accurate summary that captures the main points and core message of the content in 3-5 sentences. Focus on the following:\n" +
            "\n" +
            "1. Identify Key Ideas: Extract the primary arguments, findings, or themes without including unnecessary details or examples.\n" +
            "2. Be Concise: Summarize in a short, crisp manner, avoiding fluff or redundant information.\n" +
            "3. Maintain Clarity: Use clear, straightforward language suitable for a general audience.\n" +
            "4. Preserve Intent: Reflect the tone and purpose of the original content (e.g., informative, persuasive, narrative).\n" +
            "5. Avoid Personal Opinions**: Stick to the content's facts and intent without adding your own analysis or bias.";

    public SummarizeService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper, View error) {
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
        this.error = error;
    }

    public SummarizeResponse summarizeContent(SummarizeRequest request) {
        GeminiApiRequest geminiApiRequest = generateGeminiApiRequest(request.getContent(), !Objects.isNull(request.getSystemInstructions()) ? request.getSystemInstructions() : systemInstructions);

        var response = webClient.post()
                .uri(geminiApiUrl)
                .header("x-goog-api-key", apiKey)
                .bodyValue(geminiApiRequest)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .doOnNext(errorBody->System.out.println("Api error: "+errorBody))
                            .flatMap(errorBody-> Mono.error(new RuntimeException("Api error: "+errorBody)));
                })
                .bodyToMono(GeminiApiResponse.class).block();

        if (!response.getCandidates().isEmpty()) {
            var content = response.getCandidates().get(0).getContent();
            if (Objects.nonNull(content)) {
                if (!content.getParts().isEmpty()) {
                    return new SummarizeResponse(content.getParts().get(0).getText());
                } else throw new RuntimeException("Unable to summarize content");
            } else throw new RuntimeException("Unable to summarize content");
        } else {
            throw new RuntimeException("Unable to summarize content");
        }


    }

    private GeminiApiRequest generateGeminiApiRequest(String content, String systemInstructions) {

        return GeminiApiRequest.builder()
                .contents(List.of(GeminiApiRequest.Content.builder().parts(
                        List.of(GeminiApiRequest.Part.builder().text(content).build()
                        )).build()
                ))
                .systemInstruction(GeminiApiRequest.SystemInstruction.builder().parts(
                        List.of(GeminiApiRequest.Part.builder().text(systemInstructions).build()
                        )).build())
                .build();

    }


}
