package com.company.Entities;

import com.company.Entities.CompositeIDs.ListingCompositeId;
import jakarta.persistence.*;
import java.time.*;

@Entity
@IdClass(ListingCompositeId.class)
@Table(name = "Listing")
public class Listing {
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
    private String image_url;
    private String location;
    @Column(name = "number_guests")
    private Integer numberGuests;
    private Double price;
    @Column(name = "listing_start_date")
    private LocalDate listingStartDate;
    @Column(name = "listing_end_date")
    private LocalDate listingEndDate;

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

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
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

    public LocalDate getListingStartDate() {
        return listingStartDate;
    }

    public void setListingStartDate(LocalDate listingStartDate) {
        this.listingStartDate = listingStartDate;
    }

    public LocalDate getListingEndDate() {
        return listingEndDate;
    }

    public void setListingEndDate(LocalDate listingEndDate) {
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
                ", image_url='" + image_url + '\'' +
                ", location='" + location + '\'' +
                ", numberGuests=" + numberGuests +
                ", price=" + price +
                ", listingStartDate=" + listingStartDate +
                ", listingEndDate=" + listingEndDate +
                '}';
    }
}
