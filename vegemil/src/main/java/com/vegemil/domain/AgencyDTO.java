
package com.vegemil.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class AgencyDTO extends CommonDTO {
	
	private String idx;   
	private String area;  
	private String name;  
	private String addr;  
	private String tel;   
	private String hp;    
	private String coverRange;
	
	
	private String mapx;
	private String mapy;
	
	
}
