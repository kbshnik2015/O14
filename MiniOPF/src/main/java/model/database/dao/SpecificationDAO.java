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
import model.entities.Specification;

public class SpecificationDAO extends AbstractDAO<Specification>
{
    private static final String SELECT_ALL_SPECIFICATIONS = "SELECT * FROM specifications ORDER BY id";
    private static final String SELECT_SPECIFICATION_BY_ID = "SELECT * FROM specifications WHERE id =?";
    private static final String INSERT_INTO_SPECIFICATIONS = "INSERT INTO specifications VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_SPECIFICATION = "UPDATE specifications SET name =?, price =?, description =?, " +
            "is_address_depended =? WHERE id = ?;";

    public static final String SELECT_SPECIFICATION_DISTRICTS = "SELECT district_id FROM specifications_to_districts " +
            "WHERE specification_id =?;";
    public static final String DELETE_SPECIFICATION_DISTRICTS = "DELETE FROM specifications_to_districts WHERE " +
            "specification_id =?;";
    public static final String INSERT_INTO_SPECIFICATIONS_TO_DISTRICTS = "INSERT INTO specifications_to_districts " +
            "VALUES (?, ?)";

    public SpecificationDAO(final Connection connection)
    {
        super(connection, "specifications");
    }

    private List<BigInteger> findSpecificationDistricts(BigInteger id) throws SQLException
    {
        List<BigInteger> districtsIds = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SPECIFICATION_DISTRICTS))
        {
            preparedStatement.setLong(1, parseToLong(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                districtsIds.add(parseToBigInteger(resultSet.getLong("district_id")));
            }
        }

        return districtsIds;
    }

    private void insertSpecificationDistricts(Specification entity) throws SQLException
    {
        if (entity.getDistrictsIds().size() > 0)
        {
            for (BigInteger districtId : entity.getDistrictsIds())
            {
                try (PreparedStatement preparedStatement = connection.prepareStatement(
                        INSERT_INTO_SPECIFICATIONS_TO_DISTRICTS))
                {
                    preparedStatement.setLong(1, parseToLong(entity.getId()));
                    preparedStatement.setLong(2, parseToLong(districtId));
                    preparedStatement.executeUpdate();
                }
            }
        }
    }

    private void updateSpecificationDistricts(Specification entity) throws SQLException
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SPECIFICATION_DISTRICTS))
        {
            preparedStatement.setLong(1, parseToLong(entity.getId()));
            preparedStatement.executeUpdate();
        }
        insertSpecificationDistricts(entity);
    }

    @Override
    public List<Specification> findAll() throws SQLException
    {
        List<Specification> specifications = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SPECIFICATIONS))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                Specification specification = new Specification();
                specification.setId(parseToBigInteger(resultSet.getLong("id")));
                specification.setName(resultSet.getString("name"));
                specification.setPrice(resultSet.getFloat("price"));
                specification.setDescription(resultSet.getString("description"));
                specification.setAddressDependence(resultSet.getBoolean("is_address_depended"));
                specification.setDistrictsIds(findSpecificationDistricts(specification.getId()));
                specifications.add(specification);
            }
        }

        return specifications;
    }

    @Override
    public Specification findById(final BigInteger id) throws SQLException
    {
        Specification specification = new Specification();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SPECIFICATION_BY_ID))
        {
            preparedStatement.setLong(1, parseToLong(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                specification.setId(parseToBigInteger(resultSet.getLong("id")));
                specification.setName(resultSet.getString("name"));
                specification.setPrice(resultSet.getFloat("price"));
                specification.setDescription(resultSet.getString("description"));
                specification.setAddressDependence(resultSet.getBoolean("is_address_depended"));
                specification.setDistrictsIds(findSpecificationDistricts(specification.getId()));
            }
        }

        return specification;
    }

    @Override
    public void create(final Specification entity) throws SQLException, DataNotCreatedWarning
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_SPECIFICATIONS))
        {
            preparedStatement.setLong(1, parseToLong(entity.getId()));
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setFloat(3, entity.getPrice());
            preparedStatement.setString(4, entity.getDescription());
            preparedStatement.setBoolean(5, entity.isAddressDependence());
            boolean isObjectNotCreated = preparedStatement.executeUpdate() == 0;
            if (isObjectNotCreated)
            {
                throw new DataNotCreatedWarning("Object wasn't created");
            }
            insertSpecificationDistricts(entity);
        }
    }

    @Override
    public void update(final Specification entity) throws SQLException, DataNotUpdatedWarning
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SPECIFICATION))
        {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setFloat(2, entity.getPrice());
            preparedStatement.setString(3, entity.getDescription());
            preparedStatement.setBoolean(4, entity.isAddressDependence());
            preparedStatement.setLong(5, parseToLong(entity.getId()));
            boolean isObjectNotUpdated = preparedStatement.executeUpdate() == 0;
            if (isObjectNotUpdated)
            {
                throw new DataNotUpdatedWarning("Object wasn't updated");
            }
            updateSpecificationDistricts(entity);
        }
    }
}