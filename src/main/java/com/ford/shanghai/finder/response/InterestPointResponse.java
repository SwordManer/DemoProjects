package com.ford.shanghai.finder.response;

import java.util.List;

import com.ford.shanghai.finder.feign.response.PointOfInterest;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class InterestPointResponse extends AbstractResponse {

	private List<PointOfInterest> interestPoints;

}
