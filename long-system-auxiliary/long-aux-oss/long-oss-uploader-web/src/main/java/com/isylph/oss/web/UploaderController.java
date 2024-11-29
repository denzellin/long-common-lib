package com.isylph.oss.web;


import com.isylph.basis.beans.HttpRetData;
import com.isylph.basis.controller.exception.ReturnException;
import com.isylph.basis.types.FileData;
import com.isylph.oss.api.consts.Errors;
import com.isylph.oss.application.OssApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Tag(name = "系统功能", description = "100")
@RestController()
@RequestMapping("/upload")
public class UploaderController {

    @Autowired
    private OssApplicationService ossApplicationService;

    @Operation(summary = "上传图片，按模块分组", method = "/*", operationId = "001")
    @PostMapping(value = "/{module}/{format}")
    public HttpRetData uploadImage(@Parameter(description = "通过指定模块来设置文件保存的目录") @PathVariable String module,
                                   @Parameter(description = "上传图片时为img, 上传PDF文件时是pdf, 其他格式时随意") @PathVariable String format,
                                   @Parameter(description = "指定文件保存的子目录, 为\"\"时没有子目录") @RequestParam(required = false) String subdirectory,
                                   @RequestParam(value = "file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new ReturnException(Errors.UPLOAD_FILE_MUST_NOT_EMPTY);
        }
        FileData f = null;
        if (Objects.equals(format, "img")){
            f = ossApplicationService.saveImage(module, subdirectory, file.getOriginalFilename(), file.getInputStream());
        }else if (Objects.equals(format, "pdf")){
            f = ossApplicationService.savePdf(module, subdirectory, file.getOriginalFilename(), file.getInputStream());
        }else{
            f = ossApplicationService.saveGeneralFile(module, subdirectory, file.getOriginalFilename(), file.getInputStream());
        }

        return HttpRetData.success(f);
    }
}
