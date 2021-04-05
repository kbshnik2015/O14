package model;

import java.sql.SQLException;

import controller.exceptions.ObjectNotFoundException;
import lombok.Getter;
import lombok.Setter;

public class ModelFactory
{
    @Setter
    @Getter
    private static String currentModel = "modeldb";
    //    private static String currentModel = "modeljson";

    public static Model getModel() throws ObjectNotFoundException
    {
        if ("modeljson".equalsIgnoreCase(currentModel))
        {
            return ModelJson.getInstance();
        }
        else if ("modeldb".equalsIgnoreCase(currentModel))
        {
            try
            {
                return new ModelDB();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        throw new ObjectNotFoundException("The model wasn't found");
    }
}
