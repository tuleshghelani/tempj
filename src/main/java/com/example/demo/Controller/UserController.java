package com.example.demo.Controller;

import com.example.demo.DTO.UserDto;
import com.example.demo.Entity.User;
import com.example.demo.helper.Helper;
import com.example.demo.services.UserServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@ResponseBody
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private Helper helper;

    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("/save")
    public ResponseEntity<?> saveUser( @RequestParam("username") String username,
                                       @RequestParam("password") String password,
                                       @RequestParam("birthdate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthdate,
                                       @RequestParam("files") MultipartFile[] files) throws IOException {
        return userServices.saveUser(files, username, password, birthdate);
    }

    @GetMapping("/getUsers")
    public List<User> getUsers() {
        return userServices.getUSers();
    }

    @GetMapping("/getUserdata")
    public Optional<User> getUserdata(@RequestParam("id") Long id) {
        Optional<User> user = userServices.getUserData(id);
        if(!user.isPresent()) {
            return Optional.of(new User());
        }
        return userServices.getUserData(id);
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestParam("username") String username,
//                         @RequestParam("password") String password) {
////        UserDto user = userServices.login(username, password);
////        if(user != null) {
////            return ResponseEntity.ok(new JSONObject().put("status", true)
////                    .put("username", user.getUsername())
////                    .put("role", user.getRole())
////            );
////        }
////        return ResponseEntity.ok(new JSONObject().put("status", false));
//        return userServices.login(username, password);
//    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto jsonNode) throws JsonProcessingException {
//    public ResponseEntity<?> login(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
//        JSONObject jsonObject = helper.convertToJSONObject(new ObjectMapper(), jsonNode);
//        Object jsonObject = objectMapper.treeToValue(jsonNode, Object.class);

//        return userServices.login(jsonObject.getString("username").toString(), jsonObject.getString("password").toString());
        return userServices.login(jsonNode.getUsername(), jsonNode.getPassword());
//        return null;
    }
}
