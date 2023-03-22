package org.twt.ts.controller;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.twt.ts.dto.MessageInfo;
import org.twt.ts.dto.PrivateMessageInfo;
import org.twt.ts.dto.Result;
import org.twt.ts.exception.InvalidParamsException;
import org.twt.ts.exception.NoPrivilegesException;
import org.twt.ts.exception.UserNotExistException;
import org.twt.ts.model.repository.MessageRepo;
import org.twt.ts.service.MessageService;
import org.twt.ts.utils.FileTypeUtil;
import org.twt.ts.utils.ReturnCode;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("message")
public class MessageController {

    @Resource
    private MessageService messageService;

    @GetMapping("list")
    public Result getList() throws NoPrivilegesException {
        return Result.success(messageService.getList());
    }

    @Value("${message.path}")
    private String filePath;


    @Value("${message.acceptExt}")
    private List<String> acceptExt;
    private final MessageRepo messageRepo;

    public MessageController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }


    String verifyAndSaveFile(MultipartFile file) throws IOException, InvalidParamsException {
        if (file.isEmpty()) Result.error(ReturnCode.UnknownError);
        String ext = FileTypeUtil.getFileTypeBySuffix(Objects.requireNonNull(file.getOriginalFilename()));
        if (!acceptExt.contains(ext)) throw new InvalidParamsException();
        String path = filePath + "/" + UUID.randomUUID() + "." + ext;
        File dest = new File(path);
        file.transferTo(dest);
        return path;
    }

    @PostMapping("send")
    @Transactional
    public Result sendMessage(@RequestParam("file") MultipartFile file, @RequestParam("data") MessageInfo messageInfo) throws IOException, InvalidParamsException, NoPrivilegesException {
        String path = verifyAndSaveFile(file);

        messageService.sendMessage(path, messageInfo);

        return Result.success();
    }

    @PostMapping("sendPrivateMessage")
    @Transactional
    public Result sendPrivateMessage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("data") PrivateMessageInfo messageInfo) throws IOException, InvalidParamsException, NoPrivilegesException, UserNotExistException {
        String path = verifyAndSaveFile(file);
        messageService.sendPrivateMessage(path, messageInfo);
        return Result.success();
    }

    @GetMapping("privateList")
    public Result getPrivateMessageList() throws NoPrivilegesException {
        return Result.success(
                messageService.getPrivateList()
        );
    }

    @GetMapping("getLikeList")
    public Result getMessageLikeList(@RequestParam(name = "id") String id) throws InvalidParamsException {
        return Result.success(messageService.getLikesById(id));
    }

    @PostMapping("updateLike")
    public Result updateLike(@RequestParam(name = "id") String id) throws NoPrivilegesException, InvalidParamsException {
        messageService.updateLikes(id);
        return Result.success();
    }

    @GetMapping("getLikeStatus")
    public Result getLikeStatus(@RequestParam(name = "id") String id) throws NoPrivilegesException, InvalidParamsException {
        return Result.success(
                messageService.isLiked(id)
        );
    }
}
