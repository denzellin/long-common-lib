package com.isylph.operator.api.beans.grouplist;

import com.isylph.basis.base.BaseCmd;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class GroupMemberUpdateCmd extends BaseCmd {

    private Long id;

    private List<Long> memberIds;


}
