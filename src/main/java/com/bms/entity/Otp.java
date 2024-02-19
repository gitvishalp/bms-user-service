package com.bms.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import com.bms.util.ColumnDefinition;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Entity
@Data
@DynamicUpdate
@NoArgsConstructor
public class Otp implements Serializable {

	private static final long serialVersionUID = 1150871902352559268L;
	
	@Id
    @Column(unique = true)
    private String email;
	private String phoneNumber;
    private String otp;
    private int otpCount;
    @ManyToOne
    @JoinColumn(name = "otp_type",columnDefinition = ColumnDefinition.NVARCHAR8)
    private OtpType otpType;
    @CreationTimestamp
    private Date generatedTime;
    
	public Otp(String email, String phoneNumber, String otp, int otpCount, OtpType otpType, Date generatedTime) {
		super();
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.otp = otp;
		this.otpCount = otpCount;
		this.otpType = otpType;
		this.generatedTime = generatedTime;
	}
}
