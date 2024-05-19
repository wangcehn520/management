package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import com.sky.vo.EmployeeVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 增加员工
     * @param employee
     */
    @Insert("insert into employee" +
            " (name,username,password,phone,sex,id_Number,status,create_time,update_time,create_user,update_user)" +
            " values" + "(#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void addEmp(Employee employee);

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    Page<EmployeeVO> pageSearch(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 修改员工信息
     */
    @AutoFill(value = OperationType.UPDATE)
    void updateEmp(Employee employee);

    @Select("select id,username,name,phone,sex,id_number from employee where id = #{id}")
    Employee getById(Long id);

}
