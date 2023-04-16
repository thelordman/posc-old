package me.lord.posc.events;

import java.time.MonthDay;

public abstract class SeasonalEvent implements Event {
    public abstract MonthDay getMonthDay();
}
