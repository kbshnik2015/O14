package model.enums;

public enum ServiceStatus
{
    ACTIVE("ACTIVE"), DISCONNECTED("DISCONNECTED"), PAY_MONEY_SUSPENDED("PAY_MONEY_SUSPENDED"), SUSPENDED("SUSPENDED");

    private String string;

    ServiceStatus(String name){string = name;}

    @Override
    public String toString() {
        return string;
    }

}
