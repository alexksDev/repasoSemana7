package com.alexdev.week077.event;

import com.alexdev.week077.entity.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class BookingEmailListener {

    @Async
    @EventListener
    public void handleBookingCreated(BookingCreatedEvent event) {
        Booking booking = event.getBooking();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

        String emailContent = String.format(
            "Hello %s %s,\n\n" +
            "Your booking was successful! \n\n" +
            "The booking is for flight %s with departure date of %s and arrival date of %s.\n\n" +
            "The booking was registered at %s.\n\n" +
            "Bon Voyage!\n" +
            "Fly Away Travel",
            booking.getCustomer().getFirstName(),
            booking.getCustomer().getLastName(),
            booking.getFlight().getFlightNumber(),
            booking.getFlight().getEstDepartureTime().format(formatter),
            booking.getFlight().getEstArrivalTime().format(formatter),
            booking.getBookingDate().format(formatter)
        );

        String fileName = "flight_booking_email_" + booking.getId() + ".txt";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(emailContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
