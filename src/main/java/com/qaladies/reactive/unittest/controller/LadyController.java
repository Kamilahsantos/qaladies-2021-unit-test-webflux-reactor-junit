package com.qaladies.reactive.unittest.controller;

import com.qaladies.reactive.unittest.model.Lady;
import com.qaladies.reactive.unittest.service.LadyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ladies")
public class LadyController {

    private final LadyService ladyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Lady> createLady(@Valid @RequestBody Lady lady) {
        return ladyService.saveLady(lady);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<Lady> getAllLadies() {
        return ladyService.findAllLadies();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Lady> findLadyById(@PathVariable String id) {
        return ladyService.findLadyById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<Lady>> updateLady(@PathVariable(value = "id") String id,
                                                 @RequestBody Lady lady) {
        return ladyService.updateLady(lady,id);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteLadyById(@PathVariable String id) {
        return ladyService.deleteLady(id);
    }


}
