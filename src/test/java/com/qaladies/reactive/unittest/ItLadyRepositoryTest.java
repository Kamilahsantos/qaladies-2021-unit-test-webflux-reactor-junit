package com.qaladies.reactive.unittest;

import com.qaladies.reactive.unittest.model.Lady;
import com.qaladies.reactive.unittest.repository.LadyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class ItLadyRepositoryTest {


    @MockBean
    private LadyRepository ladyRepository;

    @Autowired
    private WebTestClient webTestClient;

    private final Lady lady = LadyMock.lady();


    @BeforeEach
    public void setUp() {
        BDDMockito.when(ladyRepository.findAll())
                .thenReturn(Flux.just(lady));

        BDDMockito.when(ladyRepository.findById(ArgumentMatchers.anyString()))
                .thenReturn(Mono.just(lady));

        BDDMockito.when(ladyRepository.delete(ArgumentMatchers.any(Lady.class)))
                .thenReturn(Mono.empty());


    }

    @Test
    @DisplayName("Deve listar todas as ladies")
    public void shouldListAllLadiesSuccessfully() {
        webTestClient
                .get()
                .uri("/ladies")
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    @DisplayName("Deve retornar uma response válida  ao listar todas as ladies")
    public void ShouldReturnAValidResponseWhenListAllLadies() {
        webTestClient
                .get()
                .uri("/ladies")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(lady.getId())
                .jsonPath("$.[0].name").isEqualTo(lady.getName())
                .jsonPath("$.[0].role").isEqualTo(lady.getRole())
                .jsonPath("$.[0].stack").isEqualTo(lady.getStack());
    }

    @Test
    @DisplayName("Deve buscar e retornar uma lady pelo id")
    public void shouldFindAndReturnALadyByIdSuccessfully() {
        webTestClient
                .get()
                .uri("/ladies/{id}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Lady.class)
                .isEqualTo(lady);
    }

    @Test
    @DisplayName("Deve excluir uma lady pelo id")
    public void shouldDeleteAnLadySuccessfully() {
        webTestClient
                .delete()
                .uri("/ladies/{id}", 1)
                .exchange()
                .expectStatus().isNoContent();
    }
}
