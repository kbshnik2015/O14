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
import model.entities.Specification;

public class SpecificationDAO extends AbstractDAO<Specification>
{
    private static final String SELECT_ALL_SPECIFICATIONS = "SELECT * FROM specifications ORDER BY id_specification";
    private static final String SELECT_SPECIFICATION_BY_ID = "SELECT * FROM specifications WHERE id_specification =?";
    private static final String SELECT_SPECIFICATION_ID_BY_UNIQUE = "SELECT id_specification FROM specifications WHERE " +
            "name =? AND price =? AND description =? AND is_address_depended =?";
    private static final String DELETE_SPECIFICATION = "DELETE FROM specifications WHERE id_specification = ?;";
    //todo: INSERT INTO SPECIFICATIONS everywhere
    private static final String INSERT_INTO_SPECIFICATIONS = "INSERT INTO specifications VALUES (nextval" +
            "('SpecificationSeq'), ?, ?, ?, ?)";
    private static final String UPDATE_SPECIFICATION = "UPDATE specifications SET name =?, price =?, description =?, " +
            "is_address_depended =? WHERE id_specification = ?;";

    public static final String SELECT_SPECIFICATION_DISTRICTS = "SELECT district_id FROM specifications_to_districts " +
            "WHERE specification_id =?;";
    public static final String DELETE_SPECIFICATION_DISTRICTS = "DELETE FROM specifications_to_districts WHERE " +
            "specification_id =?;";
    public static final String INSERT_INTO_SPECIFICATIONS_TO_DISTRICTS = "INSERT INTO specifications_to_districts VALUES (?, ?)";

    public SpecificationDAO(final Connection connection)
    {
        super(connection);
        this.tableName = "specifications";
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
                BigInteger district_id = parseToBigInteger(resultSet.getLong("district_id"));
                districtsIds.add(district_id);
            }
        }

        return districtsIds;
    }

    private void insertSpecificationDistricts(Specification entity) throws SQLException
    {
        if (entity.getDistrictsIds().size() > 0)
        {
            BigInteger specificationId = null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SPECIFICATION_ID_BY_UNIQUE))
            {
                preparedStatement.setString(1, entity.getName());
                preparedStatement.setFloat(2, entity.getPrice());
                preparedStatement.setString(3, entity.getDescription());
                preparedStatement.setBoolean(4, entity.isAddressDepended());
                ResultSet resultSet = preparedStatement.getResultSet();
                while (resultSet.next())
                {
                    specificationId = parseToBigInteger(resultSet.getLong("id_specification"));
                }
            }
            for (BigInteger districtId : entity.getDistrictsIds())
            {
                try (PreparedStatement preparedStatement = connection.prepareStatement(
                        INSERT_INTO_SPECIFICATIONS_TO_DISTRICTS))
                {
                    preparedStatement.setLong(1, parseToLong(specificationId));
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
                BigInteger id_specification = parseToBigInteger(resultSet.getLong("id_specification"));
                String name = resultSet.getString("name");
                float price = resultSet.getFloat("price");
                String description = resultSet.getString("description");
                boolean isAddressDepended = resultSet.getBoolean("is_address_depended");
                List<BigInteger> districtsIds = findSpecificationDistricts(id_specification);
                specifications.add(new Specification(id_specification, name, price, description, isAddressDepended,
                        districtsIds));
            }
        }

        return specifications;
    }

    @Override
    public Specification findById(final BigInteger id) throws SQLException
    {
        Specification specification = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SPECIFICATION_BY_ID))
        {
            preparedStatement.setLong(1, parseToLong(id));
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next())
            {
                BigInteger id_specification = parseToBigInteger(resultSet.getLong("id_specification"));
                String name = resultSet.getString("name");
                float price = resultSet.getFloat("price");
                String description = resultSet.getString("description");
                boolean isAddressDepended = resultSet.getBoolean("is_address_depended");
                List<BigInteger> districtsIds = findSpecificationDistricts(id_specification);
                specification = new Specification(id_specification, name, price, description, isAddressDepended,
                        districtsIds);
            }

        }

        return specification;
    }

    @Override
    public void delete(final BigInteger id) throws SQLException, DataNotFoundWarning
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SPECIFICATION))
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
    public void delete(final Specification entity) throws SQLException, DataNotFoundWarning
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SPECIFICATION))
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
            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SPECIFICATION))
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
    public void create(final Specification entity) throws SQLException, DataNotCreatedWarning
    {
        //todo: дернуть nextId из БД и присвоеть entity;
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_SPECIFICATIONS))
        {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setFloat(2, entity.getPrice());
            preparedStatement.setString(3, entity.getDescription());
            preparedStatement.setBoolean(4, entity.isAddressDepended());
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
            preparedStatement.setBoolean(4, entity.isAddressDepended());
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


//    public static final String INSERT_INTO_SPECIFICATIONS_TO_DISTRICTS = "INSERT INTO specifications_to_districts VALUES " +
//            "(id_spec = (SELECT id_specification FROM specifications WHERE name =?, price =?, description =?, " +
//            "is_address_depended =?), ?)";
//
//    private void insertSpecificationDistricts(Specification entity) throws SQLException
//    {
//        if (entity.getDistrictsIds().size() > 0)
//        {
//            for (BigInteger districtId : entity.getDistrictsIds())
//            {
//                try (PreparedStatement preparedStatement = connection.prepareStatement
// (INSERT_INTO_SPECIFICATIONS_TO_DISTRICTS))
//                {
//                    preparedStatement.setString(1, entity.getName());
//                    preparedStatement.setFloat(2, entity.getPrice());
//                    preparedStatement.setString(3, entity.getDescription());
//                    preparedStatement.setBoolean(4, entity.isAddressDepended());
//                    preparedStatement.setLong(5, parseToLong(districtId));
//                    preparedStatement.executeUpdate();
//                }
//            }
//        }
//    }