// package vttp.batch5.ssf.noticeboard.controllers;

// import java.util.Map;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.ResponseBody;

// import jakarta.validation.Valid;
// import vttp.batch5.ssf.noticeboard.models.notice;
// import vttp.batch5.ssf.noticeboard.repositories.NoticeRepository;
// import vttp.batch5.ssf.noticeboard.services.NoticeService;

// @Controller
// @RequestMapping
// public class LandingController {
//         @GetMapping({"/","/index.html","/notice",""})
//     public String getLanding(Model model){
//         model.addAttribute("notice",new notice());
//         return "notice.html";
//     }

//     @Autowired
//     private NoticeRepository noticeRepository;

//     @Autowired
//     private NoticeService noticeService;

//     @PostMapping(path = "/notice", consumes = "application/json", produces = "application/json")
//     public String noticeSub(@Valid @RequestBody notice notice, BindingResult result, Model model) {

//         if (result.hasErrors()) {
//             model.addAttribute("errorMessage", "Validation errors occurred");
//             model.addAttribute("timestamps", System.currentTimeMillis());
//             return "view3"; 
//         }

//         String noticeId = noticeRepository.insertNotices(notice);

//         try {
//             ResponseEntity<?> externalResponse = noticeService.postToNoticeServer(notice);
//             if (externalResponse.getStatusCode().is2xxSuccessful()) {
//                 model.addAttribute("id", noticeId);
//                 return "view2";
//             } else {
//                 model.addAttribute("errorMessage", "Failed to publish notice to external server. Status: " 
//                     + externalResponse.getStatusCode());
//                 return "view3";
//             }
//         } catch (Exception e) {
//             model.addAttribute("errorMessage", "Cannot post notice to the server. Error: " + e.getMessage());
//             return "view3";
//         }
//     }

//     @GetMapping("/status")
//     @ResponseBody
//     public ResponseEntity<?> healthCheck(){
//         try{
//             if (noticeRepository.getRandomHashkey() != null)
//             {return ResponseEntity.ok(Map.of("status", "OK"));}
//             return ResponseEntity.ok(Map.of("status", "OK"));

//         }catch(Exception ex){
//             return ResponseEntity.status(503).body(Map.of("status", "Service Unavailable"));
//         }
//     }

// }
