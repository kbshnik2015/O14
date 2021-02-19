package model.enums;

public enum OrderAim
{
    NEW("NEW"), SUSPEND("SUSPEND"), RESTORE("RESTORE"), DISCONNECT("DISCONNECT");

    private String string;

    OrderAim(String name){string = name;}

    @Override
    public String toString() {
        return string;
    }
}
