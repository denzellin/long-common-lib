package com.isylph.oss.domain.service.impl;

import com.isylph.basis.controller.exception.ReturnException;
import com.isylph.oss.api.consts.Errors;
import com.isylph.oss.domain.service.OssFileService;
import com.isylph.oss.domain.types.ImageFileType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * 设备类型 服务实现类
 * </p>
 *
 * @author denzel.lin
 * @since 2024-01-08
 */

@Slf4j
@Service
public class OssFileServiceImpl implements OssFileService {


    @Override
    public String checkImageTypeFileSuffix(ByteArrayOutputStream outputStream) {
        ImageFileType type = getImageType(outputStream);
        if (type == null) {
            throw new ReturnException(Errors.UPLOAD_INVALID_SUFFIX);
        }
        return type.getSuffix();
    }


    @Override
    public ByteArrayOutputStream inputStreamCache(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buffer)) > -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return byteArrayOutputStream;
    }



    /**
     * 获取图片文件类型
     */
    private ImageFileType getImageType(ByteArrayOutputStream outputStream) {
        // 获取文件头
        String fileHead = getFileHeader(outputStream);

        if (fileHead != null && fileHead.length() > 0) {
            fileHead = fileHead.toUpperCase();
            ImageFileType[] imageFileTypes = ImageFileType.values();

            for (ImageFileType type : imageFileTypes) {
                if (fileHead.startsWith(type.getValue())) {
                    return type;
                }
            }
        }

        return null;
    }

    /**
     * 读取文件头
     */
    private String getFileHeader(ByteArrayOutputStream outputStream) {
        byte[] b = new byte[28];
        System.arraycopy(outputStream.toByteArray(), 0, b, 0, b.length);
        return bytesToHex(b);
    }


    /**
     * 将字节数组转换成16进制字符串
     */
    private String bytesToHex(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte aSrc : src) {
            int v = aSrc & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


}
