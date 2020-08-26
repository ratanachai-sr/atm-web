package th.go.rd.atm.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import th.go.rd.atm.model.Customer;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private List<Customer> customerList;

    @PostConstruct
    public void postConstruct(){
        customerList = new ArrayList<>();
    }

    public void createCustomer(Customer customer){
        customerList.add(customer);
    }

    public List<Customer> getCustomers(){
        return new ArrayList<>(customerList);
    }
}
