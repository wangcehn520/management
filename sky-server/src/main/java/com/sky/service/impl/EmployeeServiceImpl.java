package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.BaseException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import com.sky.vo.EmployeeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.InitBinder;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @Override
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus().equals(StatusConstant.DISABLE)) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * 新增员工
     * @param employeeDTO
     */
    @Override
    public void addEmp(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        //先对数据进行合法校验
        if (checkDate(employeeDTO)) {
            throw new BaseException("提交的参数不合法");
        }
        BeanUtils.copyProperties(employeeDTO, employee);

        if (StringUtils.isEmpty(employeeDTO.getSex())) {
            employee.setSex("1");
        }
        String password = DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes());
        employee.setPassword(password);
        employee.setStatus(StatusConstant.ENABLE);
        employeeMapper.addEmp(employee);
    }

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    @Override
    public PageResult pageEmp(EmployeePageQueryDTO employeePageQueryDTO) {
        PageHelper.startPage(employeePageQueryDTO.getPage(),employeePageQueryDTO.getPageSize());
        Page<EmployeeVO> employees = employeeMapper.pageSearch(employeePageQueryDTO);
        List<EmployeeVO> EmpList = employees.getResult();
        long total = employees.getTotal();
        PageResult result = new PageResult(total,EmpList);
        return result;
    }

    /**
     * 启用或禁用员工
     * @param status
     * @param id
     */
    @Override
    public void setStatus(Integer status, Long id) {
        Employee employee = Employee.builder().status(status).id(id).build();
        employeeMapper.updateEmp(employee);
    }

    /**
     * 根据id获取员工
     * @param id
     * @return
     */
    @Override
    public Employee getById(Long id) {
        Employee employee = employeeMapper.getById(id);
        String replaceIdNumber = employee.getIdNumber().replaceAll("(\\d)\\d{16}(\\w{1})", "$1*******************$2");
        String replacePhone = employee.getPhone().replaceAll("(\\d{2})\\d{5}(\\d{4})", "$1*****$2");
        employee.setPhone(replacePhone);
        employee.setIdNumber(replaceIdNumber);
        return employee;
    }

    /**
     * 修改员工信息
     * @param employeeDTO
     */
    @Override
    public void update(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        employeeMapper.updateEmp(employee);
    }

    /**
     * 校验数据
     * @param employeeDTO
     * @return
     */
    private Boolean checkDate(EmployeeDTO employeeDTO){
        if (StringUtils.isEmpty(employeeDTO.getName()) || employeeDTO.getName().contains(" ")){
            return true;
        }
        if (StringUtils.isEmpty(employeeDTO.getPhone())){
            return true;
        }
        if (StringUtils.isEmpty(employeeDTO.getUsername())){
            return true;
        }
        if (StringUtils.isEmpty(employeeDTO.getIdNumber())){
            return true;
        }
        return false;
    }
}
