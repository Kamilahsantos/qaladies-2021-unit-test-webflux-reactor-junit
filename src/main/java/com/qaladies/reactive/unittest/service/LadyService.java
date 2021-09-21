package com.qaladies.reactive.unittest.service;

import com.qaladies.reactive.unittest.model.Lady;
import com.qaladies.reactive.unittest.repository.LadyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class LadyService {

    @Autowired
    private LadyRepository ladyRepository;

    public Mono<Lady> saveLady(Lady lady) {
        return ladyRepository.save(lady);
    }

    public Flux<Lady> findAllLadies() {
        return ladyRepository.findAll();

    }

    public Mono<Lady> findLadyById(String id) {
        return ladyRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));

    }

    public Mono<ResponseEntity<Lady>> updateLady(Lady lady, String id) {
        return ladyRepository.findById(id)
                .flatMap(existingLady -> {
                    existingLady.setName(lady.getName());
                    existingLady.setStack(lady.getStack());
                    existingLady.setRole(lady.getRole());
                    return ladyRepository.save(existingLady);

                }).map(updateLady -> new ResponseEntity<>(updateLady, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Mono<Void> deleteLady(String id) {
        return findLadyById(id)
                .flatMap(ladyRepository::delete);
    }

}
