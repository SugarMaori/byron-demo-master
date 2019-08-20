package com.git.byron.validation.entity;

import com.git.byron.validation.annotation.ConstraintsJustryDeng;
import com.git.byron.validation.annotation.TypeValid;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @Author: byron
 * @ProjectName: byron-demo-master
 * @Package: com.git.byron.validation.entity
 * @ClassName: User
 * @Description:
 * @Date: 2019/8/20 10:05
 * @Version: 1.0
 */
@Data
@Validated
public class User {

    @ConstraintsJustryDeng(str = "Test",message = "不包含内容")
    private String name;

    private String sex;
    private String phone;

    @TypeValid(intType = {1,2,3},message = "不支持此类型")
    private Integer type;
}
