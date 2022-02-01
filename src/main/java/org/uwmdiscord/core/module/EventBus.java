package org.uwmdiscord.core.module;

import net.dv8tion.jda.api.requests.RestAction;
import org.uwmdiscord.core.annotations.Command;
import org.uwmdiscord.core.annotations.CommandHandler;
import org.uwmdiscord.core.annotations.UWMSubscribe;
import org.uwmdiscord.core.logging.Logger;
import org.uwmdiscord.core.publishers.events.UWMCommandReceiveEvent;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.util.*;

public class EventBus {

    private static EventBus instance;

    public static EventBus getInstance() {
        if (instance == null) instance = new EventBus();
        return instance;
    }

    public List<Class<?>> cachedClasses;
    public HashMap<Class<?>, List<Method>> eventMethodPublishees;
    public HashMap<String, List<CommandHandler>> commandPublishees;

    public EventBus() {
        eventMethodPublishees = new HashMap<>();
        commandPublishees = new HashMap<>();
        try {
            this.cachedClasses = Registrar.getAllClasses();
        } catch(URISyntaxException | IOException e) {
            e.printStackTrace();
            Logger.error("Was not able to initialize the cached classes");
            System.exit(0);
        }
        cachedClasses.stream().map(Class::getDeclaredMethods).forEach(methods -> {
            for (Method method : methods) {
                if (method.isAnnotationPresent(UWMSubscribe.class) && method.getParameterCount() == 1) {
                    Class<?> paramType = method.getParameterTypes()[0];
                    List<Method> newList = eventMethodPublishees.getOrDefault(paramType, new ArrayList<>());
                    newList.add(method);
                    eventMethodPublishees.put(paramType, newList);
                    Logger.deepDebug(paramType.getSimpleName() + ": " + method.getName());
                }
            }
        });
        cachedClasses.stream().filter(clazz -> clazz.isAnnotationPresent(Command.class)).forEach(commandClass -> {
            String command = commandClass.getDeclaredAnnotation(Command.class).command();
                commandPublishees.computeIfPresent(command, (key, val) -> {
                    try {
                       val.add((CommandHandler) commandClass.newInstance());
                       Logger.warning("Multiple handlers of the same command exist: " + command);
                    } catch (InstantiationException | IllegalAccessException e) {
                        Logger.warning(commandClass.getSimpleName() + " was not able to be instantiated, needs a default constructor?");
                    }
                    return val;
                });
                commandPublishees.computeIfAbsent(command, (key) -> {
                    try {
                        return new ArrayList<>(Collections.singletonList((CommandHandler)commandClass.newInstance()));
                    } catch (InstantiationException | IllegalAccessException e) {
                        Logger.warning(commandClass.getSimpleName() + " was not able to be instantiated, needs a default constructor?");
                    }
                    return new ArrayList<>();
                });
        });
        eventMethodPublishees.values().forEach(methodList -> methodList.sort(Comparator.comparing(o -> o.getDeclaredAnnotation(UWMSubscribe.class).priority())));
    }

    public void publishCommand(String command, UWMCommandReceiveEvent event) {
        commandPublishees.getOrDefault(command, new ArrayList<>()).forEach(clazz -> clazz.handle(event));
        event.getMessageActions().forEach(RestAction::queue);
    }

    public void publish(Class<?> clazz, Object eventToExecute) {
        if (!eventMethodPublishees.containsKey(clazz)) {
            Logger.warning(clazz.getSimpleName() + " does not have anything to publish to.");
            return;
        }
        List<Method> methods = eventMethodPublishees.get(clazz);
        List<Method> methodsToRemove = new ArrayList<>();
        methods.stream().forEach(method -> {
            try {
                if (Modifier.isStatic(method.getModifiers())) method.invoke(null, eventToExecute);
                else if(Arrays.stream(method.getDeclaringClass().getDeclaredConstructors()).anyMatch(c -> c.getParameterCount() == 0)) method.invoke(method.getDeclaringClass().newInstance(), eventToExecute);
                else method.invoke(eventToExecute, eventToExecute);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
                Logger.warning("Was not able to execute " + method.getName() + " in " + method.getDeclaringClass().getSimpleName() + " removing!");
                methodsToRemove.add(method);
            }
        });

        if (methodsToRemove.size() > 0) {
            methods.removeAll(methodsToRemove);
            eventMethodPublishees.replace(clazz, methods);
        }
    }

}
