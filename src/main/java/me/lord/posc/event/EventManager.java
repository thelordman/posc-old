package me.lord.posc.event;

import me.lord.posc.utilities.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;

public class EventManager {
	private static final int eventAmount;
	private static final Event[] events;
	private static Event currentEvent = null;

	static {
		Class<? extends Event>[] classes = ReflectionUtil.getSubclasses(Event.class, "me.lord.posc.event", c -> !c.isAnnotationPresent(EventName.class));
		eventAmount = classes.length;
		events = new Event[eventAmount];
		for (int i = 0; i < classes.length; i++) {
			Event event;
			try {
				event = classes[i].getDeclaredConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
			events[i] = event;
		}
	}

	protected static int getEventAmount() {
		return eventAmount;
	}

	protected static Event[] getEvents() {
		return events;
	}

	protected static Event getCurrentEvent() {
		return currentEvent;
	}

	protected static void setCurrentEvent(Event event) {
		currentEvent = event;
	}
}
