package com.company.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@IdClass(ListingCompositeId.class)
@Table(name = "Listing")
public class Listing implements Cloneable {
    @Id
    @Column(name = "listing_id")
    private Integer listingId;
    @Id
    private Integer version;
    @Column(name = "audited_datetime")
    private LocalDateTime auditedDatetime;
    @ManyToOne
    @JoinColumn(name="owner_id")
    private User owner;
    private String title;
    private String description;
    @Column(name = "image_url")
    private String imageUrl;
    private String location;
    @Column(name = "number_guests")
    private Integer numberGuests;
    private Double price;
    @Column(name = "listing_start_date")
    private Date listingStartDate;
    @Column(name = "listing_end_date")
    private Date listingEndDate;

    public Integer getListingId() {
        return listingId;
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

    public LocalDateTime getAuditedDatetime() {
        return auditedDatetime;
    }

    public void setAuditedDatetime(LocalDateTime auditedDatetime) {
        this.auditedDatetime = auditedDatetime;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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

    public void setImageUrl(String image_url) {
        this.imageUrl = image_url;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public Date getListingStartDate() {
        return listingStartDate;
    }

    public void setListingStartDate(Date listingStartDate) {
        this.listingStartDate = listingStartDate;
    }

    public Date getListingEndDate() {
        return listingEndDate;
    }

    public void setListingEndDate(Date listingEndDate) {
        this.listingEndDate = listingEndDate;
    }

    @Override
    public String toString() {
        return "Listing{" +
                "listingId=" + listingId +
                ", version=" + version +
                ", auditedDatetime=" + auditedDatetime +
                ", owner=" + owner.getUserId() +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", location='" + location + '\'' +
                ", numberGuests=" + numberGuests +
                ", price=" + price +
                ", listingStartDate=" + listingStartDate +
                ", listingEndDate=" + listingEndDate +
                '}';
    }

    @Override
    public Listing clone() {
        Listing listing = new Listing();
        listing.setListingId(this.getListingId());
        listing.setVersion(this.getVersion());
        listing.setAuditedDatetime(this.getAuditedDatetime());
        listing.setOwner(this.getOwner());
        listing.setTitle(this.getTitle());
        listing.setDescription(this.getDescription());
        listing.setImageUrl(this.getImageUrl());
        listing.setLocation(this.getLocation());
        listing.setNumberGuests(this.getNumberGuests());
        listing.setPrice(this.getPrice());
        listing.setListingStartDate(this.getListingStartDate());
        listing.setListingEndDate(this.getListingEndDate());
        return listing;
    }
}
