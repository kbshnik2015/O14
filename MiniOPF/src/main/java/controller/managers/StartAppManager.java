package controller.managers;

import controller.exceptions.ObjectNotFoundException;
import model.Model;
import model.ModelFactory;

public class StartAppManager
{
    public static void startApp() throws ObjectNotFoundException
    {
        Model model = ModelFactory.getModel();

        Thread workWaitersThread = new Thread(WorkWaitersManager::distributeOrdersBackground);
        workWaitersThread.setDaemon(true);
        PayDayManager payDayManager = new PayDayManager();
        payDayManager.setDaemon(true);

        workWaitersThread.start();
        payDayManager.start();
    }
}
