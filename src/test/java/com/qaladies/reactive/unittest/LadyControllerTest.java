package com.qaladies.reactive.unittest;

import com.qaladies.reactive.unittest.controller.LadyController;
import com.qaladies.reactive.unittest.model.Lady;
import com.qaladies.reactive.unittest.service.LadyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class LadyControllerTest {

    @InjectMocks
    private LadyController ladyController;

    @Mock
    private LadyService ladyService;

    private final Lady lady = LadyMock.lady();

    @BeforeEach
    public void setUp() {
        BDDMockito.when(ladyService.findAllLadies())
                .thenReturn(Flux.just(lady));

        BDDMockito.when(ladyService.findLadyById(ArgumentMatchers.anyString()))
                .thenReturn(Mono.just(lady));

        BDDMockito.when(ladyService.saveLady(lady))
                .thenReturn(Mono.just(lady));


    }


    @Test
    @DisplayName("Deve retornar todas as ladies")
    public void shouldListAllLadies() {
        StepVerifier.create(ladyController.getAllLadies())
                .expectSubscription()
                .expectNext(lady)
                .verifyComplete();
    }

    @Test
    @DisplayName("Deve buscar e retornar uma lady pelo id")
    public void shouldFindAndReturnALadyById() {
        StepVerifier.create(ladyController.findLadyById("1"))
                .expectSubscription()
                .expectNext(lady)
                .verifyComplete();
    }

    @Test
    @DisplayName("Deve criar uma lady")
    public void shouldCreateAnLady() {

        StepVerifier.create(ladyController.createLady(lady))
                .expectSubscription()
                .expectNext(lady)
                .verifyComplete();
    }

}
