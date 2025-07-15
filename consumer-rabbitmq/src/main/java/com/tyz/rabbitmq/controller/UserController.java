package com.tyz.rabbitmq.controller;

import com.tyz.rabbitmq.config.valid.UserGroup;
import com.tyz.rabbitmq.entity.UserRegistrationReqVO;
import com.tyz.rabbitmq.entity.UserVO;
import com.tyz.rabbitmq.service.IUserService;
import com.tyz.rabbitmq.vo.UserIdCardVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<?> save(@Validated(UserGroup.CreateGroup.class) @RequestBody UserVO user) {
        if (Objects.nonNull(user)) {
            UserVO userVo = userService.saveOne(user);
            return ResponseEntity.ok()
                    .body(userVo);
        }
        return ResponseEntity.status(500).build();
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@Validated(UserGroup.UpdateGroup.class) @RequestBody UserVO user) {
        // 更新逻辑
        return ResponseEntity.ok().body(user);
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

    /**
     * <p>
     *     body 参数验证
     *     @Valid 的触发时机是在HandlerMethodArgumentResolver这个组件封装解析参数是触发
     *     @Validated 如果在Service层中使用，是AOP机制，通过MethodValidationInterceptorAOP代理在方法调用是触发
     *     @Validated 如果只是在Controller层方法中使用，Spring MVC 会将其视为 @Valid 的增强版，
     *               仍通过 RequestResponseBodyMethodProcessor 触发校验
     * </p>
     *
     * @param vo 注册vo
     * @return ResponseEntity obj
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationReqVO vo) {
        // 业务逻辑处理
        return ResponseEntity.ok("用户注册成功");
    }

    /**
     * <p>
     *     对于路径变量和请求参数的校验，需要使用@Validated注解在类级别
     * </p>
     *
     * @param id 注册ID
     * @return ResponseEntity obj
     */
    @GetMapping("/register/{id}")
    public ResponseEntity<UserRegistrationReqVO> getUserById(@PathVariable @Min(1) Long id) {
        // 获取用户逻辑
        return ResponseEntity.ok(new UserRegistrationReqVO());
    }

}
