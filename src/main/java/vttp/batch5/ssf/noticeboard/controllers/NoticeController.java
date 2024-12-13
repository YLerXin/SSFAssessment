package vttp.batch5.ssf.noticeboard.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import vttp.batch5.ssf.noticeboard.models.notice;
import vttp.batch5.ssf.noticeboard.repositories.NoticeRepository;
import vttp.batch5.ssf.noticeboard.services.NoticeService;

// Use this class to write your request handlers
@Controller
@RequestMapping
public class NoticeController {

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private NoticeService noticeService;

    @GetMapping({"/","/index.html","/notice",""})
    public String getLanding(Model model){
        model.addAttribute("notice",new notice());
        return "notice.html";
    }

    @PostMapping(path = "/notice", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Object noticeSub(@Valid @RequestBody notice notice, BindingResult result, Model model, HttpServletRequest request) {


        boolean wantJson = request.getHeader("Accept") != null &&
        request.getHeader("Accept").contains("application/json");
        if (result.hasErrors()) {
            if (wantJson){
                //return "notice"
                Map<String,Object> errorRes = new HashMap<>();
                errorRes.put("errorMessage", "Validation errors occurred");
                errorRes.put("timestamps", System.currentTimeMillis());
                return ResponseEntity.badRequest().body(errorRes);
            }
            else{
                ModelAndView mav = new ModelAndView();
                mav.addObject("errorMessage", "Validation errors occurred");
                mav.addObject("timestamps", System.currentTimeMillis());
                return mav;
            }
        }

        String noticeId = noticeRepository.insertNotices(notice);

        try {
            ResponseEntity<?> externalResponse = noticeService.postToNoticeServer(notice);
            if (externalResponse.getStatusCode().is2xxSuccessful()) {
                if (wantJson) {
                    Map<String,Object> successRes = new HashMap<>();
                    successRes.put("Id", noticeId);
                    successRes.put("timestamps", System.currentTimeMillis());
                    return ResponseEntity.ok(successRes);
                } else {
                    ModelAndView mav = new ModelAndView("view2");
                    mav.addObject("id", noticeId);
                    return mav;
                }
            } else {
                if (wantJson) {
                    Map<String,Object> failRes = new HashMap<>();
                    failRes.put("errorMessage", "Failed to publish notice. Status: " + externalResponse.getStatusCode());
                    failRes.put("timestamps", System.currentTimeMillis());
                    return ResponseEntity.badRequest().body(failRes);
                } else {
                    ModelAndView mav = new ModelAndView("view3");
                    mav.addObject("errorMessage", "Failed to publish notice. Status: " + externalResponse.getStatusCode());
                    mav.addObject("timestamps", System.currentTimeMillis());
                    return mav;
                }
            }
        } catch (Exception e) {
            if (wantJson) {
                Map<String,Object> failRes = new HashMap<>();
                failRes.put("errorMessage", "Cannot post notice: " + e.getMessage());
                failRes.put("timestamps", System.currentTimeMillis());
                return ResponseEntity.badRequest().body(failRes);
            } else {
                ModelAndView mav = new ModelAndView("view3");
                mav.addObject("errorMessage", "Cannot post notice: " + e.getMessage());
                mav.addObject("timestamps", System.currentTimeMillis());
                return mav;
            }
        }
    }

    @GetMapping("/status")
    @ResponseBody
    public ResponseEntity<?> healthCheck(){
        try{
            if (noticeRepository.getRandomHashkey() != null)
            {return ResponseEntity.ok(Map.of("status", "OK"));}
            return ResponseEntity.ok(Map.of("status", "OK"));

        }catch(Exception ex){
            return ResponseEntity.status(503).body(Map.of("status", "Service Unavailable"));
        }
    }
}
    


