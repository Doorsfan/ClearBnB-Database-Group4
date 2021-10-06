package com.company.application;

import com.company.domain.User;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public class ListingDTO {
    private Integer listingId, version, ownerId, numberGuests;
    private Double price;
    private String auditedDateTime, title, description, imageUrl, location, listingStartDate, listingEndDate;
    private User owner;

    public Integer getListingId() {
        return listingId;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setListingId(Integer listingId) {
        this.listingId = listingId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getNumberGuests() {
        return numberGuests;
    }

    public void setNumberGuests(Integer numberGuests) {
        this.numberGuests = numberGuests;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAuditedDateTime() {
        return auditedDateTime;
    }

    public void setAuditedDateTime(String auditedDateTime) {
        this.auditedDateTime = auditedDateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getListingStartDate() {
        return listingStartDate;
    }

    public void setListingStartDate(String listingStartDate) {
        this.listingStartDate = listingStartDate;
    }

    public String getListingEndDate() {
        return listingEndDate;
    }

    public void setListingEndDate(String listingEndDate) {
        this.listingEndDate = listingEndDate;
    }

}
