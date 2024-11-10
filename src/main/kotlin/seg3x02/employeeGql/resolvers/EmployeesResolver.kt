package seg3x02.employeeGql.resolvers

import com.expediagroup.graphql.server.operations.Query
import com.expediagroup.graphql.server.operations.Mutation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeeRepository

@Controller
class EmployeesResolver @Autowired constructor(
    private val employeeRepository: EmployeeRepository
) : Query, Mutation {

    // Query to get all employees
    fun getEmployees(): List<Employee> = employeeRepository.findAll()

    // Query to get a specific employee by ID
    fun getEmployeeById(id: String): Employee? = employeeRepository.findById(id).orElse(null)

    // Mutation to add a new employee
    fun addEmployee(name: String, dateOfBirth: String, city: String, salary: Float, gender: String?, email: String?): Employee {
        val newEmployee = Employee(name, dateOfBirth, city, salary, gender, email)
        return employeeRepository.save(newEmployee)
    }

    // Mutation to update an existing employee
    fun updateEmployee(id: String, name: String?, dateOfBirth: String?, city: String?, salary: Float?, gender: String?, email: String?): Employee? {
        val employee = employeeRepository.findById(id).orElse(null)
        employee?.apply {
            if (name != null) this.name = name
            if (dateOfBirth != null) this.dateOfBirth = dateOfBirth
            if (city != null) this.city = city
            if (salary != null) this.salary = salary
            if (gender != null) this.gender = gender
            if (email != null) this.email = email
        }
        return employee?.let { employeeRepository.save(it) }
    }
}

