package com.tyz.rabbitmq.controller;

import com.tyz.rabbitmq.entity.DeptVO;
import com.tyz.rabbitmq.service.IDeptService;
import com.tyz.rabbitmq.vo.DeptEmpVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @program: cloud-demo
 * @description: DeptController
 * @author: tyz
 * @create: 2025-06-06
 */
@RestController
@RequestMapping("/api/dept")
public class DeptController {
    @Autowired
    private IDeptService deptService;
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody DeptVO deptVO) {
        if (Objects.nonNull(deptVO)) {
            DeptVO dept = deptService.save(deptVO);
            return ResponseEntity.ok().body(dept);
        }
        return ResponseEntity.status(500).build();
    }

    @PostMapping("/emp/save")
    public ResponseEntity<?> saveLinkEmp(@RequestBody DeptEmpVO deptEmpVO) {
        if (Objects.nonNull(deptEmpVO)) {
            DeptEmpVO dept = deptService.saveLinkEmp(deptEmpVO);
            return ResponseEntity.ok().body(dept);
        }
        return ResponseEntity.status(500).build();
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getInfo(@PathVariable("id") Long id) {
        if (Objects.nonNull(id)) {
            DeptEmpVO deptEmpVO = deptService.findDepEmpInfo(id);
            return ResponseEntity.ok()
                    .body(deptEmpVO);
        }
        return ResponseEntity.status(500).build();
    }
}
