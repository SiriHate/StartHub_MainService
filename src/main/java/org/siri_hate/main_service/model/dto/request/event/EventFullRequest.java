package org.siri_hate.main_service.model.dto.request.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class EventFullRequest {

    @NotBlank(message = "Event name should not be blank")
    private String eventName;

    @NotBlank(message = "Category should not be blank")
    private String category;

    @NotBlank(message = "Event description should not be blank")
    private String eventDescription;

    @NotNull(message = "Event date should not be blank")
    private LocalDateTime EventDate;

    @NotBlank(message = "Event location should not be blank")
    private String onlineConferenceLink;

    public EventFullRequest() {
    }

    public EventFullRequest(
            String eventName,
            String category,
            String eventDescription,
            LocalDateTime EventDate,
            String onlineConferenceLink) {
        this.eventName = eventName;
        this.category = category;
        this.eventDescription = eventDescription;
        this.EventDate = EventDate;
        this.onlineConferenceLink = onlineConferenceLink;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public LocalDateTime getEventDate() {
        return EventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        EventDate = eventDate;
    }

    public String getOnlineConferenceLink() {
        return onlineConferenceLink;
    }

    public void setOnlineConferenceLink(String onlineConferenceLink) {
        this.onlineConferenceLink = onlineConferenceLink;
    }
}
