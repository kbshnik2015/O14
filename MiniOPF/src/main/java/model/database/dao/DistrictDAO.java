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
import model.entities.District;

public class DistrictDAO extends AbstractDAO<District>
{
    private static final String SELECT_ALL_DISTRICTS = "SELECT * FROM districts ORDER BY id_district";
    private static final String SELECT_DISTRICT_BY_ID = "SELECT * FROM districts WHERE id_district =?;";
    private static final String DELETE_DISTRICT = "DELETE FROM districts WHERE id_district = ?;";
    private static final String INSERT_INTO_DISTRICTS = "INSERT INTO districts VALUES (nextval('DistrictSeq'), ?, ?)";
    private static final String INSERT_INTO_DISTRICTS_WITHOUT_PARENT = "INSERT INTO districts (id_district, name) VALUES " +
            "(nextval('DistrictSeq'), ?)";
    private static final String UPDATE_DISTRICT = "UPDATE districts SET name =?, id_parent =? WHERE id_district = ?;";
    private static final String UPDATE_DISTRICT_WITHOUT_PARENT = "UPDATE districts SET name =?, id_parent = default WHERE id_district = ?;";

    public DistrictDAO(final Connection connection)
    {
        super(connection);
        this.tableName = "districts";
    }

    @Override
    public List<District> findAll() throws SQLException
    {
        List<District> districts = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_DISTRICTS))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                BigInteger id_district = parseToBigInteger(resultSet.getLong("id_district"));
                String name = resultSet.getString("name");
                BigInteger parentId = parseToBigInteger(resultSet.getLong("id_parent"));
                if (BigInteger.valueOf(0).equals(parentId))
                {
                    parentId = null;
                }
                districts.add(new District(id_district, name, parentId));
            }
        }

        return districts;
    }

    @Override
    public District findById(final BigInteger id) throws SQLException
    {
        District district = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_DISTRICT_BY_ID))
        {
            preparedStatement.setLong(1, parseToLong(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                BigInteger id_district = parseToBigInteger(resultSet.getLong("id_district"));
                String name = resultSet.getString("name");
                BigInteger parentId = parseToBigInteger(resultSet.getLong("id_parent"));
                if (BigInteger.valueOf(0).equals(parentId))
                {
                    parentId = null;
                }
                district = new District(id_district, name, parentId);
            }
        }

        return district;
    }

    @Override
    public void delete(final BigInteger id) throws SQLException, DataNotFoundWarning
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_DISTRICT))
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
    public void delete(final District entity) throws SQLException, DataNotFoundWarning
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_DISTRICT))
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
            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_DISTRICT))
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
            String warningMessage = "Districts with ids : ";
            for (BigInteger id : notDeletedIds)
            {
                warningMessage = warningMessage.concat(id + ", ");
            }
            warningMessage = warningMessage.concat("weren't deleted");
            throw new DataNotFoundWarning(warningMessage);
        }
    }

    @Override
    public void create(final District entity) throws SQLException, DataNotCreatedWarning
    {
        boolean isObjectNotCreated = false;
        if (entity.getParentId() != null)
        {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_DISTRICTS))
            {
                preparedStatement.setString(1, entity.getName());
                preparedStatement.setLong(2, parseToLong(entity.getParentId()));
                isObjectNotCreated = preparedStatement.executeUpdate() == 0;
            }
        }
        else
        {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_DISTRICTS_WITHOUT_PARENT))
            {
                preparedStatement.setString(1, entity.getName());
                isObjectNotCreated = preparedStatement.executeUpdate() == 0;
            }
        }
        if (isObjectNotCreated)
        {
            throw new DataNotCreatedWarning("Object wasn't created");
        }
    }

    @Override
    public void update(final District entity) throws SQLException, DataNotUpdatedWarning
    {
        boolean isObjectNotUpdated = false;
        if (entity.getParentId() != null)
        {
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DISTRICT))
            {
                preparedStatement.setString(1, entity.getName());
                preparedStatement.setLong(2, parseToLong(entity.getParentId()));
                preparedStatement.setLong(3, parseToLong(entity.getId()));
                isObjectNotUpdated = preparedStatement.executeUpdate() == 0;
            }
        }
        else
        {
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DISTRICT_WITHOUT_PARENT))
            {
                preparedStatement.setString(1, entity.getName());
                preparedStatement.setLong(2, parseToLong(entity.getId()));
                isObjectNotUpdated = preparedStatement.executeUpdate() == 0;
            }
        }
        if (isObjectNotUpdated)
        {
            throw new DataNotUpdatedWarning("Object wasn't updated");
        }
    }
}
