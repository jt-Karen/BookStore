package com.bookstore.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bookstore.entity.order.OrderData;
import com.bookstore.entity.order.OrderHeader;
import com.bookstore.entity.pick.PickHeader;
import com.bookstore.mapper.order.OrderHeaderMapper;
import com.bookstore.service.order.OrderDataService;
import com.bookstore.service.order.OrderHeaderService;
import com.bookstore.service.pick.PickHeaderService;
import com.bookstore.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
public class OrderController {
    @Autowired
    private PickHeaderService pickHeaderService;
    @Autowired
    private OrderHeaderService orderHeaderService;
    @Autowired
    private OrderDataService orderDataService;
    @Autowired
    private OrderHeaderMapper orderHeaderMapper;

    @RequestMapping("/setorderbypick")
    public String getViewSetByPick() {
        return "setOrderByPick";
    }

    @RequestMapping("/setorderbyinventory")
    public String getViewSetByInventory() {
        return "setOrderByInventory";
    }

    @RequestMapping("/order")
    public String getView() {
        return "order";
    }

    @RequestMapping("/addorder")
    public String getViewAdd() {
        return "addOrder";
    }

    @RequestMapping("/editorder")
    public String getViewEdit() {
        return "editOrder";
    }

    @RequestMapping("/orderdata")
    @ResponseBody
    public DataVO getOrder(){
        return orderHeaderService.find();
    }

    @RequestMapping("/orderdatasearch")
    @ResponseBody
    public DataVO getOrderSearch(@RequestParam String search){
        DataVO dataVO = new DataVO<>();
        dataVO.setCode(0);
        dataVO.setMsg(" ");
        dataVO.setCount(orderHeaderMapper.selectCount(null));
        QueryWrapper<OrderHeader> orderHeaderQueryWrapper = new QueryWrapper<>();
        orderHeaderQueryWrapper.like("number_view",search).or().like("relate_number",search).or().like("account",search).or().like("time",search);
        List<OrderHeader> orderHeaderList = orderHeaderMapper.selectList(orderHeaderQueryWrapper);
        dataVO.setData(orderHeaderList);
        return dataVO;
    }

    @RequestMapping("/submitorderbypick")
    @ResponseBody
    public void submitOrderByPick(@RequestParam Integer number, @RequestParam String account, @RequestParam String publisherId, @RequestParam String tableData){
        OrderHeader orderHeader = new OrderHeader();
        String pickNumberView = pickHeaderService.getNumberView(number);
        orderHeader.setRelateNumber(pickNumberView);
        orderHeader.setAccount(account);
        orderHeader.setPublisherId(publisherId);
        orderHeaderService.save(orderHeader);
        Integer orderNumber = orderHeader.getNumber();
        String orderNumberView = "order_" + orderNumber;
        orderHeader.setNumberView(orderNumberView);
        orderHeaderService.updateById(orderHeader);

        OrderData orderData = new OrderData();
        JSONArray jsonArray = JSONArray.parseArray(tableData);
        for(int i = 0; i < jsonArray.size(); i++){
            JSONObject obj= jsonArray.getJSONObject(i);
            String bookId = (String) obj.get("bookId");
            String orders = (String) obj.get("orders");
            orderData.setNumber(orderNumber);
            orderData.setBookId(bookId);
            orderData.setOrders(orders);
            orderDataService.save(orderData);
        }

        PickHeader pickHeader = new PickHeader();
        pickHeader.setNumber(number);
        pickHeader.setStateOrder("已处理");
        pickHeaderService.updateById(pickHeader);
    }

    @RequestMapping("/submitorder")
    @ResponseBody
    public void submitOrder(@RequestParam String account, @RequestParam String publisherId, @RequestParam String tableData){
        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setAccount(account);
        orderHeader.setPublisherId(publisherId);
        orderHeaderService.save(orderHeader);
        Integer orderNumber = orderHeader.getNumber();
        String orderNumberView = "order_" + orderNumber;
        orderHeader.setNumberView(orderNumberView);
        orderHeaderService.updateById(orderHeader);

        OrderData orderData = new OrderData();
        JSONArray jsonArray = JSONArray.parseArray(tableData);
        for(int i = 0; i < jsonArray.size(); i++){
            JSONObject obj= jsonArray.getJSONObject(i);
            String bookId = (String) obj.get("bookId");
            String orders = (String) obj.get("orders");
            orderData.setNumber(orderNumber);
            orderData.setBookId(bookId);
            orderData.setOrders(orders);
            orderDataService.save(orderData);
        }
    }

    @RequestMapping("/updateorder")
    @ResponseBody
    public void updateOrder(@RequestParam Integer number, @RequestParam String tableData){
        QueryWrapper<OrderData> orderDataQueryWrapper = new QueryWrapper<>();
        orderDataQueryWrapper.eq("number",number);
        orderDataService.remove(orderDataQueryWrapper);

        OrderData orderData = new OrderData();
        JSONArray jsonArray = JSONArray.parseArray(tableData);
        for(int i = 0; i < jsonArray.size(); i++){
            JSONObject obj= jsonArray.getJSONObject(i);
            String bookId = (String) obj.get("bookId");
            String orders = (String) obj.get("orders");
            orderData.setNumber(number);
            orderData.setBookId(bookId);
            orderData.setOrders(orders);
            orderDataService.save(orderData);
        }
    }

    @RequestMapping("/delorder")
    @ResponseBody
    public void delOrder(@RequestParam Integer number){
        QueryWrapper<OrderData> orderDataQueryWrapper = new QueryWrapper<>();
        orderDataQueryWrapper.eq("number",number);
        orderDataService.remove(orderDataQueryWrapper);

        QueryWrapper<OrderHeader> orderHeaderQueryWrapper = new QueryWrapper<>();
        orderHeaderQueryWrapper.eq("number",number);
        orderHeaderService.remove(orderHeaderQueryWrapper);
    }
}
