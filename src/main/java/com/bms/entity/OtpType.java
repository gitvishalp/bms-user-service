package com.bms.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.bms.util.ColumnDefinition;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Entity
@Data
@NoArgsConstructor
public class OtpType implements Serializable {

	private static final long serialVersionUID = -1605122978165010350L;
	
	@Id
	@Column(columnDefinition = ColumnDefinition.NVARCHAR8)
    private String code;
	private String name;
	private String description;
	@Column(name = "is_active")
	private boolean active;
	@CreationTimestamp
	private Date createdAt;
	@UpdateTimestamp
	private Date updatedAt;
}
