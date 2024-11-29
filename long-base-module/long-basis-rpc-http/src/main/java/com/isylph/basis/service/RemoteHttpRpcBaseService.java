package com.isylph.basis.service;


import com.isylph.basis.beans.HttpRetData;
import com.isylph.basis.consts.BaseErrorConsts;
import com.isylph.basis.controller.exception.ReturnException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RemoteHttpRpcBaseService{

    public <T> T RpcProc(HttpRetData<T> ret) {
        if (null == ret) {
            log.error("Failed to call rpc for info");
            throw new ReturnException(BaseErrorConsts.RET_RPC_ERR);
        }
        if (!BaseErrorConsts.RET_OK.equals(ret.getCode())) {

            log.error("Failed to get the information: {}", ret);
            throw new ReturnException(ret.getCode());
        }

        if (null == ret.getTotal()) {
            return ret.getData();
        } else {
            return ret.getData();
        }
    }
    /**
    public <T> void RpcProc(HttpRetData<List<T>> ret, Page<T> page) {
        if (null == ret) {
            log.error("Failed to call rpc for info");
            throw new ReturnException(BaseErrorConsts.RET_RPC_ERR);
        }
        if (!BaseErrorConsts.RET_OK.equals(ret.getCode())) {

            log.error("Failed to get the information: {}", ret);
            throw new ReturnException(ret.getCode());
        }
        page.setRecords(ret.getData());
        page.setTotal(ret.getTotal().longValue());
    }

    */
}
