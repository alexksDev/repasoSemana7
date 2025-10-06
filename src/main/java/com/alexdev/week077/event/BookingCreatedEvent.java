package com.alexdev.week077.event;

import com.alexdev.week077.entity.Booking;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class BookingCreatedEvent extends ApplicationEvent {

    private final Booking booking;

    public BookingCreatedEvent(Object source, Booking booking) {
        super(source);
        this.booking = booking;
    }
}
