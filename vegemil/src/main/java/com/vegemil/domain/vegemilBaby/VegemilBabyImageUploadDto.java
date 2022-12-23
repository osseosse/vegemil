package com.vegemil.domain.vegemilBaby;

import java.awt.Image;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class VegemilBabyImageUploadDto {
	
	 private MultipartFile file;
	 private String caption;

	 
}
