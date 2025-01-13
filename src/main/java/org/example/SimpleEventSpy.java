package org.example;

import org.apache.maven.eventspy.EventSpy;
import org.eclipse.aether.RepositoryEvent;
import org.eclipse.aether.RequestTrace;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.eclipse.aether.RepositoryEvent.EventType.ARTIFACT_RESOLVED;
import static org.eclipse.aether.RepositoryEvent.EventType.METADATA_RESOLVED;

@Named("simple")
@Singleton
public class SimpleEventSpy implements EventSpy {
    private final List<RepositoryEvent> events = new ArrayList<>();

    @Override
    public void init(Context context) throws Exception {
        System.out.println("Initializing Simple Event Spy");
    }

    @Override
    public void onEvent(Object o) throws Exception {
        if (o instanceof RepositoryEvent event) {
            if (event.getType() == ARTIFACT_RESOLVED || event.getType() == METADATA_RESOLVED) {
                events.add(event);
            }
        }
    }

    @Override
    public void close() throws Exception {
        System.out.println("Closing Simple Event Spy");
        var str = events.stream().map(e -> e.getArtifact() + "|" +  rootData(e.getTrace())).collect(Collectors.joining("\n"));
        System.out.println(str);
    }

    private static String rootData(RequestTrace requestTrace) {
        requestTrace = root(requestTrace);
        if (requestTrace == null) {
            return null;
        }
        return requestTrace.getData().getClass().getSimpleName();
    }

    private static RequestTrace root(RequestTrace requestTrace) {
        if (requestTrace == null) {
            return null;
        }
        RequestTrace parent = requestTrace.getParent();
        while (parent != null) {
            requestTrace = parent;
            parent = requestTrace.getParent();
        }
        return requestTrace;
    }

}
