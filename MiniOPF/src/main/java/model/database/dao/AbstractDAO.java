package model.database.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.database.exceptions.DataNotCreatedWarning;
import model.database.exceptions.DataNotFoundWarning;
import model.database.exceptions.DataNotUpdatedWarning;
import model.entities.Entity;

public abstract class AbstractDAO<T extends Entity>
{
    protected Connection connection;

    protected String tableName;

    private static final String DELETE = "DELETE FROM ? WHERE id = ?;";

    public AbstractDAO(Connection connection)
    {
        this.connection = connection;
    }

    protected BigInteger parseToBigInteger(long digit)
    {
        return BigInteger.valueOf(digit);
    }

    protected long parseToLong(BigInteger digit)
    {
        return Long.valueOf(digit.toString());
    }

    public abstract List<T> findAll() throws SQLException;

    public abstract T findById(BigInteger id) throws SQLException;

    //public abstract void delete(BigInteger id) throws SQLException, DataNotFoundWarning;

    public abstract void delete(T entity) throws SQLException, DataNotFoundWarning;

    //public abstract void delete(List<BigInteger> ids) throws SQLException, DataNotFoundWarning;

    public abstract void create(T entity) throws SQLException, DataNotCreatedWarning;

    public abstract void update(T entity) throws SQLException, DataNotUpdatedWarning;

    public void delete(BigInteger id) throws SQLException, DataNotFoundWarning
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE))
        {
            preparedStatement.setString(1, tableName);
            preparedStatement.setLong(2, parseToLong(id));
            boolean isObjectNotFound = preparedStatement.executeUpdate() == 0;
            if (isObjectNotFound)
            {
                throw new DataNotFoundWarning("Object wasn't found for deletion");
            }
        }
    }

    public void delete(List<BigInteger> ids) throws SQLException, DataNotFoundWarning
    {
        List<BigInteger> notDeletedIds = new ArrayList<>();
        for (BigInteger id : ids)
        {
            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE))
            {
                preparedStatement.setString(1, tableName);
                preparedStatement.setLong(2, parseToLong(id));
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

}
