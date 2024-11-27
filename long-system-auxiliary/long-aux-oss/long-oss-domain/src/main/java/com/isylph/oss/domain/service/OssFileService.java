package com.isylph.oss.domain.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author denzel.lin
 * @since 2024-01-08
 */
public interface OssFileService {

    String checkImageTypeFileSuffix(ByteArrayOutputStream outputStream);

    ByteArrayOutputStream inputStreamCache(InputStream inputStream);

}
