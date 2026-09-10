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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

		String originalFilename = file.getOriginalFilename();
		long fileSize = file.getSize();
		log.info("[ImageServer] 업로드 시작 - folder: {}, file: {}, size: {}bytes", folder, originalFilename, fileSize);

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
				String fullUrl = imageServerUrl + url;
				log.info("[ImageServer] 업로드 성공 - file: {}, url: {}", originalFilename, fullUrl);
				return fullUrl;
			}

			log.error("[ImageServer] 업로드 실패 - file: {}, status: {}, body: {}", originalFilename, response.getStatusCode(), response.getBody());
			throw new RuntimeException("이미지 서버 업로드 실패: " + response.getStatusCode());

		} catch (RestClientException e) {
			log.error("[ImageServer] API 통신 오류 - folder: {}, file: {}", folder, originalFilename, e);
			throw new RuntimeException("이미지 서버 통신 실패", e);
		} catch (IOException e) {
			log.error("[ImageServer] 파일 읽기 오류 - file: {}", originalFilename, e);
			throw new RuntimeException("파일 업로드 실패", e);
		}
	}

	@SuppressWarnings("unchecked")
	public void rotate(String imageUrl, int angle) {
		String path = imageUrl.replace(imageServerUrl, "");
		int lastSlash = path.lastIndexOf("/");
		String filename = path.substring(lastSlash + 1);
		String beforeFilename = path.substring(0, lastSlash);
		String folder = beforeFilename.substring(beforeFilename.lastIndexOf("/") + 1);

		log.info("[ImageServer] 회전 요청 - folder: {}, file: {}, angle: {}", folder, filename, angle);

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("X-API-KEY", apiKey);
			headers.setContentType(MediaType.APPLICATION_JSON);

			Map<String, Object> body = new java.util.HashMap<>();
			body.put("folder", folder);
			body.put("filename", filename);
			body.put("angle", angle);

			HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

			ResponseEntity<Map> response = restTemplate.postForEntity(
				imageServerUrl + "/api/rotate",
				requestEntity,
				Map.class
			);

			if (response.getStatusCode().is2xxSuccessful()) {
				log.info("[ImageServer] 회전 성공 - file: {}, message: {}", filename, response.getBody().get("message"));
				return;
			}

			log.error("[ImageServer] 회전 실패 - file: {}, status: {}", filename, response.getStatusCode());
			throw new RuntimeException("이미지 서버 회전 실패: " + response.getStatusCode());

		} catch (RestClientException e) {
			log.error("[ImageServer] 회전 API 통신 오류 - file: {}", filename, e);
			throw new RuntimeException("이미지 서버 통신 실패", e);
		}
	}
}
