package org.summarize.assistant.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SummarizeRequest {
    private String content;
    private String systemInstructions;
}
