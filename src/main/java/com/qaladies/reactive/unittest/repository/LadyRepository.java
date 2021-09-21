package com.qaladies.reactive.unittest.repository;

import com.qaladies.reactive.unittest.model.Lady;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LadyRepository extends ReactiveMongoRepository<Lady, String> {
}
