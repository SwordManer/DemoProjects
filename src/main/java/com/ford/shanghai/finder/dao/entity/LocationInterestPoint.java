package com.ford.shanghai.finder.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import static com.ford.shanghai.finder.utils.Constants.JAVA_DATE_TIME_FMT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "location_interestpoint")
@NoArgsConstructor
@AllArgsConstructor
public class LocationInterestPoint implements Serializable{

	private static final long serialVersionUID = -3299616808519127354L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "channel")
	private String channel;
	
	@Column(name = "loc_latitude")
	private BigDecimal locLatitude;

	@Column(name = "loc_logitude")
	private BigDecimal locLogitude;

	@Column(name = "location")
	private String location;

	@Column(name = "poi_id")
	private String poiId;

	@Column(name = "poi_type")
	private String poiType;

	@Column(name = "poiLatitude")
	private BigDecimal poiLatitude;

	@Column(name = "poi_logitude")
	private BigDecimal poiLogitude;

	@Column(name = "radius")
	private BigDecimal radius;

	@Column(name = "distance")
	private BigDecimal distance;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = JAVA_DATE_TIME_FMT)
    @Column(name = "create_time", insertable = false, updatable = false)
    protected Date createTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = JAVA_DATE_TIME_FMT)
    @Column(name = "update_time", insertable = false, updatable = false)
    protected Date updateTime;
}