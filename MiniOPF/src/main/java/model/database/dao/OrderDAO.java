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
import model.entities.Order;
import model.enums.OrderAim;
import model.enums.OrderStatus;

public class OrderDAO extends AbstractDAO<Order>
{
    private static final String SELECT_ALL_ORDERS = "SELECT * FROM orders ORDER BY id";
    private static final String SELECT_ORDER_BY_ID = "SELECT * FROM orders WHERE id =?";
    private static final String INSERT_INTO_ORDERS = "INSERT INTO orders VALUES (nextval('idSeq'), ?, ?, ?, ?, ?, ?, " +
            "?)";
    private static final String INSERT_INTO_ORDERS_WITHOUT_EMPLOYEE = "INSERT INTO orders (id, customer_id, " +
            "specification_id, service_id, aim, status, address) VALUES (nextval('idSeq'), ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_INTO_ORDERS_WITHOUT_SERVICE = "INSERT INTO orders (id, customer_id, " +
            "employee_id, specification_id, aim, status, address) VALUES (nextval('idSeq'), ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_INTO_ORDERS_WITHOUT_EMPLOYEE_AND_SERVICE = "INSERT INTO orders (id, " +
            "customer_id, specification_id, aim, status, address) VALUES (nextval('idSeq'), ?, ?, ?, ?, ?)";
    private static final String UPDATE_ORDER =
            "UPDATE orders SET customer_id =?, employee_id =?, specification_id =?, " +
                    "service_id =?, aim =?, status =?, address =? WHERE id = ?;";
    private static final String UPDATE_ORDER_WITHOUT_EMPLOYEE = "UPDATE orders SET customer_id =?, employee_id = " +
            "default, specification_id =?, service_id =?, aim =?, status =?, address =? WHERE id = ?;";
    private static final String UPDATE_ORDER_WITHOUT_SERVICE = "UPDATE orders SET customer_id =?, employee_id =?, " +
            "specification_id =?, service_id = default, aim =?, status =?, address =? WHERE id = ?;";
    private static final String UPDATE_ORDER_WITHOUT_EMPLOYEE_AND_SERVICE = "UPDATE orders SET customer_id =?, " +
            "employee_id = default, specification_id =?, service_id = default, aim =?, status =?, address =? WHERE id" +
            " = ?;";

    public OrderDAO(final Connection connection)
    {
        super(connection, "orders");
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
                Order order = new Order();
                order.setId(parseToBigInteger(resultSet.getLong("id")));
                order.setCustomerId(parseToBigInteger(resultSet.getLong("customer_id")));
                if (BigInteger.valueOf(0).equals(parseToBigInteger(resultSet.getLong("employee_id"))))
                {
                    order.setEmployeeId(null);
                }
                else
                {
                    order.setEmployeeId(parseToBigInteger(resultSet.getLong("employee_id")));
                }
                order.setSpecId(parseToBigInteger(resultSet.getLong("specification_id")));
                if (BigInteger.valueOf(0).equals(parseToBigInteger(resultSet.getLong("service_id"))))
                {
                    order.setServiceId(null);
                }
                else
                {
                    order.setServiceId(parseToBigInteger(resultSet.getLong("service_id")));
                }
                order.setOrderAim(parseToOrderAim(resultSet.getString("aim")));
                order.setOrderStatus(parseToOrderStatus(resultSet.getString("status")));
                order.setAddress(resultSet.getString("address"));
                orders.add(order);
            }
        }

        return orders;
    }

    @Override
    public Order findById(final BigInteger id) throws SQLException
    {
        Order order = new Order();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_BY_ID))
        {
            preparedStatement.setLong(1, parseToLong(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                order.setId(parseToBigInteger(resultSet.getLong("id")));
                order.setCustomerId(parseToBigInteger(resultSet.getLong("customer_id")));
                if (BigInteger.valueOf(0).equals(parseToBigInteger(resultSet.getLong("employee_id"))))
                {
                    order.setEmployeeId(null);
                }
                else
                {
                    order.setEmployeeId(parseToBigInteger(resultSet.getLong("employee_id")));
                }
                order.setSpecId(parseToBigInteger(resultSet.getLong("specification_id")));
                if (BigInteger.valueOf(0).equals(parseToBigInteger(resultSet.getLong("service_id"))))
                {
                    order.setServiceId(null);
                }
                else
                {
                    order.setServiceId(parseToBigInteger(resultSet.getLong("service_id")));
                }
                order.setOrderAim(parseToOrderAim(resultSet.getString("aim")));
                order.setOrderStatus(parseToOrderStatus(resultSet.getString("status")));
                order.setAddress(resultSet.getString("address"));
            }
        }

        return order;
    }

    @Override
    public void create(final Order entity) throws SQLException, DataNotCreatedWarning
    {
        boolean isObjectNotCreated;

        if (entity.getEmployeeId() != null && entity.getServiceId() != null)
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
        else
        {
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
        boolean isObjectNotUpdated;

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
            try (PreparedStatement preparedStatement = connection
                    .prepareStatement(UPDATE_ORDER_WITHOUT_EMPLOYEE_AND_SERVICE))
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
