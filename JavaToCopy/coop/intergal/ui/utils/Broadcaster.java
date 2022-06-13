package coop.intergal.ui.utils;

import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import com.vaadin.flow.shared.Registration;

public class Broadcaster {
static Executor executor = Executors.newSingleThreadExecutor();

static LinkedList<Consumer<String>> listeners = new LinkedList<>();

public static synchronized Registration register(
        Consumer<String> listener) {
    listeners.add(listener);

    return () -> {
        synchronized (Broadcaster.class) {
            listeners.remove(listener);
        }
    };
}

public static synchronized void broadcast(String message) {
    for (Consumer<String> listener : listeners) {
        executor.execute(() -> listener.accept(message));
    }
}
}