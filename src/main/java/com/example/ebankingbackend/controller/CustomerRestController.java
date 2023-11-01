package com.example.ebankingbackend.controller;

import com.example.ebankingbackend.dto.CustomerDTO;
import com.example.ebankingbackend.models.Customer;
import com.example.ebankingbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CustomerRestController {
    private BankAccountService bnAccountService;
    @GetMapping("/customers")
    public List<CustomerDTO> customers(){
        return bnAccountService.customers();

    }
    @GetMapping("/customer/{id}")
    public CustomerDTO getCustomer(@PathVariable(name="id") Long CustomerId){
        return bnAccountService.getCustomer(CustomerId);}
    @PostMapping("/customer/add")
    public CustomerDTO getCustomer(@RequestBody CustomerDTO customerDto){
            return bnAccountService.createCustomer(customerDto);
    }
    @PutMapping("/customer/update/{id}")
    public CustomerDTO updateCustomer (@PathVariable Long customerId,@RequestBody  CustomerDTO customerDto){
        customerDto.setId(customerId);
      return  bnAccountService.updateCustomer(customerDto);
    }
    @DeleteMapping("/customer/delete/{id}")
    public void deleteCustomer (@PathVariable Long customerId){
         bnAccountService.deleteCustomer(customerId);
    }

}
