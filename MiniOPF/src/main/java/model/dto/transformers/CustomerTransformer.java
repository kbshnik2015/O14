package model.dto.transformers;

import lombok.NoArgsConstructor;
import model.dto.CustomerDTO;
import model.dto.EntityDTO;
import model.entities.Customer;
import model.entities.Entity;

@NoArgsConstructor
public class CustomerTransformer implements Transformer
{
    @Override
    public EntityDTO toDto(Entity entity)
    {
        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setId(entity.getId());
        customerDTO.setFirstName(((Customer) entity).getFirstName());
        customerDTO.setLastName(((Customer) entity).getLastName());
        customerDTO.setLogin(((Customer) entity).getLogin());
        customerDTO.setPassword(((Customer) entity).getPassword());
        customerDTO.setDistrictId(((Customer) entity).getDistrictId());
        customerDTO.setAddress(((Customer) entity).getAddress());
        customerDTO.setBalance(((Customer) entity).getBalance());

        return customerDTO;
    }

    @Override
    public Entity toEntity(EntityDTO entityDTO)
    {
        Customer customer = new Customer();

        customer.setId(entityDTO.getId());
        customer.setFirstName(((CustomerDTO) entityDTO).getFirstName());
        customer.setLastName(((CustomerDTO) entityDTO).getLastName());
        customer.setLogin(((CustomerDTO) entityDTO).getLogin());
        customer.setPassword(((CustomerDTO) entityDTO).getPassword());
        customer.setDistrictId(((CustomerDTO) entityDTO).getDistrictId());
        customer.setAddress(((CustomerDTO) entityDTO).getAddress());
        customer.setBalance(((CustomerDTO) entityDTO).getBalance());

        return customer;
    }
}