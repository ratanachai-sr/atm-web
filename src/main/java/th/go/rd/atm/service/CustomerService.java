package th.go.rd.atm.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import th.go.rd.atm.model.Customer;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private List<Customer> customers;

    @PostConstruct
    public void postConstruct(){
        customers = new ArrayList<>();
    }

    public String hash(String pin){
        String salt=BCrypt.gensalt(12);
        return BCrypt.hashpw(pin,salt);
    }

    public void createCustomer(Customer customer){
        String hashPin = hash(customer.getPin());
        customer.setPin(hashPin);
        customers.add(customer);
    }

    public List<Customer> getCustomers(){
        return new ArrayList<>(customers);
    }

    public Customer checkPin(Customer inputCustomer){
        Customer matchingCustomer = null;
        for (Customer c : customers) {
            if (c.getId() == inputCustomer.getId()){
                matchingCustomer = c;
            }
        }
        if (matchingCustomer != null){
            if (BCrypt.checkpw(inputCustomer.getPin(),matchingCustomer.getPin()))
                return matchingCustomer;
        }
        return null;
    }
}
