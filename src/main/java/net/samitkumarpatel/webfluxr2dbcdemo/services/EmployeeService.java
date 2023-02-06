package net.samitkumarpatel.webfluxr2dbcdemo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.samitkumarpatel.webfluxr2dbcdemo.models.Employee;
import net.samitkumarpatel.webfluxr2dbcdemo.repositories.EmployeeRepository;
import net.samitkumarpatel.webfluxr2dbcdemo.utility.NotFoundException;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    public Flux<Employee> getAll() {
        return employeeRepository.findAll();
    }
    public Mono<Employee> getById(long id) {
        return employeeRepository.findById(id);
    }

    public Mono<Employee> update(long id, Employee employee) {
        return employeeRepository
                .findById(id)
                .map(employee1 -> {
                  return new Employee(employee.id(), employee.name(), employee.address(), employee.designation(), employee.salary(), employee.doj(), employee.department());
                })
                .flatMap(updateEmployee -> employeeRepository.save(updateEmployee));
    }

    public Mono<Void> delete(long id) {
        return employeeRepository.deleteById(id);
    }
    public Mono<Void> save(Employee employee) {
        return employeeRepository.save(employee).then();
    }
}
