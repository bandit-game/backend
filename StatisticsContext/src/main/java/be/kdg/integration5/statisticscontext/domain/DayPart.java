package be.kdg.integration5.statisticscontext.domain;

import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public enum DayPart {
    MORNING("Morning", LocalDateTime.of(2000, 1, 1, 6, 0), LocalDateTime.of(2000, 1, 1, 11, 59)),
    AFTERNOON("Afternoon", LocalDateTime.of(2000, 1, 1, 12, 0), LocalDateTime.of(2000, 1, 1, 17, 59)),
    EVENING("Evening", LocalDateTime.of(2000, 1, 1, 18, 0), LocalDateTime.of(2000, 1, 1, 21, 59)),
    NIGHT("Night", LocalDateTime.of(2000, 1, 1, 22, 0), LocalDateTime.of(2000, 1, 2, 5, 59)); // Wrap-around

    private final String description;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;

    DayPart(String description, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    // Determine the DayPart for a given LocalDateTime
    public static DayPart fromDateTime(LocalDateTime dateTime) {
        for (DayPart dayPart : values()) {
            if (dayPart.includes(dateTime)) {
                return dayPart;
            }
        }
        throw new IllegalArgumentException("Invalid date-time: " + dateTime);
    }

    // Check if a LocalDateTime falls within this DayPart
    public boolean includes(LocalDateTime dateTime) {
        LocalDateTime normalizedDateTime = normalize(dateTime);
        if (this == NIGHT) {
            // Handle wrap-around for NIGHT (e.g., 22:00 - 05:59)
            return !normalizedDateTime.isBefore(startDateTime) || !normalizedDateTime.isAfter(endDateTime);
        }
        return !normalizedDateTime.isBefore(startDateTime) && normalizedDateTime.isBefore(endDateTime.plusSeconds(1));
    }

    private LocalDateTime normalize(LocalDateTime dateTime) {
        return LocalDateTime.of(2000, 1, 1, dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond());
    }
}
