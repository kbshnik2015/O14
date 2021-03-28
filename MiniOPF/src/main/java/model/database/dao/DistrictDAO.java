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
import model.entities.District;

public class DistrictDAO extends AbstractDAO<District>
{
    private static final String SELECT_ALL_DISTRICTS = "SELECT * FROM districts ORDER BY id";
    private static final String SELECT_DISTRICT_BY_ID = "SELECT * FROM districts WHERE id =?;";
    private static final String INSERT_INTO_DISTRICTS = "INSERT INTO districts VALUES (?, ?, ?)";
    private static final String INSERT_INTO_DISTRICTS_WITHOUT_PARENT =
            "INSERT INTO districts (id_district, name) VALUES (?, ?)";
    private static final String UPDATE_DISTRICT = "UPDATE districts SET name =?, id_parent =? WHERE id = ?;";
    private static final String UPDATE_DISTRICT_WITHOUT_PARENT = "UPDATE districts SET name =?, id_parent = default " +
            "WHERE id = ?;";

    public DistrictDAO(final Connection connection)
    {
        super(connection, "districts");
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
                District district = new District();
                district.setId(parseToBigInteger(resultSet.getLong("id")));
                district.setName(resultSet.getString("name"));
                if (BigInteger.valueOf(0).equals(parseToBigInteger(resultSet.getLong("id_parent"))))
                {
                    district.setParentId(null);
                }
                else
                {
                    district.setParentId(parseToBigInteger(resultSet.getLong("id_parent")));
                }
                districts.add(district);
            }
        }

        return districts;
    }

    @Override
    public District findById(final BigInteger id) throws SQLException
    {
        District district = new District();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_DISTRICT_BY_ID))
        {
            preparedStatement.setLong(1, parseToLong(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                district.setId(parseToBigInteger(resultSet.getLong("id")));
                district.setName(resultSet.getString("name"));
                if (BigInteger.valueOf(0).equals(parseToBigInteger(resultSet.getLong("id_parent"))))
                {
                    district.setParentId(null);
                }
                else
                {
                    district.setParentId(parseToBigInteger(resultSet.getLong("id_parent")));
                }
            }
        }

        return district;
    }

    @Override
    public void create(final District entity) throws SQLException, DataNotCreatedWarning
    {
        boolean isObjectNotCreated;

        if (entity.getParentId() != null)
        {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_DISTRICTS))
            {
                preparedStatement.setLong(1, parseToLong(entity.getId()));
                preparedStatement.setString(2, entity.getName());
                preparedStatement.setLong(3, parseToLong(entity.getParentId()));
                isObjectNotCreated = preparedStatement.executeUpdate() == 0;
            }
        }
        else
        {
            try (PreparedStatement preparedStatement = connection
                    .prepareStatement(INSERT_INTO_DISTRICTS_WITHOUT_PARENT))
            {
                preparedStatement.setLong(1, parseToLong(entity.getId()));
                preparedStatement.setString(2, entity.getName());
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
        boolean isObjectNotUpdated;

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
