package controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RegexParser
{
    /**
     * Regex can includes symbols like digits . *
     */
    public static List<BigInteger> filterIds(List<BigInteger> ids, String inputRegex)
    {
        List<BigInteger> filteredIds = new ArrayList<>();

        String tmp = inputRegex.replaceAll("\\*", "(\\\\d*)");
        String regex = tmp.replaceAll("\\.", "(\\\\d)");

        for (BigInteger id : ids)
        {
            boolean matches = id.toString().matches(regex);
            if (matches)
            {
                filteredIds.add(id);
            }
        }

        return filteredIds;
    }
}
