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
import model.entities.Employee;
import model.enums.EmployeeStatus;

public class EmployeeDAO extends AbstractDAO<Employee>
{
    private static final String SELECT_ALL_EMPLOYEES = "SELECT * FROM employees ORDER BY id";
    private static final String SELECT_EMPLOYEE_BY_ID = "SELECT * FROM employees WHERE id =?";
    private static final String INSERT_INTO_EMPLOYEES = "INSERT INTO employees VALUES (?, ?, ?, ?, ?, " +
            "?, ?)";
    private static final String UPDATE_EMPLOYEE = "UPDATE employees SET first_name =?, last_name =?, password =?, " +
            "status =?, is_waiting =? WHERE id = ?;";

    public EmployeeDAO(final Connection connection)
    {
        super(connection, "employees");
    }

    private static EmployeeStatus parseToEmployeeStatus(final String arg)
    {
        EmployeeStatus employeeStatus = null;
        switch (arg.toUpperCase())
        {
            case "WORKING":
                employeeStatus = EmployeeStatus.WORKING;
                break;
            case "ON_VACATION":
                employeeStatus = EmployeeStatus.ON_VACATION;
                break;
            case "RETIRED":
                employeeStatus = EmployeeStatus.RETIRED;
                break;
        }
        return employeeStatus;
    }

    @Override
    public List<Employee> findAll() throws SQLException
    {
        List<Employee> employees = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_EMPLOYEES))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                Employee employee = new Employee();
                employee.setId(parseToBigInteger(resultSet.getLong("id")));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setLogin(resultSet.getString("login"));
                employee.setPassword(resultSet.getString("password"));
                employee.setEmployeeStatus(parseToEmployeeStatus(resultSet.getString("status")));
                employee.setWaitingForOrders(resultSet.getBoolean("is_waiting"));
                employees.add(employee);
            }
        }

        return employees;
    }

    @Override
    public Employee findById(final BigInteger id) throws SQLException
    {
        Employee employee = new Employee();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMPLOYEE_BY_ID))
        {
            preparedStatement.setLong(1, parseToLong(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                employee.setId(parseToBigInteger(resultSet.getLong("id")));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setLogin(resultSet.getString("login"));
                employee.setPassword(resultSet.getString("password"));
                employee.setEmployeeStatus(parseToEmployeeStatus(resultSet.getString("status")));
                employee.setWaitingForOrders(resultSet.getBoolean("is_waiting"));
            }
        }

        return employee;
    }

    @Override
    public void create(final Employee entity) throws SQLException, DataNotCreatedWarning
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_EMPLOYEES))
        {
            preparedStatement.setLong(1, parseToLong(entity.getId()));
            preparedStatement.setString(2, entity.getFirstName());
            preparedStatement.setString(3, entity.getLastName());
            preparedStatement.setString(4, entity.getLogin());
            preparedStatement.setString(5, entity.getPassword());
            preparedStatement.setString(6, entity.getEmployeeStatus().toString());
            preparedStatement.setBoolean(7, entity.isWaitingForOrders());
            boolean isObjectNotCreated = preparedStatement.executeUpdate() == 0;
            if (isObjectNotCreated)
            {
                throw new DataNotCreatedWarning("Object wasn't created");
            }
        }
    }

    @Override
    public void update(final Employee entity) throws SQLException, DataNotUpdatedWarning
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EMPLOYEE))
        {
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setString(3, entity.getPassword());
            preparedStatement.setString(4, entity.getEmployeeStatus().toString());
            preparedStatement.setBoolean(5, entity.isWaitingForOrders());
            preparedStatement.setLong(6, parseToLong(entity.getId()));
            boolean isObjectNotUpdated = preparedStatement.executeUpdate() == 0;
            if (isObjectNotUpdated)
            {
                throw new DataNotUpdatedWarning("Object wasn't updated");
            }
        }
    }
}
