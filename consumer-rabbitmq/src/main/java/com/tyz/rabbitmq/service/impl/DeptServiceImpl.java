package com.tyz.rabbitmq.service.impl;

import com.tyz.rabbitmq.entity.DeptVO;
import com.tyz.rabbitmq.mapper.DeptMapper;
import com.tyz.rabbitmq.service.IDeptService;
import com.tyz.rabbitmq.service.IEmployeeService;
import com.tyz.rabbitmq.vo.DeptEmpVO;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.concurrent.CompletableFuture;

/**
 * @program: cloud-demo
 * @description: DeptService
 * @author: tyz
 * @create: 2025-06-06
 */
@Service
public class DeptServiceImpl implements IDeptService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeptServiceImpl.class);
    private final DeptMapper deptMapper;

    private final IEmployeeService employeeService;

    @Autowired
    public DeptServiceImpl(DeptMapper deptMapper, IEmployeeService employeeService) {
        this.deptMapper = deptMapper;
        this.employeeService = employeeService;
    }

    /**
     * @param vo 部分vo
     * @return vo
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public DeptVO save(DeptVO vo) {
        vo.setStatus(1);
        deptMapper.insert(vo);
        return vo;
    }

    /**
     * @param deptEmpVO vo
     * @return vo
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public DeptEmpVO saveLinkEmp(DeptEmpVO deptEmpVO) {
        DeptVO vo = new DeptVO();
        vo.setDeptName(deptEmpVO.getDeptName());
        vo.setDeptNo(deptEmpVO.getDeptNo());
        vo.setStatus(1);
        deptMapper.insert(vo);
        final Long deptId = vo.getDeptId();
        deptEmpVO.getEmployees().forEach(emp -> {
            emp.setDeptId(deptId);
            emp.setStatus(1);
        });
        if (CollectionUtils.isNotEmpty(deptEmpVO.getEmployees())) {
            try {
                LOGGER.info(String.valueOf(TransactionSynchronizationManager.isActualTransactionActive()));
                LOGGER.info(TransactionSynchronizationManager.getCurrentTransactionName());
                employeeService.batchSave(deptEmpVO.getEmployees());
            } catch (Exception e0) {
                LOGGER.error(e0.getMessage(), e0);
            }
        }
        deptEmpVO.setDeptId(vo.getDeptId());
        deptEmpVO.setStatus(vo.getStatus());

        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    /**
                     * <p>
                     *     用于事务提交后处理，异常也不会对已提交的事物有影响。
                     *     事务成功提交后 触发，适合执行异步任务。
                     * </p>
                     */
                    @Override
                    public void afterCommit() {
                        CompletableFuture.runAsync(() -> {
                            LOGGER.info("afterCommit doing somethings....");
                        });
                        // int a = 1/0;
                    }
                }
        );
        LOGGER.info("do over somethings....");
        return deptEmpVO;
    }

    /**
     * @param id id
     * @return vo
     */
    @Override
    public DeptEmpVO findDepEmpInfo(Long id) {
        return deptMapper.findDepEmpIno(id);
    }
}
