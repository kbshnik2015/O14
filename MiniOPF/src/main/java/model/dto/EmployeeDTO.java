package model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import model.enums.EmployeeStatus;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EmployeeDTO extends AbstractUserDTO
{
    private EmployeeStatus employeeStatus;

    private boolean isWaitingForOrders;
}
