package org.summarize.assistant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.summarize.assistant.dto.SummarizeRequest;
import org.summarize.assistant.dto.SummarizeResponse;
import org.summarize.assistant.service.SummarizeService;

@RestController
@RequestMapping("/api/summarize")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SummarizeController {

    private final SummarizeService summarizeService;

    @PostMapping
    public ResponseEntity<SummarizeResponse> summarize(@RequestBody SummarizeRequest request) {

       return ResponseEntity.ok(summarizeService.summarizeContent(request));

    }

}
