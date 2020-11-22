package com.ford.shanghai.finder.dao.entity;

import java.io.Serializable;
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
@Table(name = "interest_point")
@NoArgsConstructor
@AllArgsConstructor
public class InterestPoint implements Serializable{

	private static final long serialVersionUID = -3299616808519127354L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "channel")
	private String channel;
	
	@Column(name = "poi_type")
	private String poiType;

	@Column(name = "id_in_channel")
	private String uid;

	@Column(name = "name")
	private String name;

	@Column(name = "location")
	private String location;

	@Column(name = "address")
	private String address;

	@Column(name = "province")
	private String province;

	@Column(name = "city")
	private String city;

	@Column(name = "area")
	private String area;

	@Column(name = "street_id")
	private String streetId;

	@Column(name = "telephone")
	private String telephone;

	@Column(name = "detail")
	private Integer detail;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = JAVA_DATE_TIME_FMT)
    @Column(name = "create_time", insertable = false, updatable = false)
    protected Date createTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = JAVA_DATE_TIME_FMT)
    @Column(name = "update_time", insertable = false, updatable = false)
    protected Date updateTime;
}
