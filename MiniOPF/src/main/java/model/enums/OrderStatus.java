package model.enums;

public enum OrderStatus
{
    IN_PROGRESS("IN_PROGRESS"), SUSPENDED("SUSPENDED"), COMPLETED("COMPLETED"), ENTERING("ENTERING"), CANCELLED("CANCELLED");

    private String string;

    OrderStatus(String name){string = name;}

    @Override
    public String toString() {
        return string;
    }
}
