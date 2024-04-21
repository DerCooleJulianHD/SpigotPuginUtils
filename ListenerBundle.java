package de.dercoolejulianhd.plugin_utilites.listener;

import jdk.jfr.Description;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Object that saves and contains
 * active Event-Listeners from Plugin which is using this.
 */
@Description(" /* Object that saves and contains active Event-Listeners from Plugin which is using this */ ")
public class ListenerBundle {

    final List<Listener> listeners;

    public ListenerBundle() {
        this.listeners = new ArrayList<>();
    }


    /**
     * Description:
     * adds a Listener to a List of Listeners.
     */
    @Description(" /* adds a Listener to a List of Listeners */ ")
    public void addListener(Listener listener){

        if (this.listeners.contains(listener)) {
            return;
        }

        this.listeners.add(listener);
    }


    /**
     * Description:
     * removes a Listener which was set to the List before.
     */
    @Description(" /* removes a Listener which was set to the List before */ ")
    public void removeListener(Listener listener) {

        if (!this.listeners.contains(listener)) {
            return;
        }

        this.listeners.remove(listener);
    }


    /**
     * Description:
     * returns the List which contains the saved Event-Listeners.
     */
    @Description(" /* returns the List which contains the saved Event-Listeners */ ")
    public List<Listener> listeners() {
        return listeners;
    }
}
