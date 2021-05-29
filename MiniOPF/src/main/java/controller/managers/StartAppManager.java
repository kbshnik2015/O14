package controller.managers;

import controller.exceptions.ObjectNotFoundException;

public class StartAppManager
{
    public static void startApp() throws ObjectNotFoundException
    {
        Thread workWaitersThread = new Thread(WorkWaitersManager::distributeOrdersBackground);
        workWaitersThread.setDaemon(true);
        PayDayManager payDayManager = new PayDayManager();
        payDayManager.setDaemon(true);

        workWaitersThread.start();
        payDayManager.start();
    }
}
