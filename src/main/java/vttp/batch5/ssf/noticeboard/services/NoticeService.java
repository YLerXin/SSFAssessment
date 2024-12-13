package vttp.batch5.ssf.noticeboard.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import vttp.batch5.ssf.noticeboard.models.notice;
import vttp.batch5.ssf.noticeboard.repositories.NoticeRepository;
@Service
public class NoticeService {

	// TODO: Task 3
	// You can change the signature of this method by adding any number of parameters
	// and return any type
	public static final String url = "https://publishing-production-d35a.up.railway.app/";
	public ResponseEntity<?> postToNoticeServer(notice notice) {
		RestTemplate restTemplate = new RestTemplate();

		Map<String,Object> payload = new HashMap<>();
		payload.put("title",notice.getTitle());
		payload.put("poster",notice.getPoster());
		payload.put("postDate",notice.getPostDate().getTime());
		payload.put("categories",notice.getCategories());
		payload.put("text",notice.getText());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String,Object>> request = new HttpEntity<>(payload,headers);

		ResponseEntity<?> resp = restTemplate.postForEntity(url,request,Map.class);
		return resp;

	}

}
