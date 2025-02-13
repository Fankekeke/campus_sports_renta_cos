package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.CreditRecordInfo;
import cc.mrbird.febs.cos.entity.MessageInfo;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.service.ICreditRecordInfoService;
import cc.mrbird.febs.cos.service.IMessageInfoService;
import cc.mrbird.febs.cos.service.IUserInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/user-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserInfoController {

    private final IUserInfoService userInfoService;

    private final ICreditRecordInfoService creditRecordInfoService;

    private final IMessageInfoService messageInfoService;

    /**
     * 分页获取用户信息
     *
     * @param page     分页对象
     * @param userInfo 用户信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<UserInfo> page, UserInfo userInfo) {
        return R.ok(userInfoService.selectUserPage(page, userInfo));
    }

    /**
     * 设置用户积分
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/setUserCreditScore")
    public R setUserCreditScore(@RequestParam("userId") Integer userId) {
        // 获取用户信息
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, userId));
        if (userInfo != null) {
            int creditScore = 60 - userInfo.getCreditScore();
            // 添加用户信用积分记录
            CreditRecordInfo creditRecordInfo = new CreditRecordInfo();
            creditRecordInfo.setUserId(userInfo.getId());
            creditRecordInfo.setType("1");
            creditRecordInfo.setScore(creditScore);
            creditRecordInfo.setContent("系统赠送");
            creditRecordInfo.setAfterScore(60);
            creditRecordInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
            creditRecordInfoService.save(creditRecordInfo);
            userInfo.setCreditScore(60);

            // 添加消息通知
            MessageInfo messageInfo = new MessageInfo();
            messageInfo.setUserId(userInfo.getId());
            messageInfo.setContent("您的信用积分已经重置，系统赠送信用积分60分");
            messageInfo.setStatus("0");
            messageInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
            messageInfoService.save(messageInfo);
            return R.ok(userInfoService.updateById(userInfo));
        }
        return R.ok();
    }

    /**
     * 用户信息详情
     *
     * @param id 用户ID
     * @return 结果
     */
    @GetMapping("/detail/{userId}")
    public R userDetail(@PathVariable("userId") Integer id) {
        return R.ok(userInfoService.userDetail(id));
    }

    /**
     * 用户信息详情
     *
     * @param id 用户ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(userInfoService.getById(id));
    }

    /**
     * 用户信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(userInfoService.list());
    }

    /**
     * 新增用户信息
     *
     * @param userInfo 用户信息
     * @return 结果
     */
    @PostMapping
    public R save(UserInfo userInfo) {
        return R.ok(userInfoService.save(userInfo));
    }

    /**
     * 修改用户信息
     *
     * @param userInfo 用户信息
     * @return 结果
     */
    @PutMapping
    public R edit(UserInfo userInfo) {
        return R.ok(userInfoService.updateById(userInfo));
    }

    /**
     * 删除用户信息
     *
     * @param ids ids
     * @return 用户信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(userInfoService.removeByIds(ids));
    }
}
