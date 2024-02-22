package com.bms.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import com.bms.util.ColumnDefinition;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Data
@Entity
@DynamicUpdate
@NoArgsConstructor
public class User implements Serializable {
	
	private static final long serialVersionUID = -7048993901697158510L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	//@Column(columnDefinition = ColumnDefinition.UNIQUEIDENTIFIER)
	private String id;
	private String firstName;
	private String lastName;
	private LocalDate dob;
	private String phoneNumber;
	private boolean phoneNumberVerified;
	private String email;
	private String password;
	@Column(columnDefinition = "INT DEFAULT 0")
	private int failedLoginAttempt;
	@Column(columnDefinition = ColumnDefinition.BIT)
	private boolean active;
	private String country;
	@CreationTimestamp
	private Date createdAt;
	@UpdateTimestamp
	private Date updatedAt;
	
}
