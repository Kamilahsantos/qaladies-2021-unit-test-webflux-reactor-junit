package com.qaladies.reactive.unittest;

import com.qaladies.reactive.unittest.model.Lady;
import com.qaladies.reactive.unittest.repository.LadyRepository;
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
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;



@ExtendWith(SpringExtension.class)
public class LadyServiceTest {

    @InjectMocks
    private LadyService ladyService;

    @Mock
    private LadyRepository ladyRepository;

    private final Lady lady = LadyMock.lady();


    @BeforeEach
    public void setUp() {

        BDDMockito.when(ladyRepository.save(LadyMock.lady()))
                .thenReturn(Mono.just(lady));

        BDDMockito.when(ladyRepository.findAll())
                .thenReturn(Flux.just(lady));

        BDDMockito.when(ladyRepository.findById(ArgumentMatchers.anyString()))
                .thenReturn(Mono.just(lady));


        BDDMockito.when(ladyRepository.delete(ArgumentMatchers.any(Lady.class)))
                .thenReturn(Mono.empty());
    }

    @Test
    @DisplayName("Deve criar uma lady")
    public void shouldCreateAnLadySuccessfully() {
        Lady lady = LadyMock.lady();

        StepVerifier.create(ladyService.saveLady(lady))
                .expectSubscription()
                .expectNext(lady)
                .verifyComplete();
    }

    @Test
    @DisplayName("Deve listar todas as ladies")
    public void shouldReturnAllLadiesSuccessfully() {
        StepVerifier.create(ladyService.findAllLadies())
                .expectSubscription()
                .expectNext(lady)
                .verifyComplete();
    }

    @Test
    @DisplayName("Deve buscar e retornar uma lady pelo id ")
    public void shouldFindByIdAndReturnAnLadySuccessfully() {
        StepVerifier.create(ladyService.findLadyById("1"))
                .expectSubscription()
                .expectNext(lady)
                .verifyComplete();
    }

    @Test
    @DisplayName("Deve deletar uma lady com sucesso")
    public void shouldDeleteAnLadySuccessfully() {
        StepVerifier.create(ladyService.deleteLady("2"))
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    @DisplayName("Deve retornar exception quando não encontrar uma lady")
    public void ShouldReturnExceptionWhenLadyNotFound() {
        BDDMockito.when(ladyRepository.findById(ArgumentMatchers.anyString()))
                .thenReturn(Mono.empty());

        StepVerifier.create(ladyService.findLadyById("lady nao criada"))
                .expectSubscription()
                .expectError(ResponseStatusException.class)
                .verify();
    }

}
