package com.bookstore.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bookstore.entity.pick.PickData;
import com.bookstore.entity.pick.PickHeader;
import com.bookstore.entity.sale.SaleHeader;
import com.bookstore.mapper.pick.PickHeaderMapper;
import com.bookstore.service.pick.PickDataService;
import com.bookstore.service.pick.PickHeaderService;
import com.bookstore.service.sale.SaleHeaderService;
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
public class PickController {
    @Autowired
    private PickHeaderService pickHeaderService;
    @Autowired
    private PickDataService pickDataService;
    @Autowired
    private SaleHeaderService saleHeaderService;
    @Autowired
    private PickHeaderMapper pickHeaderMapper;

    @RequestMapping("/setpick")
    public String getViewSet() {
        return "setPick";
    }

    @RequestMapping("/pick")
    public String getView() {
        return "pick";
    }

    @RequestMapping("/checkpick")
    public String getViewCheck() {
        return "checkPick";
    }

    @RequestMapping("/pickquery")
    public String getViewQuery() {
        return "pickQuery";
    }

    @RequestMapping("/pickdata")
    @ResponseBody
    public DataVO getPick(){
        return pickHeaderService.find();
    }

    @RequestMapping("/pickquerydata")
    @ResponseBody
    public DataVO getPickQuery(){
        return pickHeaderService.findQuery();
    }

    @RequestMapping("/pickdatasearch")
    @ResponseBody
    public DataVO getPickSearch(@RequestParam String search){
        DataVO dataVO = new DataVO<>();
        dataVO.setCode(0);
        dataVO.setMsg(" ");
        dataVO.setCount(pickHeaderMapper.selectCount(null));
        QueryWrapper<PickHeader> pickHeaderQueryWrapper = new QueryWrapper<>();
        pickHeaderQueryWrapper.like("number_view",search).or().like("relate_number",search).or().like("account",search).or().like("time",search).or().like("state_output",search);
        List<PickHeader> pickHeaderList = pickHeaderMapper.selectList(pickHeaderQueryWrapper);
        dataVO.setData(pickHeaderList);
        return dataVO;
    }

    @RequestMapping("/pickquerydatasearch")
    @ResponseBody
    public DataVO getPickQuerySearch(@RequestParam String search){
        DataVO dataVO = new DataVO<>();
        dataVO.setCode(0);
        dataVO.setMsg(" ");
        dataVO.setCount(pickHeaderMapper.selectCount(null));
        QueryWrapper<PickHeader> pickHeaderQueryWrapper = new QueryWrapper<>();
        pickHeaderQueryWrapper.like("number_view",search).or().like("relate_number",search).or().like("account",search).or().like("time",search).or().like("state_order",search);
        List<PickHeader> pickHeaderList = pickHeaderMapper.selectList(pickHeaderQueryWrapper);
        dataVO.setData(pickHeaderList);
        return dataVO;
    }

    @RequestMapping("/submitpick")
    @ResponseBody
    public void submitPick(@RequestParam Integer number, @RequestParam String account, @RequestParam Integer customerId, @RequestParam String tableData){
        PickHeader pickHeader = new PickHeader();
        String saleNumberView = saleHeaderService.getNumberView(number);
        pickHeader.setRelateNumber(saleNumberView);
        pickHeader.setAccount(account);
        pickHeader.setCustomerId(customerId);
        pickHeader.setStateOutput("未处理");
        pickHeader.setStateOrder("已处理");
        pickHeaderService.save(pickHeader);
        Integer pickNumber = pickHeader.getNumber();
        String pickNumberView = "pick_" + pickNumber;
        pickHeader.setNumberView(pickNumberView);
        pickHeaderService.updateById(pickHeader);

        PickData pickData = new PickData();
        JSONArray jsonArray = JSONArray.parseArray(tableData);
        int flag = 0;
        for(int i = 0; i < jsonArray.size(); i++){
            JSONObject obj= jsonArray.getJSONObject(i);
            String bookId = (String) obj.get("bookId");
            Integer purchase = (Integer) obj.get("purchase");
            Integer Short = (Integer) obj.get("short");
            if (Short > 0){
                flag = 1;
            }
            pickData.setNumber(pickNumber);
            pickData.setBookId(bookId);
            pickData.setPurchase(purchase);
            pickData.setShort(Short);
            pickDataService.save(pickData);
        }
        if (flag == 1){
            pickHeader.setStateOrder("未处理");
            pickHeaderService.updateById(pickHeader);
        }
        SaleHeader saleHeader = new SaleHeader();
        saleHeader.setNumber(number);
        saleHeader.setState("已处理");
        saleHeaderService.updateById(saleHeader);
    }

    @RequestMapping("/delpick")
    @ResponseBody
    public void delPick(@RequestParam Integer number){
        QueryWrapper<PickData> pickDataQueryWrapper = new QueryWrapper<>();
        pickDataQueryWrapper.eq("number",number);
        pickDataService.remove(pickDataQueryWrapper);

        QueryWrapper<PickHeader> pickHeaderQueryWrapper = new QueryWrapper<>();
        pickHeaderQueryWrapper.eq("number",number);
        pickHeaderService.remove(pickHeaderQueryWrapper);
    }
}
