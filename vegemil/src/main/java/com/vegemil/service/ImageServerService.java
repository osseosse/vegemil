package com.vegemil.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageServerService {

	@Value("${image-server.url}")
	private String imageServerUrl;

	@Value("${image-server.api-key}")
	private String apiKey;

	private final RestTemplate restTemplate = new RestTemplate();

	@SuppressWarnings("unchecked")
	public String upload(MultipartFile file, String folder) {
		if (file == null || file.isEmpty()) {
			return "";
		}

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("X-API-KEY", apiKey);
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);

			MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
			body.add("file", new ByteArrayResource(file.getBytes()) {
				@Override
				public String getFilename() {
					return file.getOriginalFilename();
				}
			});
			body.add("folder", folder);

			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

			ResponseEntity<Map> response = restTemplate.postForEntity(
				imageServerUrl + "/api/upload",
				requestEntity,
				Map.class
			);

			if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
				String url = (String) response.getBody().get("url");
				return imageServerUrl + url;
			}

			throw new RuntimeException("이미지 서버 업로드 실패: " + response.getStatusCode());

		} catch (IOException e) {
			throw new RuntimeException("파일 업로드 실패", e);
		}
	}
}
