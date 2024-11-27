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
 * 收货人
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
public class Consignee  extends BaseType {

    private String name;

    private Mobile mobile;

    private Address address;
}
