package eu.dmpr.kn.demo.exception;

import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;

import eu.dmpr.kn.demo.utils.Log;

public class ApplicationErrorListener implements
        ApplicationListener<ApplicationFailedEvent> {

    @Override
    public void onApplicationEvent(ApplicationFailedEvent event) {
        if (event.getException() != null) {
            Log.error("!!!!!!Looks like something not working as expected so application is stopping!!!!!!", event);
            event.getApplicationContext().close();
            System.exit(-1);
        }
    }
}