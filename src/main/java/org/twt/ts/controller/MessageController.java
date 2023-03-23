package org.twt.ts.controller;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.twt.ts.dto.BaseID;
import org.twt.ts.dto.MessageInfo;
import org.twt.ts.dto.PrivateMessageInfo;
import org.twt.ts.dto.Result;
import org.twt.ts.exception.InvalidFileExtException;
import org.twt.ts.exception.InvalidParamsException;
import org.twt.ts.exception.NoPrivilegesException;
import org.twt.ts.exception.UserNotExistException;
import org.twt.ts.model.repository.MessageRepo;
import org.twt.ts.model.repository.PrivateMessageRepo;
import org.twt.ts.service.MessageService;
import org.twt.ts.utils.FileTypeUtil;
import org.twt.ts.utils.ReturnCode;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
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
    private final PrivateMessageRepo privateMessageRepo;

    public MessageController(MessageRepo messageRepo,
                             PrivateMessageRepo privateMessageRepo) {
        this.messageRepo = messageRepo;
        this.privateMessageRepo = privateMessageRepo;
    }


    String verifyAndSaveFile(MultipartFile file) throws IOException, InvalidFileExtException {
        if (file.isEmpty()) Result.error(ReturnCode.UnknownError);
        String ext = FileTypeUtil.getFileTypeBySuffix(Objects.requireNonNull(file.getOriginalFilename()));
        System.out.println(ext);
        if (!acceptExt.contains(ext)) throw new InvalidFileExtException();
        String name = UUID.randomUUID() + "." + ext;
        String path = filePath + "/" + name;
        File dest = new File(path);
        file.transferTo(dest);
        return name;
    }

    @PostMapping("send")
    @Transactional
    public Result sendMessage(@RequestParam("file") MultipartFile file,
                              @RequestParam("title") String title,
                              @RequestParam("desc") String desc,
                              @RequestParam("disabled_list") String disabled
    )
            throws IOException, NoPrivilegesException, InvalidFileExtException {

        String path = verifyAndSaveFile(file);
        messageService.sendMessage(
                path,
                new MessageInfo(
                        title,
                        desc,
                        Objects.equals(disabled, "") ? null : Arrays.stream(disabled.split(",")).map(Integer::parseInt).toList()
                )
        );

        return Result.success();
    }

    @PostMapping("sendPrivateMessage")
    @Transactional
    public Result sendPrivateMessage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("desc") String desc,
            @RequestParam("receiver_id") String receiverId) throws IOException, NoPrivilegesException, UserNotExistException, InvalidFileExtException {
        String path = verifyAndSaveFile(file);
        messageService.sendPrivateMessage(path,
                new PrivateMessageInfo(
                        title,
                        desc,
                        Integer.parseInt(receiverId)
                ));
        return Result.success();
    }

    @GetMapping("mine")
    public Result getMine() throws NoPrivilegesException {
        return Result.success(messageService.getMine());
    }


    @GetMapping("privateList")
    public Result getPrivateMessageList() throws NoPrivilegesException {
        return Result.success(
                messageService.getPrivateList()
        );
    }

    @GetMapping("getLikeList")
    public Result getMessageLikeList(@RequestBody BaseID id) throws InvalidParamsException {
        return Result.success(messageService.getLikesById(id.getId()));
    }

    @PostMapping("updateLike")
    public Result updateLike(@RequestBody BaseID id) throws NoPrivilegesException, InvalidParamsException {
        messageService.updateLikes(id.getId());
        return Result.success();
    }

    @GetMapping("getLikeStatus")
    public Result getLikeStatus(@RequestBody BaseID id) throws NoPrivilegesException, InvalidParamsException {
        return Result.success(
                messageService.isLiked(id.getId())
        );
    }

    @PostMapping("delete")
    public Result deleteMessage(@RequestBody BaseID id) throws NoPrivilegesException {

        messageService.deleteMessage(id.getId());
        return Result.success();
    }

    @PostMapping("deletePrivate")
    public Result deletePrivateMessage(@RequestBody BaseID id) {

        messageService.deletePrivateMessage(id.getId());
        return Result.success();
    }

    @PostMapping("get")
    public Result getMessage(@RequestBody BaseID id) throws InvalidParamsException {

        return Result.success(
                messageRepo.findMessageById(id.getId())
                        .orElseThrow(InvalidParamsException::new)
        );
    }

    @PostMapping("getPrivate")
    public Result getPrivateMessage(@RequestBody BaseID id) throws InvalidParamsException {

        return Result.success(
                privateMessageRepo.findPrivateMessageByPid(id.getId())
                        .orElseThrow(InvalidParamsException::new)
        );
    }
}
