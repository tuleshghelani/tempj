package com.example.demo.services;

import com.example.demo.DTO.UserDto;
import com.example.demo.Entity.User;
import com.example.demo.helper.Helper;
import com.example.demo.helper.MD5Util;
import com.example.demo.helper.Role;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServices {
//    @Value("${file.upload.path}")
//    private String fileUploadPath;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    Helper helper;

    public ResponseEntity<?> saveUser(MultipartFile[] files, String username, String password, Date birthdate) throws IOException {
        Optional<User> isUserExist = userRepository.findByUsername(username);
        if(isUserExist.isPresent()) {
            return ResponseEntity.ok(new JSONObject().put("message", "Username already exist").toString());
        }
        String fileUploadPath = "C:\\Users\\GT\\Downloads\\demo\\demo\\";
        List<String> fileNames = new ArrayList<>();

        for (MultipartFile file : files) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String filePath = fileUploadPath + File.separator + fileName;
            file.transferTo(new File(filePath));
            fileNames.add(fileName);
        }
//        String hashedPassword = MD5Util.md5(password);
        password = MD5Util.md5(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setBirthdate(birthdate);
        user.setRole(Role.Standard);
        user.setImages(fileNames.toString());

        userRepository.save(user);
        return ResponseEntity.ok(new JSONObject().put("message", "User-created successfully").toString());
    }

    public List<User> getUSers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserData(Long id) {
        return userRepository.findById(id);
    }

    public ResponseEntity<?> login(String username, String password) {
        String hashedPassword = MD5Util.md5(password);
        Optional<User> user = userRepository.findByUsernameAndPassword(username, hashedPassword);
        if(user.isPresent()) {
            UserDto userDto = new UserDto(user.get().getUsername(), user.get().getRole().name());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", true);
            jsonObject.put("role", userDto.getRole()).toString();
            jsonObject.put("username", userDto.getUsername()).toString();
            return ResponseEntity.ok(helper.convertToJsonNode(objectMapper, jsonObject));
        }
//        return null;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", false);
        return ResponseEntity.ok(helper.convertToJsonNode(objectMapper, jsonObject));
    }
}
