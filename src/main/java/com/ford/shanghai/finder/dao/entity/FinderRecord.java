package com.ford.shanghai.finder.dao.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

import static com.ford.shanghai.finder.utils.Constants.JAVA_DATE_TIME_FMT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "finderrecord")
@NoArgsConstructor
@AllArgsConstructor
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class FinderRecord implements Serializable{

	private static final long serialVersionUID = -3299616808519127354L;

	@Id
	@GeneratedValue(generator = "jpa-uuid", strategy = GenerationType.AUTO)
	private String id;

	@Column(name = "poi_type")
	private String poiType;

	@Column(name = "user_id")
	private String userId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = JAVA_DATE_TIME_FMT)
	@Column(name = "query_time", insertable = false, updatable = false)
	protected Date queryTime;

	@Column(name = "start_location")
	private String startLocation;

	@Column(name = "end_location")
	private String endLocation;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = JAVA_DATE_TIME_FMT)
    @Column(name = "create_time", insertable = false, updatable = false)
    protected Date createTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = JAVA_DATE_TIME_FMT)
    @Column(name = "update_time", insertable = false, updatable = false)
    protected Date updateTime;
}
