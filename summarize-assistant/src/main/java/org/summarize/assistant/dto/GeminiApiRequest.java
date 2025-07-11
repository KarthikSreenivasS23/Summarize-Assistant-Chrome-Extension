package org.summarize.assistant.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class GeminiApiRequest {

    @JsonProperty("system_instruction")
    private SystemInstruction systemInstruction;
    private List<Content> contents;
    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SystemInstruction {
        private List<Part> parts;
    }

    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Content {
        private List<Part> parts;
    }

    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Part {
        private String text;
    }
}
