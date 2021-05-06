package com.erdem.readingisgood.repository;

import com.erdem.readingisgood.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

}
