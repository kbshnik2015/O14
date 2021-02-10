package controller.managers;

import java.io.IOException;

import model.Model;

public class StartAppManager
{
    public void startApp() throws IOException
    {
        Model.getInstance();

        Thread workWaitersThread = new Thread(
                () -> WorkWaitersManager.distributeOrdersBackground());
        workWaitersThread.setDaemon(true);
        PayDayManager payDayManager = new PayDayManager();
        payDayManager.setDaemon(true);

        workWaitersThread.start();
        payDayManager.start();
    }
}
