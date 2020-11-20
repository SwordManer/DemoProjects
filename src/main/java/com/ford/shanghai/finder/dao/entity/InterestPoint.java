package com.ford.shanghai.finder.dao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import static com.ford.shanghai.finder.utils.Constants.JAVA_DATE_TIME_FMT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "interest_point")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterestPoint {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@Column(name = "channel", nullable = false, unique = true)
	private String channel;
	
	@Column(name = "poi_type", nullable = false, unique = true)
	private String poiType;

	@Column(name = "id_in_channel", nullable = false, unique = true)
	private String uid;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@Column(name = "location", nullable = false, unique = true)
	private String location;

	@Column(name = "address", nullable = false, unique = true)
	private String address;

	@Column(name = "province", nullable = false, unique = true)
	private String province;

	@Column(name = "city", nullable = false, unique = true)
	private String city;

	@Column(name = "area", nullable = false, unique = true)
	private String area;

	@Column(name = "street_id", nullable = false, unique = true)
	private String streetId;

	@Column(name = "telephone", nullable = false, unique = true)
	private String telephone;

	@Column(name = "detail", nullable = false, unique = true)
	private Integer detail;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = JAVA_DATE_TIME_FMT)
    @Column(name = "create_time", insertable = false, updatable = false)
    protected Date createTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = JAVA_DATE_TIME_FMT)
    @Column(name = "update_time", insertable = false, updatable = false)
    protected Date updateTime;
}
