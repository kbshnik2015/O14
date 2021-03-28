package model.database.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.database.exceptions.DataNotCreatedWarning;
import model.database.exceptions.DataNotUpdatedWarning;
import model.entities.Customer;

public class CustomerDAO extends AbstractDAO<Customer>
{
    private static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM customers ORDER BY id";
    private static final String SELECT_CUSTOMER_BY_ID = "SELECT * FROM customers WHERE id =?";
    private static final String INSERT_INTO_CUSTOMERS = "INSERT INTO customers VALUES (?, ?, ?, ?, ?, " +
            "?, ?)";
    private static final String UPDATE_CUSTOMER = "UPDATE customers SET first_name =?, last_name =?, password =?, " +
            "address =?, balance =? WHERE id =?;";

    public CustomerDAO(final Connection connection)
    {
        super(connection, "customers");
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
                Customer customer = new Customer();
                customer.setId(parseToBigInteger(resultSet.getLong("id")));
                customer.setFirstName(resultSet.getString("first_name"));
                customer.setLastName(resultSet.getString("last_name"));
                customer.setLogin(resultSet.getString("login"));
                customer.setPassword(resultSet.getString("password"));
                customer.setAddress(resultSet.getString("address"));
                customer.setBalance(resultSet.getFloat("balance"));
                customers.add(customer);
            }
        }
        return customers;
    }

    @Override
    public Customer findById(final BigInteger id) throws SQLException
    {
        Customer customer = new Customer();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CUSTOMER_BY_ID))
        {
            preparedStatement.setLong(1, parseToLong(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                customer.setId(parseToBigInteger(resultSet.getLong("id")));
                customer.setFirstName(resultSet.getString("first_name"));
                customer.setLastName(resultSet.getString("last_name"));
                customer.setLogin(resultSet.getString("login"));
                customer.setPassword(resultSet.getString("password"));
                customer.setAddress(resultSet.getString("address"));
                customer.setBalance(resultSet.getFloat("balance"));
            }
        }

        return customer;
    }

    @Override
    public void create(final Customer entity) throws SQLException, DataNotCreatedWarning
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_CUSTOMERS))
        {
            preparedStatement.setLong(1, parseToLong(entity.getId()));
            preparedStatement.setString(2, entity.getFirstName());
            preparedStatement.setString(3, entity.getLastName());
            preparedStatement.setString(4, entity.getLogin());
            preparedStatement.setString(5, entity.getPassword());
            preparedStatement.setString(6, entity.getAddress());
            preparedStatement.setFloat(7, entity.getBalance());
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
