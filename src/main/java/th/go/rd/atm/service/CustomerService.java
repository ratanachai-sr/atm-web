package th.go.rd.atm.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import th.go.rd.atm.data.CustomerRepository;
import th.go.rd.atm.model.Customer;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

//        private List<Customer> customers = new ArrayList<>();
    private CustomerRepository repository;

//    @PostConstruct
//    public void postConstruct(){
//        customers = new ArrayList<>();
//    }

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public void createCustomer(Customer customer){
        String hashPin = hash(customer.getPin());
        customer.setPin(hashPin);
        repository.save(customer);
//        customers.add(customer);
    }

    public List<Customer> getCustomers(){
        return repository.findAll();
//        return new ArrayList<>(customers);
    }

    public Customer findCustomer(int id) {
        return repository.findById(id);
//        for (Customer customer : customers) {
//            if (customer.getId() == id)
//                return customer;
//        }
//        return null;
    }

    public Customer checkPin(Customer inputCustomer) {
        Customer storedCustomer = findCustomer(inputCustomer.getId());

        if (storedCustomer != null){
            String storedPin = storedCustomer.getPin();
            if (BCrypt.checkpw(inputCustomer.getPin(),storedPin))
                return storedCustomer;
        }
        return null;
    }

    private String hash(String pin) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(pin, salt);
    }

}
