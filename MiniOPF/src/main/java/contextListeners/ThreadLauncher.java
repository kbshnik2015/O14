package contextListeners;
import controller.managers.StartAppManager;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ThreadLauncher implements ServletContextListener
{
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        StartAppManager.startApp();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
