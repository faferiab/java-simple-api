package ai.smartassets.challenge.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import ai.smartassets.challenge.entity.BrandEntity;

@Repository
public interface BrandRepository extends MongoRepository<BrandEntity, String>, QuerydslPredicateExecutor<BrandEntity> {
}
