package model.database.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.database.exceptions.DataNotCreatedWarning;
import model.database.exceptions.DataNotFoundWarning;
import model.database.exceptions.DataNotUpdatedWarning;
import model.entities.Service;
import model.enums.ServiceStatus;

public class ServiceDAO extends AbstractDAO<Service>
{
    private static final String SELECT_ALL_SERVICES = "SELECT * FROM services ORDER BY id_service";
    private static final String SELECT_SERVICE_BY_ID = "SELECT * FROM services WHERE id_service =?";
    private static final String DELETE_SERVICE = "DELETE FROM services WHERE id_service = ?;";
    private static final String INSERT_INTO_SERVICES = "INSERT INTO services VALUES (nextval('ServiceSeq'), ?, ?, ?, ?)";
    private static final String UPDATE_SERVICE = "UPDATE services SET pay_day =?, specification_id =?, status =?, " +
            "customer_id =? WHERE id_service = ?;";

    public ServiceDAO(final Connection connection)
    {
        super(connection);
        this.tableName = "services";
    }

    private static ServiceStatus parseToServiceStatus(final String arg)
    {
        ServiceStatus serviceStatus = null;
        switch (arg.toUpperCase())
        {
            case "ACTIVE":
                serviceStatus = ServiceStatus.ACTIVE;
                break;
            case "DISCONNECTED":
                serviceStatus = ServiceStatus.DISCONNECTED;
                break;
            case "PAY_MONEY_SUSPENDED":
                serviceStatus = ServiceStatus.PAY_MONEY_SUSPENDED;
                break;
            case "SUSPENDED":
                serviceStatus = ServiceStatus.SUSPENDED;
                break;
        }
        return serviceStatus;
    }

    @Override
    public List<Service> findAll() throws SQLException
    {
        List<Service> services = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SERVICES))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                BigInteger id_service = parseToBigInteger(resultSet.getLong("id_service"));
                Date payDay = resultSet.getDate("pay_day");
                BigInteger specification_id = parseToBigInteger(resultSet.getLong("specification_id"));
                ServiceStatus serviceStatus = parseToServiceStatus(resultSet.getString("status"));
                BigInteger customer_id = parseToBigInteger(resultSet.getLong("customer_id"));
                services.add(new Service(id_service, payDay, specification_id, serviceStatus, customer_id));
            }
        }

        return services;
    }

    @Override
    public Service findById(final BigInteger id) throws SQLException
    {
        Service service = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SERVICE_BY_ID))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                BigInteger id_service = parseToBigInteger(resultSet.getLong("id_service"));
                Date payDay = resultSet.getDate("pay_day");
                BigInteger specification_id = parseToBigInteger(resultSet.getLong("specification_id"));
                ServiceStatus serviceStatus = parseToServiceStatus(resultSet.getString("status"));
                BigInteger customer_id = parseToBigInteger(resultSet.getLong("customer_id"));
                service = new Service(id_service, payDay, specification_id, serviceStatus, customer_id);
            }
        }

        return service;
    }

    @Override
    public void delete(final BigInteger id) throws SQLException, DataNotFoundWarning
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SERVICE))
        {
            preparedStatement.setLong(1, parseToLong(id));
            boolean isObjectNotFound = preparedStatement.executeUpdate() == 0;
            if (isObjectNotFound)
            {
                throw new DataNotFoundWarning("Object wasn't found for deletion");
            }
        }
    }

    @Override
    public void delete(final Service entity) throws SQLException, DataNotFoundWarning
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SERVICE))
        {
            preparedStatement.setLong(1, parseToLong(entity.getId()));
            boolean isObjectNotFound = preparedStatement.executeUpdate() == 0;
            if (isObjectNotFound)
            {
                throw new DataNotFoundWarning("Object wasn't found for deletion");
            }
        }
    }

    @Override
    public void delete(final List<BigInteger> ids) throws SQLException, DataNotFoundWarning
    {
        List<BigInteger> notDeletedIds = new ArrayList<>();
        for (BigInteger id : ids)
        {
            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SERVICE))
            {
                preparedStatement.setLong(1, parseToLong(id));
                boolean isObjectNotFound = preparedStatement.executeUpdate() == 0;
                if (isObjectNotFound)
                {
                    notDeletedIds.add(id);
                }
            }
        }

        if (notDeletedIds.size() > 0)
        {
            String warningMessage = "Services with ids : ";
            for (BigInteger id : notDeletedIds)
            {
                warningMessage = warningMessage.concat(id + ", ");
            }
            warningMessage = warningMessage.concat("weren't deleted");
            throw new DataNotFoundWarning(warningMessage);
        }
    }

    @Override
    public void create(final Service entity) throws SQLException, DataNotCreatedWarning
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_SERVICES))
        {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            preparedStatement.setString(1, formatter.format(entity.getPayDay()));
            preparedStatement.setLong(2, parseToLong(entity.getSpecificationId()));
            preparedStatement.setString(3, entity.getServiceStatus().toString());
            preparedStatement.setLong(4, parseToLong(entity.getCustomerId()));
            boolean isObjectNotCreated = preparedStatement.executeUpdate() == 0;
            if (isObjectNotCreated)
            {
                throw new DataNotCreatedWarning("Object wasn't created");
            }
        }
    }

    @Override
    public void update(final Service entity) throws SQLException, DataNotUpdatedWarning
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SERVICE))
        {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            preparedStatement.setString(1, formatter.format(entity.getPayDay()));
            preparedStatement.setLong(2, parseToLong(entity.getSpecificationId()));
            preparedStatement.setString(3, entity.getServiceStatus().toString());
            preparedStatement.setLong(4, parseToLong(entity.getCustomerId()));
            preparedStatement.setLong(5, parseToLong(entity.getId()));
            boolean isObjectNotUpdated = preparedStatement.executeUpdate() == 0;
            if (isObjectNotUpdated)
            {
                throw new DataNotUpdatedWarning("Object wasn't updated");
            }
        }
    }
}
