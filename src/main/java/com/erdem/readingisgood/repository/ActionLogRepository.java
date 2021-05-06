package com.erdem.readingisgood.repository;

import com.erdem.readingisgood.model.ActionLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionLogRepository extends MongoRepository<ActionLog<?>, String> {

}
