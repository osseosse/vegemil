package com.vegemil.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class BeansoupEventDTO extends CommonDTO {

	private Long mIdx;
	private String mTitle;
	private String mStartDate;
	private String mEndDate;
	private Long mIng;
	private Long mDisplay;
	private String mThum;
	private String mImg;
	private String mEdayId;
	private String mContent;
	private String mType;
	private String mProduct;
	@Override
	public String toString() {
		return "BeansoupEventDTO [mIdx=" + mIdx + ", mTitle=" + mTitle + ", mStartDate=" + mStartDate + ", mEndDate="
				+ mEndDate + ", mIng=" + mIng + ", mDisplay=" + mDisplay + ", mThum=" + mThum + ", mImg=" + mImg
				+ ", mEdayId=" + mEdayId + ", mContent=" + mContent + ", mType=" + mType + ", mProduct=" + mProduct
				+ "]";
	}
	
	

}
