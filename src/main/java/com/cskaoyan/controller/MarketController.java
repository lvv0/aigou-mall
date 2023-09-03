package com.cskaoyan.controller;

import com.cskaoyan.model.bean.Brand;
import com.cskaoyan.model.bean.Issue;
import com.cskaoyan.model.bean.Keyword;
import com.cskaoyan.model.bo.*;
import com.cskaoyan.model.bo.BrandListBo;
import com.cskaoyan.model.bo.CategoryBo;
import com.cskaoyan.model.bo.CategoryBoChildren;
import com.cskaoyan.model.bo.OrderListBo;
import com.cskaoyan.model.vo.*;
import com.cskaoyan.service.MarketService;
import com.cskaoyan.utils.ValidationUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author huxudong
 * @version 1.0
 * @date 2021/8/11 18:50
 */
@RestController
@RequestMapping("/admin")
public class MarketController {

    @Autowired
    MarketService marketService;

    @RequestMapping("/region/list")
    public RegionVo regionList() {
        RegionVo regionVo = new RegionVo();
        regionVo.setErrno(0);
        regionVo.setErrmsg("成功");
        List<RegionVo.DataBean> list = marketService.regionList();
        regionVo.setData(list);
        return regionVo;
    }

    @RequestMapping("/brand/list")
    public BaseRespVo brandList(BrandListBo bo) {
        BrandListVo listVo = marketService.brandList(bo);
        return BaseRespVo.ok(listVo);
    }

    @RequestMapping("/brand/delete")
    public BaseRespVo brandDelete(@RequestBody Brand brand) {
        int id = brand.getId();
        int affectedRows = marketService.deleteBrandById(id);
        if (affectedRows == 1)
            return BaseRespVo.ok();
        return BaseRespVo.fail("删除异常");
    }

    @RequestMapping("/brand/update")
    public BaseRespVo brandUpdate(@Validated @RequestBody BrandCreateBo bo,BindingResult result) {
        BaseRespVo baseRespVo = ValidationUtils.dealWithFiledError(result);
        if(baseRespVo.getErrno()==640){
            return baseRespVo;
        }
        Brand brand = new Brand();
        BeanUtils.copyProperties(bo,brand);
        brand.setFloorPrice(new BigDecimal(bo.getFloorPrice()));
        int affectedRows = marketService.updateBrandById(brand);
        if (affectedRows == 1)
            return BaseRespVo.ok();
        return BaseRespVo.fail("修改异常");
    }

    @RequestMapping("/brand/create")
    public BaseRespVo brandCreate(@Validated @RequestBody BrandCreateBo bo,BindingResult result) {
        BaseRespVo baseRespVo = ValidationUtils.dealWithFiledError(result);
        if(baseRespVo.getErrno()==640){
            return baseRespVo;
        }
        Brand brand = new Brand();
        BeanUtils.copyProperties(bo,brand);
        brand.setFloorPrice(new BigDecimal(bo.getFloorPrice()));
        AddBrandVo affectedRows = marketService.createBrand(brand);
        return BaseRespVo.ok(affectedRows);
    }

    @RequestMapping("/category/list")
    public BaseRespVo categoryList() {
        List<CategoryBo> list = marketService.categoryList();
        return BaseRespVo.ok(list);
    }

    @RequestMapping("/category/l1")
    public BaseRespVo categoryL1() {
        List<CategoryL1> l1List = marketService.categoryL1List();
        return BaseRespVo.ok(l1List);
    }

    @RequestMapping("/category/create")
    public BaseRespVo categoryCreate(@RequestBody CategoryBoChildren children) {
        // 如果在创建二级目录而未指定一级目录，则创建失败
        if ("L2".equals(children.getLevel()) && children.getPid() == 0) {
            return BaseRespVo.fail("未指定一级目录，创建失败");
        }
        CreateCategoryVo vo = marketService.createCategory(children);
        return BaseRespVo.ok(vo);

    }

    @RequestMapping("/category/delete")
    public BaseRespVo categoryDelete(@RequestBody CategoryBo categoryBo) {
        boolean deleted = marketService.deleteCategoryById(categoryBo.getId());
        if (deleted) {
            return BaseRespVo.ok();
        }
        return BaseRespVo.fail("删除失败");
    }

    @RequestMapping("keyword/list")
    public BaseRespVo list(MarketKeyWordListBo marketKeyWordListBo){
        MarketKeyWordListVo marketKeyWordListVo = marketService.list(marketKeyWordListBo);
        if (marketKeyWordListVo != null){
            return BaseRespVo.ok(marketKeyWordListVo);
        }else return BaseRespVo.fail("服务器繁忙!");
    }

    @RequestMapping("keyword/create")
    public BaseRespVo create(@RequestBody MarketKeyWordCreateBo marketKeyWordCreateBo){
        Keyword keyword = marketService.create(marketKeyWordCreateBo);
        if (keyword != null){
            return BaseRespVo.ok(keyword);
        }else return BaseRespVo.fail("服务器繁忙!");
    }

    @RequestMapping("keyword/update")
    public BaseRespVo update(@RequestBody Keyword keyword){
        Keyword data = marketService.update(keyword);
        if (data != null){
            return BaseRespVo.ok(data);
        }else return BaseRespVo.fail("服务器繁忙!");
    }

    @RequestMapping("keyword/delete")
    public BaseRespVo delete(@RequestBody Keyword keyword){
        if (marketService.delete(keyword)){
            return BaseRespVo.ok();
        }else return BaseRespVo.fail("服务器繁忙!");

    }

    @RequestMapping("/category/update")
    public BaseRespVo categoryUpdate(@RequestBody CategoryBo bo) {
        if (bo.getPid() == null&&"L2".equals(bo.getLevel())) {
            return BaseRespVo.fail2("未指定父类目");
        }
        if (bo.getChildren()!=null&&bo.getChildren().size()>0&&"L2".equals(bo.getLevel())){
            return BaseRespVo.fail("当前类目有子类目，不允许修改");
        }
        boolean flag = marketService.updateCategory(bo);
        if (flag)
            return BaseRespVo.ok();
        return BaseRespVo.fail("修改失败");
    }

    @RequestMapping("/order/list")
    public BaseRespVo orderList(OrderListBo orderListBo){
        OrderListVo orderListVo = marketService.orderList(orderListBo);
        return BaseRespVo.ok(orderListVo);
    }

    @RequestMapping("/order/detail")
    public BaseRespVo orderDetail(Integer id){
        OrderDetailVo vo = marketService.selectOrderDetailById(id);
        return BaseRespVo.ok(vo);
    }

    // TODO 不清楚具体的逻辑(退款)
    @RequestMapping("/order/refund")
    public BaseRespVo orderRefund(Integer orderId, BigDecimal refundMoney){
//        boolean flag = marketService.orderRefund(orderId,refundMoney);
        return BaseRespVo.fail("退款失败");
    }

    // TODO 不清楚具体的逻辑(发货)
    @RequestMapping("/order/ship")
    public BaseRespVo orderShip(@Validated @RequestBody OrderShipBo bo, BindingResult bindingResult){
        BaseRespVo baseRespVo = ValidationUtils.dealWithFiledError(bindingResult);
        if(baseRespVo.getErrno()==640){
            return baseRespVo;
        }
        boolean flag = marketService.orderShip(bo);
        if (flag)
            return BaseRespVo.ok("发货成功");
        return BaseRespVo.fail("发货失败");
    }

    @RequestMapping("/issue/list")
    public BaseRespVo issueList(Integer page,Integer limit,String question,String sort,String order){
        IssueListVo vo = marketService.issueList(page,limit,question,sort,order);
        return BaseRespVo.ok(vo);
    }

    @RequestMapping("/issue/delete")
    public BaseRespVo issueDelete(@RequestBody Issue issue){
        boolean flag = marketService.issueDelete(issue.getId());
        if (flag)
            return BaseRespVo.ok("删除成功");
        return BaseRespVo.fail("删除失败");
    }

    @RequestMapping("/issue/create")
    public BaseRespVo issueCreate(@RequestBody IssueCreateBo bo){
        Issue issue = marketService.issueCreate(bo);
        return BaseRespVo.ok(issue);
    }

    @RequestMapping("/issue/update")
    public BaseRespVo issueUpdate(@RequestBody Issue issue){
        Issue issue1 = marketService.issueUpdate(issue);
        return BaseRespVo.ok(issue1);
    }

    //该接口为商品管理中的商品评论，不用实现，直接返回fail
    @RequestMapping("/order/reply")
    public BaseRespVo reply(@RequestBody MarketOrderReplyBo marketOrderReplyBo){
        return BaseRespVo.fail(622,"订单商品已回复！");
    }

}
