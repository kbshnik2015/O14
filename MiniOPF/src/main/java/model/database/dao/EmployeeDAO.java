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
import model.entities.Employee;
import model.enums.EmployeeStatus;

public class EmployeeDAO extends AbstractDAO<Employee>
{
    private static final String SELECT_ALL_EMPLOYEES = "SELECT * FROM employees ORDER BY id_employee";
    private static final String SELECT_EMPLOYEE_BY_ID = "SELECT * FROM employees WHERE id_employee =?";
    private static final String DELETE_EMPLOYEE = "DELETE FROM employees WHERE id_employee = ?;";
    private static final String INSERT_INTO_EMPLOYEES = "INSERT INTO employees VALUES (nextval('EmployeeSeq'), ?, ?, ?, ?, " +
            "?, ?)";
    private static final String UPDATE_EMPLOYEE = "UPDATE employees SET first_name =?, last_name =?, password =?, " +
            "status =?, is_waiting =? WHERE id_employee = ?;";

    public EmployeeDAO(final Connection connection)
    {
        super(connection);
        this.tableName = "employees";
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
                BigInteger id_employee = parseToBigInteger(resultSet.getLong("id_employee"));
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                EmployeeStatus status = parseToEmployeeStatus(resultSet.getString("status"));
                boolean isWaiting = resultSet.getBoolean("is_waiting");
                employees.add(new Employee(id_employee, firstName, lastName, login, password, status, isWaiting));
            }
        }

        return employees;
    }

    @Override
    public Employee findById(final BigInteger id) throws SQLException
    {
        Employee employee = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMPLOYEE_BY_ID))
        {
            preparedStatement.setLong(1, parseToLong(id));
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next())
            {
                BigInteger id_employee = parseToBigInteger(resultSet.getLong("id_employee"));
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                EmployeeStatus status = parseToEmployeeStatus(resultSet.getString("status"));
                boolean isWaiting = resultSet.getBoolean("is_waiting");
                employee = new Employee(id_employee, firstName, lastName, login, password, status, isWaiting);
            }

        }

        return employee;
    }

    @Override
    public void delete(final BigInteger id) throws SQLException, DataNotFoundWarning
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EMPLOYEE))
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
    public void delete(final Employee entity) throws SQLException, DataNotFoundWarning
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EMPLOYEE))
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
    public void delete(final List<BigInteger> ids) throws DataNotFoundWarning, SQLException
    {
        List<BigInteger> notDeletedIds = new ArrayList<>();
        for (BigInteger id : ids)
        {
            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EMPLOYEE))
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
            String warningMessage = "Employees with ids : ";
            for (BigInteger id : notDeletedIds)
            {
                warningMessage = warningMessage.concat(id + ", ");
            }
            warningMessage = warningMessage.concat("weren't deleted");
            throw new DataNotFoundWarning(warningMessage);
        }
    }

    @Override
    public void create(final Employee entity) throws SQLException, DataNotCreatedWarning
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_EMPLOYEES))
        {
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setString(3, entity.getLogin());
            preparedStatement.setString(4, entity.getPassword());
            preparedStatement.setString(5, entity.getEmployeeStatus().toString());
            preparedStatement.setBoolean(6, entity.isWaitingForOrders());
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
