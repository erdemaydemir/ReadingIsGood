package com.erdem.readingisgood.service;

import com.erdem.readingisgood.model.ActionLog;
import com.erdem.readingisgood.model.Customer;
import com.erdem.readingisgood.repository.CustomerRepository;
import com.erdem.readingisgood.rest.model.request.CreateCustomerRequest;
import com.erdem.readingisgood.rest.model.response.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ActionLogService actionLogService;
    private final DozerBeanMapper dozerBeanMapper;

    public boolean existsById(String id) {
        return customerRepository.existsById(id);
    }

    public List<CustomerResponse> getCustomers() {
        return customerRepository.findAll().stream().map(order -> dozerBeanMapper.map(order, CustomerResponse.class)).collect(Collectors.toList());
    }

    public void saveWithRequest(CreateCustomerRequest createCustomerRequest) {
        Customer customer = dozerBeanMapper.map(createCustomerRequest, Customer.class);
        this.save(customer);
    }

    public Customer save(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        actionLogService.save(savedCustomer, ActionLog.ActionLogTypeEnum.INSERT, "Customer object inserted");
        return savedCustomer;
    }

    public void deleteById(String id) {
        customerRepository.deleteById(id);
        actionLogService.save(id, ActionLog.ActionLogTypeEnum.DELETE, "Customer object deleted id = " + id);
    }
}
