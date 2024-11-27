package com.isylph.operator.domain.entity;

import com.isylph.basis.consts.RetCodeConsts;
import com.isylph.basis.controller.exception.ReturnException;
import com.isylph.operator.domain.types.SysDepartment;
import com.isylph.utils.StringUtils;
import com.isylph.utils.encryption.PasswordUtils;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 系统用户 sys_user
 * </p>
 *
 * @author denzel.lin
 * @since 2021-08-19
 */


@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysOperator {

    private Long id;

    /**
     * 添加时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    private SysDepartment org;
    /**
     * 账号
     */
    private String account;

    /**
     * 手机号码
     */
    private String mobile;

    private String email;

    /**
     * 头像
     */
    private String avatar;

    private String signature;

    private String title;

    /**
     * 姓名
     */
    private String name;

    private String type;

    /**
     * 密码盐
     */
    private String salt;

    private String password;

    /**
     * 用户状态 0 正常
     */
    private Integer status;

    private String remark;

    private String permissionCode;

    List<OperatorDepartment> departments;


    public Boolean login(String password){
        String pwd = PasswordUtils.encryptPassword(password, this.salt).toUpperCase();
        if (!pwd.equals(this.password)){
            //log.info("Incorrect password: {}", account);
            throw new ReturnException(RetCodeConsts.RET_ERROR);
        }

        return true;
    }

    public void initPassword(String password){
        if (this.salt == null){
            this.salt = PasswordUtils.getSalt();
        }

        if (StringUtils.isEmpty(password)){
            return;
        }

        this.password = PasswordUtils.encryptPassword(password, this.salt);
    }

    public SysOperator setOrg(SysDepartment org){
        this.org = org;
        if(org == null){
            return this;
        }
        if(this.permissionCode == null){
            /**
             * 默认拥有本组织的数据权限
             */
            this.permissionCode = org.getCode();
        }else{
            /**
             * 如果组织权限不在本机构的范围内，则回收权限到默认状态
             */
            if (!this.permissionCode.startsWith(org.getCode())){
                this.permissionCode = org.getCode();
            }
        }

        return this;
    }
    private void copy(SysOperator so){

        if (so.org != null){
            setOrg(so.getOrg());
        }

        if(!StringUtils.isEmpty(so.account)){
            this.account = so.account;
        }

        if(!StringUtils.isEmpty(so.mobile)){
            this.mobile = so.mobile;
        }

        if(!StringUtils.isEmpty(so.email)){
            this.email = so.email;
        }

        if(!StringUtils.isEmpty(so.name)){
            this.name = so.name;
        }

        if(so.status != null){
            this.status = so.status;
        }

        this.title = so.title;
        this.avatar = so.avatar;
        this.signature = so.signature;
        this.remark = so.remark;

    }

    public void update(SysOperator so){
        if (so == null){
            return;
        }
        copy(so);
    }
}
