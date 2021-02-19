package model.enums;

public enum EmployeeStatus
{
    WORKING("WORKING"), ON_VACATION("ON_VACATION"), RETIRED("RETIRED");

    private String string;

    EmployeeStatus(String name){string = name;}

    @Override
    public String toString() {
        return string;
    }

}
