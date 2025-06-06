package com.tyz.rabbitmq.controller;

import com.tyz.rabbitmq.entity.UserVO;
import com.tyz.rabbitmq.service.IUserService;
import com.tyz.rabbitmq.vo.UserIdCardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @program: cloud-demo
 * @description: LoginController
 * @author: tyz
 * @create: 2025-05-17
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody UserVO user) {
        if (Objects.nonNull(user)) {
            UserVO userVo = userService.saveOne(user);
            return ResponseEntity.ok()
                    .body(userVo);
        }
        return ResponseEntity.status(500).build();
    }

    @PostMapping("/card/save")
    public ResponseEntity<?> saveUserIdCard(@RequestBody UserVO user) {
        if (Objects.nonNull(user)) {
            UserVO userVo = userService.saveUserIdCard(user);
            return ResponseEntity.ok()
                    .body(userVo);
        }
        return ResponseEntity.status(500).build();
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getInfo(@PathVariable("id") Long id) {
        if (Objects.nonNull(id)) {
            UserIdCardVO userVo = userService.findUserCardInfo(id);
            return ResponseEntity.ok()
                    .body(userVo);
        }
        return ResponseEntity.status(500).build();
    }
}
