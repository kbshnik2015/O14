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
//        private static String currentModel = "modeljson";

    private static Model model;

    public static Model getModel() throws ObjectNotFoundException
    {
        if ("modeljson".equalsIgnoreCase(currentModel))
        {
            return ModelJson.getInstance();
        }
        else if ("modeldb".equalsIgnoreCase(currentModel))
        {
            if (model == null)
            {
                try
                {
                    model = new ModelDB();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            return model;
        }
        throw new ObjectNotFoundException("The model wasn't found");
    }
}
