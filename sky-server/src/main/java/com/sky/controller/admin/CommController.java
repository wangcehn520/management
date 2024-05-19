package com.sky.controller.admin;

import com.github.xiaoymin.knife4j.core.util.StrUtil;
import com.sky.constant.FileConstant;
import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterators;
import java.util.UUID;

/**
 * @author LeLe
 * @date 2024/5/7 0:40
 * @Description:
 */
@RestController
@RequestMapping("/admin/common")
@ApiOperation("通用接口")
@Slf4j
public class CommController {

    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file) throws IOException {
        log.info("上传的文件: {}",file);
        List<String> stringList = creatFilename(file.getOriginalFilename());
        try {
            file.transferTo(new File(stringList.get(0),stringList.get(1)));
            String path = "http://localhost/" + "img" + File.separator + stringList.get(2) + File.separator + stringList.get(1);
            return Result.success(path);
        } catch (Exception e) {
            log.error("文件上传失败：{}",e.getMessage());
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }

    private List<String> creatFilename(String originalFilename){
        List<String> list = new ArrayList<>();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + suffix;
        String folderName = LocalDate.now().toString();
        File file = new File(FileConstant.FILE_PATH + folderName);
        if (!file.exists()){
            file.mkdir();
        }
        list.add(FileConstant.FILE_PATH + folderName + File.separator);
        list.add(fileName);
        list.add(folderName);
        return list;
    }
}
