package com.isylph.basis.types;

import com.isylph.basis.base.BaseType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 * 地址
 * </p>
 *
 * @author denzel.lin
 * @since 2020-02-12
 */
@ToString
@Getter
@EqualsAndHashCode(callSuper = true)
@Builder(toBuilder=true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
public class Address  extends BaseType {

    /**
     * 国家地区
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String district;

    /**
     * 街道
     */
    private String street;

    /**
     * 地址
     */
    private String address;

    /**
     * 邮编
     */
    private String zipCode;
}
