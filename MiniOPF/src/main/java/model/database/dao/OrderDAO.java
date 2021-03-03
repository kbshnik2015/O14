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
import model.entities.Order;
import model.enums.OrderAim;
import model.enums.OrderStatus;

public class OrderDAO extends AbstractDAO<Order>
{
    private static final String SELECT_ALL_ORDERS = "SELECT * FROM orders ORDER BY id_order";
    private static final String SELECT_ORDER_BY_ID = "SELECT * FROM orders WHERE id_order =?";
    private static final String DELETE_ORDER = "DELETE FROM orders WHERE id_order = ?;";
    private static final String INSERT_INTO_ORDERS = "INSERT INTO orders VALUES (nextval('ServiceSeq'), ?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_INTO_ORDERS_WITHOUT_EMPLOYEE = "INSERT INTO orders (id_order, customer_id, specification_id, service_id, aim, status, address) VALUES (nextval('ServiceSeq'), ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_INTO_ORDERS_WITHOUT_SERVICE = "INSERT INTO orders (id_order, customer_id, employee_id, specification_id, aim, status, address) VALUES (nextval('ServiceSeq'), ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_INTO_ORDERS_WITHOUT_EMPLOYEE_AND_SERVICE = "INSERT INTO orders (id_order, customer_id, specification_id, aim, status, address) VALUES (nextval('ServiceSeq'), ?, ?, ?, ?, ?)";
    private static final String UPDATE_ORDER = "UPDATE orders SET customer_id =?, employee_id =?, specification_id =?, " +
                    "service_id =?, aim =?, status =?, address =? WHERE id_order = ?;";
    private static final String UPDATE_ORDER_WITHOUT_EMPLOYEE = "UPDATE orders SET customer_id =?, employee_id = default, specification_id =?, service_id =?, aim =?, status =?, address =? WHERE id_order = ?;";
    private static final String UPDATE_ORDER_WITHOUT_SERVICE  = "UPDATE orders SET customer_id =?, employee_id =?, specification_id =?, service_id = default, aim =?, status =?, address =? WHERE id_order = ?;";
    private static final String UPDATE_ORDER_WITHOUT_EMPLOYEE_AND_SERVICE = "UPDATE orders SET customer_id =?, employee_id = default, specification_id =?, service_id = default, aim =?, status =?, address =? WHERE id_order = ?;";

    public OrderDAO(final Connection connection)
    {
        super(connection);
        this.tableName = "orders";
    }

    private static OrderStatus parseToOrderStatus(final String arg)
    {
        OrderStatus orderStatus = null;
        switch (arg.toUpperCase())
        {
            case "IN_PROGRESS":
                orderStatus = OrderStatus.IN_PROGRESS;
                break;
            case "SUSPENDED":
                orderStatus = OrderStatus.SUSPENDED;
                break;
            case "COMPLETED":
                orderStatus = OrderStatus.COMPLETED;
                break;
            case "ENTERING":
                orderStatus = OrderStatus.ENTERING;
                break;
            case "CANCELLED":
                orderStatus = OrderStatus.CANCELLED;
                break;
        }
        return orderStatus;
    }

    private static OrderAim parseToOrderAim(final String arg)
    {
        OrderAim orderAim = null;
        switch (arg.toUpperCase())
        {
            case "NEW":
                orderAim = OrderAim.NEW;
                break;
            case "SUSPEND":
                orderAim = OrderAim.SUSPEND;
                break;
            case "RESTORE":
                orderAim = OrderAim.RESTORE;
                break;
            case "DISCONNECT":
                orderAim = OrderAim.DISCONNECT;
                break;
        }
        return orderAim;
    }

    @Override
    public List<Order> findAll() throws SQLException
    {
        List<Order> orders = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ORDERS))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                BigInteger id_order = parseToBigInteger(resultSet.getLong("id_order"));
                BigInteger customer_id = parseToBigInteger(resultSet.getLong("customer_id"));
                BigInteger employee_id = parseToBigInteger(resultSet.getLong("employee_id"));
                if (BigInteger.valueOf(0).equals(employee_id))
                {
                    employee_id = null;
                }
                BigInteger specification_id = parseToBigInteger(resultSet.getLong("specification_id"));
                BigInteger service_id = parseToBigInteger(resultSet.getLong("service_id"));
                if (BigInteger.valueOf(0).equals(service_id))
                {
                    service_id = null;
                }
                OrderAim orderAim = parseToOrderAim(resultSet.getString("aim"));
                OrderStatus orderStatus = parseToOrderStatus(resultSet.getString("status"));
                String address = resultSet.getString("address");
                orders.add(new Order(id_order, customer_id, employee_id, specification_id, service_id, orderAim,
                        orderStatus, address));
            }
        }

        return orders;
    }

    @Override
    public Order findById(final BigInteger id) throws SQLException
    {
        Order order = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_BY_ID))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                BigInteger id_order = parseToBigInteger(resultSet.getLong("id_order"));
                BigInteger customer_id = parseToBigInteger(resultSet.getLong("customer_id"));
                BigInteger employee_id = parseToBigInteger(resultSet.getLong("employee_id"));
                if (BigInteger.valueOf(0).equals(employee_id))
                {
                    employee_id = null;
                }
                BigInteger specification_id = parseToBigInteger(resultSet.getLong("specification_id"));
                BigInteger service_id = parseToBigInteger(resultSet.getLong("service_id"));
                if (BigInteger.valueOf(0).equals(service_id))
                {
                    service_id = null;
                }
                OrderAim orderAim = parseToOrderAim(resultSet.getString("aim"));
                OrderStatus orderStatus = parseToOrderStatus(resultSet.getString("status"));
                String address = resultSet.getString("address");
                order = new Order(id_order, customer_id, employee_id, specification_id, service_id, orderAim,
                        orderStatus, address);
            }
        }

        return order;
    }

    @Override
    public void delete(final BigInteger id) throws SQLException, DataNotFoundWarning
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDER))
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
    public void delete(final Order entity) throws SQLException, DataNotFoundWarning
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDER))
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
            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDER))
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
            String warningMessage = "Orders with ids : ";
            for (BigInteger id : notDeletedIds)
            {
                warningMessage = warningMessage.concat(id + ", ");
            }
            warningMessage = warningMessage.concat("weren't deleted");
            throw new DataNotFoundWarning(warningMessage);
        }
    }

    @Override
    public void create(final Order entity) throws SQLException, DataNotCreatedWarning
    {
        boolean isObjectNotCreated = false;
        if(entity.getEmployeeId() != null && entity.getServiceId() != null)
        {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_ORDERS))
            {
                preparedStatement.setLong(1, parseToLong(entity.getCustomerId()));
                preparedStatement.setLong(2, parseToLong(entity.getEmployeeId()));
                preparedStatement.setLong(3, parseToLong(entity.getSpecId()));
                preparedStatement.setLong(4, parseToLong(entity.getServiceId()));
                preparedStatement.setString(5, entity.getOrderAim().toString());
                preparedStatement.setString(6, entity.getOrderStatus().toString());
                preparedStatement.setString(7, entity.getAddress());
                isObjectNotCreated = preparedStatement.executeUpdate() == 0;
            }
        }
        else if (entity.getEmployeeId() == null && entity.getServiceId() != null)
        {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_ORDERS_WITHOUT_EMPLOYEE))
            {
                preparedStatement.setLong(1, parseToLong(entity.getCustomerId()));
                preparedStatement.setLong(2, parseToLong(entity.getSpecId()));
                preparedStatement.setLong(3, parseToLong(entity.getServiceId()));
                preparedStatement.setString(4, entity.getOrderAim().toString());
                preparedStatement.setString(5, entity.getOrderStatus().toString());
                preparedStatement.setString(6, entity.getAddress());
                isObjectNotCreated = preparedStatement.executeUpdate() == 0;
            }
        }
        else if (entity.getEmployeeId() != null && entity.getServiceId() == null)
        {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_ORDERS_WITHOUT_SERVICE))
            {
                preparedStatement.setLong(1, parseToLong(entity.getCustomerId()));
                preparedStatement.setLong(2, parseToLong(entity.getEmployeeId()));
                preparedStatement.setLong(3, parseToLong(entity.getSpecId()));
                preparedStatement.setString(4, entity.getOrderAim().toString());
                preparedStatement.setString(5, entity.getOrderStatus().toString());
                preparedStatement.setString(6, entity.getAddress());
                isObjectNotCreated = preparedStatement.executeUpdate() == 0;
            }
        }
        else {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    INSERT_INTO_ORDERS_WITHOUT_EMPLOYEE_AND_SERVICE))
            {
                preparedStatement.setLong(1, parseToLong(entity.getCustomerId()));
                preparedStatement.setLong(2, parseToLong(entity.getSpecId()));
                preparedStatement.setString(3, entity.getOrderAim().toString());
                preparedStatement.setString(4, entity.getOrderStatus().toString());
                preparedStatement.setString(5, entity.getAddress());
                isObjectNotCreated = preparedStatement.executeUpdate() == 0;
            }
        }
        if (isObjectNotCreated)
        {
            throw new DataNotCreatedWarning("Object wasn't created");
        }
    }

    @Override
    public void update(final Order entity) throws SQLException, DataNotUpdatedWarning
    {
        boolean isObjectNotUpdated = false;
        if (entity.getEmployeeId() != null && entity.getServiceId() != null)
        {
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER))
            {
                preparedStatement.setLong(1, parseToLong(entity.getCustomerId()));
                preparedStatement.setLong(2, parseToLong(entity.getEmployeeId()));
                preparedStatement.setLong(3, parseToLong(entity.getSpecId()));
                preparedStatement.setLong(4, parseToLong(entity.getServiceId()));
                preparedStatement.setString(5, entity.getOrderAim().toString());
                preparedStatement.setString(6, entity.getOrderStatus().toString());
                preparedStatement.setString(7, entity.getAddress());
                preparedStatement.setLong(8, parseToLong(entity.getId()));
                isObjectNotUpdated = preparedStatement.executeUpdate() == 0;
            }
        }
        else if (entity.getEmployeeId() == null && entity.getServiceId() != null)
        {
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_WITHOUT_EMPLOYEE))
            {
                preparedStatement.setLong(1, parseToLong(entity.getCustomerId()));
                preparedStatement.setLong(2, parseToLong(entity.getSpecId()));
                preparedStatement.setLong(3, parseToLong(entity.getServiceId()));
                preparedStatement.setString(4, entity.getOrderAim().toString());
                preparedStatement.setString(5, entity.getOrderStatus().toString());
                preparedStatement.setString(6, entity.getAddress());
                preparedStatement.setLong(7, parseToLong(entity.getId()));
                isObjectNotUpdated = preparedStatement.executeUpdate() == 0;
            }
        }
        else if (entity.getEmployeeId() != null && entity.getServiceId() == null)
        {
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_WITHOUT_SERVICE))
            {
                preparedStatement.setLong(1, parseToLong(entity.getCustomerId()));
                preparedStatement.setLong(2, parseToLong(entity.getEmployeeId()));
                preparedStatement.setLong(3, parseToLong(entity.getSpecId()));
                preparedStatement.setString(4, entity.getOrderAim().toString());
                preparedStatement.setString(5, entity.getOrderStatus().toString());
                preparedStatement.setString(6, entity.getAddress());
                preparedStatement.setLong(7, parseToLong(entity.getId()));
                isObjectNotUpdated = preparedStatement.executeUpdate() == 0;
            }
        }
        else
        {
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_WITHOUT_EMPLOYEE_AND_SERVICE))
            {
                preparedStatement.setLong(1, parseToLong(entity.getCustomerId()));
                preparedStatement.setLong(2, parseToLong(entity.getSpecId()));
                preparedStatement.setString(3, entity.getOrderAim().toString());
                preparedStatement.setString(4, entity.getOrderStatus().toString());
                preparedStatement.setString(5, entity.getAddress());
                preparedStatement.setLong(6, parseToLong(entity.getId()));
                isObjectNotUpdated = preparedStatement.executeUpdate() == 0;
            }
        }
        if (isObjectNotUpdated)
        {
            throw new DataNotUpdatedWarning("Object wasn't updated");
        }
    }
}
