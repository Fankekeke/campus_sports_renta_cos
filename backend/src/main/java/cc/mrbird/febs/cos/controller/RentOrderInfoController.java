package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.RentOrderInfo;
import cc.mrbird.febs.cos.service.IRentOrderInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 租借订单
 *
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/rent-order-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RentOrderInfoController {

    private final IRentOrderInfoService rentOrderInfoService;

    /**
     * 分页获取租借订单
     *
     * @param page          分页对象
     * @param rentOrderInfo 租借订单
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<RentOrderInfo> page, RentOrderInfo rentOrderInfo) {
        return R.ok(rentOrderInfoService.queryRentOrderPage(page, rentOrderInfo));
    }

    /**
     * 计算订单金额
     *
     * @param rentOrderInfo 订单信息
     * @return 结果
     */
    @PostMapping("/calculate")
    public R calculateOrderPrice(RentOrderInfo rentOrderInfo) throws FebsException {
        return R.ok(rentOrderInfoService.calculateOrderPrice(rentOrderInfo));
    }

    /**
     * 校验用户是否还有未归还订单
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/checkOrderByUser")
    public R checkOrderByUser(@RequestParam("userId") Integer userId) throws FebsException {
        return R.ok(rentOrderInfoService.checkOrderByUser(userId));
    }

    /**
     * 器材归还
     *
     * @param orderCode 订单编号
     * @return 结果
     */
    @GetMapping("/returnDevice")
    public R returnDevice(@RequestParam("orderCode") String orderCode) {
        return R.ok(rentOrderInfoService.returnDevice(orderCode));
    }

    /**
     * 器材归还审核入库
     *
     * @param orderCode 订单编号
     * @return 结果
     */
    @GetMapping("/checkReturnDevice")
    public R checkReturnDevice(@RequestParam("orderCode") String orderCode) {
        return R.ok(rentOrderInfoService.checkReturnDevice(orderCode));
    }

    /**
     * 首页统计数据
     *
     * @return 结果
     */
    @GetMapping("/homeData")
    public R homeData() {
        return R.ok(rentOrderInfoService.homeData());
    }

    /**
     * 支付完成后回调
     *
     * @param orderCode 租借订单编号
     * @return 结果
     */
    @GetMapping("/callbackPayment")
    public R callbackPayment(@RequestParam("orderCode") String orderCode) {
        return R.ok(rentOrderInfoService.callbackPayment(orderCode));
    }

    /**
     * 租借订单详情
     *
     * @param id 器械ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(rentOrderInfoService.getById(id));
    }

    /**
     * 租借订单列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(rentOrderInfoService.list());
    }

    /**
     * 新增租借订单
     *
     * @param rentOrderInfo 租借订单
     * @return 结果
     */
    @PostMapping
    public R save(RentOrderInfo rentOrderInfo) {
        return R.ok(rentOrderInfoService.addRentOrder(rentOrderInfo));
    }

    /**
     * 修改租借订单
     *
     * @param rentOrderInfo 租借订单
     * @return 结果
     */
    @PutMapping
    public R edit(RentOrderInfo rentOrderInfo) {
        return R.ok(rentOrderInfoService.updateById(rentOrderInfo));
    }

    /**
     * 删除租借订单
     *
     * @param ids ids
     * @return 租借订单
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(rentOrderInfoService.removeByIds(ids));
    }


}
