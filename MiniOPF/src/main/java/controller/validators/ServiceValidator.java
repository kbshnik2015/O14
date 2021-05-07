package controller.validators;

import java.math.BigInteger;
import java.util.Date;

import controller.exceptions.IllegalTransitionException;
import model.dto.EntityDTO;
import model.dto.ServiceDTO;
import model.enums.ServiceStatus;

public class ServiceValidator implements Validator
{
    @Override
    public boolean validate(final EntityDTO entity)
    {
        Date payDay = ((ServiceDTO) entity).getPayDay();
        BigInteger specId = ((ServiceDTO) entity).getSpecificationId();
        BigInteger customerId = ((ServiceDTO) entity).getCustomerId();
        ServiceStatus serviceStatus = ((ServiceDTO) entity).getServiceStatus();

        if (customerId == null)
        {
            throw new IllegalTransitionException("Service have to have a customer");
        }

        if (specId == null)
        {
            throw new IllegalTransitionException("Service have to have a specification");
        }

        if ((serviceStatus.equals(ServiceStatus.ACTIVE) || serviceStatus.equals(ServiceStatus.PAY_MONEY_SUSPENDED)) &&
                payDay == null)
        {
            {
                throw new IllegalTransitionException("Service with status 'ACTIVE' have to have a payday");
            }
        }

        if (payDay != null && (serviceStatus.equals(ServiceStatus.SUSPENDED) || serviceStatus.equals(
                ServiceStatus.DISCONNECTED)))
        {
            throw new IllegalTransitionException("Service with status 'SUSPENDED'/'DISCONNECTED' can't have a payday");
        }

        return true;
    }
}
