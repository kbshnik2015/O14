package model.database.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.database.exceptions.DataNotCreatedWarning;
import model.database.exceptions.DataNotFoundWarning;
import model.database.exceptions.DataNotUpdatedWarning;
import model.entities.Customer;

public class CustomerDAO extends AbstractDAO<Customer>
{
    private static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM customers ORDER BY id_customer";
    private static final String SELECT_CUSTOMER_BY_ID = "SELECT * FROM customers WHERE id_customer =?";
    private static final String DELETE_CUSTOMER = "DELETE FROM customers WHERE id_customer = ?;";
    private static final String INSERT_INTO_CUSTOMERS = "INSERT INTO customers VALUES (nextval('CustomerSeq'), ?, ?, ?, ?, " +
            "?, ?)";
    private static final String UPDATE_CUSTOMER = "UPDATE customers SET first_name =?, last_name =?, password =?, " +
            "address =?, balance =? WHERE id_customer = ?;";

    public CustomerDAO(final Connection connection)
    {
        super(connection);
        this.tableName = "customers";
    }

    @Override
    public List<Customer> findAll() throws SQLException
    {
        List<Customer> customers = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CUSTOMERS))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                BigInteger id_customer = parseToBigInteger(resultSet.getLong("id_customer"));
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String address = resultSet.getString("address");
                float balance = resultSet.getFloat("balance");
                customers.add(new Customer(id_customer, firstName, lastName, login, password, address, balance));
            }
        }
        return customers;
    }

    @Override
    public Customer findById(final BigInteger id) throws SQLException
    {
        Customer customer = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CUSTOMER_BY_ID))
        {
            preparedStatement.setLong(1, parseToLong(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                BigInteger id_customer = parseToBigInteger(resultSet.getLong("id_customer"));
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String address = resultSet.getString("address");
                float balance = resultSet.getFloat("balance");
                customer = new Customer(id_customer, firstName, lastName, login, password, address, balance);
            }
        }

        return customer;
    }

    @Override
    public void delete(final BigInteger id) throws SQLException, DataNotFoundWarning
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CUSTOMER))
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
    public void delete(final Customer entity) throws SQLException, DataNotFoundWarning
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CUSTOMER))
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
            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CUSTOMER))
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
            String warningMessage = "Customers with ids : ";
            for (BigInteger id : notDeletedIds)
            {
                warningMessage = warningMessage.concat(id + ", ");
            }
            warningMessage = warningMessage.concat("weren't deleted");
            throw new DataNotFoundWarning(warningMessage);
        }
    }

    @Override
    public void create(final Customer entity) throws SQLException, DataNotCreatedWarning
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_CUSTOMERS))
        {
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setString(3, entity.getLogin());
            preparedStatement.setString(4, entity.getPassword());
            preparedStatement.setString(5, entity.getAddress());
            preparedStatement.setFloat(6, entity.getBalance());
            boolean isObjectNotCreated = preparedStatement.executeUpdate() == 0;
            if (isObjectNotCreated)
            {
                throw new DataNotCreatedWarning("Object wasn't created");
            }
        }
    }

    @Override
    public void update(final Customer entity) throws SQLException, DataNotUpdatedWarning
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CUSTOMER))
        {
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setString(3, entity.getPassword());
            preparedStatement.setString(4, entity.getAddress());
            preparedStatement.setFloat(5, entity.getBalance());
            preparedStatement.setLong(6, parseToLong(entity.getId()));
            boolean isObjectNotUpdated = preparedStatement.executeUpdate() == 0;
            if (isObjectNotUpdated)
            {
                throw new DataNotUpdatedWarning("Object wasn't updated");
            }
        }
    }
}
