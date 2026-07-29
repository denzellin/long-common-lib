package com.vivavot.basis.application;



import com.vivavot.basis.base.BaseCmd;
import com.vivavot.basis.beans.BaseDTO;

import java.util.List;

/**

 */
public interface BaseBeanAssembler<CV extends BaseCmd, UV extends BaseCmd, V extends BaseDTO, T> {

    /**
     * 输入do返回dto
     *
     * @param t
     * @return v
     */
    V doToDTO(T t);
    List<V> doToDTO(List<T> list);


    /**
     * 输入saveCmd返回do
     *
     * @param cv
     * @return t
     */
    T saverCmdToDO(CV cv);

    /**
     * 输入updateCmd返回do
     *
     * @param uv
     * @return t
     */
    T updaterCmdToDO(UV uv);
}
