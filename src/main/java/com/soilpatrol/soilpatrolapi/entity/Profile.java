package com.soilpatrol.soilpatrolapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    private UUID id; // Supabase Auth generates UUIDs

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "email", unique = true)
    private String email;          // NEW!

    @Column(name = "password")
    private String password;       // NEW!
    @Column(name = "subscription_tier")
    private String subscriptionTier = "free";

    @Column(name = "created_at")
    private ZonedDateTime createdAt;
}