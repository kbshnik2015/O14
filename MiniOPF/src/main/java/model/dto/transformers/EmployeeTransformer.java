package model.dto.transformers;

import lombok.NoArgsConstructor;
import model.dto.EmployeeDTO;
import model.dto.EntityDTO;
import model.entities.Employee;
import model.entities.Entity;

@NoArgsConstructor
public class EmployeeTransformer implements Transformer
{
    @Override
    public EntityDTO toDto(Entity entity)
    {
        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setId(entity.getId());
        employeeDTO.setLastName(((Employee) entity).getLastName());
        employeeDTO.setLogin(((Employee) entity).getLogin());
        employeeDTO.setPassword(((Employee) entity).getPassword());
        employeeDTO.setEmployeeStatus(((Employee) entity).getEmployeeStatus());
        employeeDTO.setWaitingForOrders(((Employee) entity).isWaitingForOrders());

        return employeeDTO;
    }

    @Override
    public Entity toEntity(EntityDTO entityDTO)
    {
        Employee employee = new Employee();

        employee.setId(entityDTO.getId());
        employee.setLastName(((EmployeeDTO) entityDTO).getLastName());
        employee.setLogin(((EmployeeDTO) entityDTO).getLogin());
        employee.setPassword(((EmployeeDTO) entityDTO).getPassword());
        employee.setEmployeeStatus(((EmployeeDTO) entityDTO).getEmployeeStatus());
        employee.setWaitingForOrders(((EmployeeDTO) entityDTO).isWaitingForOrders());

        return employee;
    }
}