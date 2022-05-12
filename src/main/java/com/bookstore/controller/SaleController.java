package com.bookstore.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bookstore.entity.sale.SaleData;
import com.bookstore.entity.sale.SaleHeader;
import com.bookstore.mapper.sale.SaleHeaderMapper;
import com.bookstore.service.sale.SaleDataService;
import com.bookstore.service.sale.SaleHeaderService;
import com.bookstore.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
public class SaleController {
    @Autowired
    private SaleHeaderService saleHeaderService;
    @Autowired
    private SaleDataService saleDataService;
    @Autowired
    private SaleHeaderMapper saleHeaderMapper;

    @RequestMapping("/saledata")
    @ResponseBody
    public DataVO getSale(){
        return saleHeaderService.find();
    }

    @RequestMapping("/salequerydata")
    @ResponseBody
    public DataVO getSaleQuery(){
        return saleHeaderService.findQuery();
    }

    @RequestMapping("/sale")
    public String getView() {
        return "sale";
    }

    @RequestMapping("/setsale")
    public String getViewSet() {
        return "setSale";
    }

    @RequestMapping("/editsale")
    public String getViewEdit() {
        return "editSale";
    }

    @RequestMapping("/salequery")
    public String getViewQuery() {
        return "saleQuery";
    }

    @RequestMapping("/checksale")
    public String getViewCheck() {
        return "checkSale";
    }

    @RequestMapping("/saledatasearch")
    @ResponseBody
    public DataVO getSaleSearch(@RequestParam String search){
        DataVO dataVO = new DataVO<>();
        dataVO.setCode(0);
        dataVO.setMsg(" ");
        dataVO.setCount(saleHeaderMapper.selectCount(null));
        QueryWrapper<SaleHeader> saleHeaderQueryWrapper = new QueryWrapper<>();
        saleHeaderQueryWrapper.like("number_view",search).or().like("account",search).or().like("time",search);
        List<SaleHeader> saleHeaderList = saleHeaderMapper.selectList(saleHeaderQueryWrapper);
        dataVO.setData(saleHeaderList);
        return dataVO;
    }

    @RequestMapping("/salequerydatasearch")
    @ResponseBody
    public DataVO getSaleQuerySearch(@RequestParam String search){
        DataVO dataVO = new DataVO<>();
        dataVO.setCode(0);
        dataVO.setMsg(" ");
        dataVO.setCount(saleHeaderMapper.selectCount(null));
        QueryWrapper<SaleHeader> saleHeaderQueryWrapper = new QueryWrapper<>();
        saleHeaderQueryWrapper.like("number_view",search).or().like("account",search).or().like("time",search).or().like("state",search);
        List<SaleHeader> saleHeaderList = saleHeaderMapper.selectList(saleHeaderQueryWrapper);
        dataVO.setData(saleHeaderList);
        return dataVO;
    }

    @RequestMapping("/submitsale")
    @ResponseBody
    public void submitSale(@RequestParam String account, @RequestParam Integer customerId, @RequestParam String tableData){
        SaleHeader saleHeader = new SaleHeader();
        saleHeader.setAccount(account);
        saleHeader.setCustomerId(customerId);
        saleHeader.setState("未处理");
        saleHeaderService.save(saleHeader);
        Integer number = saleHeader.getNumber();
        String numberView = "sale_" + number;
        saleHeader.setNumberView(numberView);
        saleHeaderService.updateById(saleHeader);

        SaleData saleData = new SaleData();
        JSONArray jsonArray = JSONArray.parseArray(tableData);
        for(int i = 0; i < jsonArray.size(); i++){
            JSONObject obj= jsonArray.getJSONObject(i);
            String bookId = (String) obj.get("bookId");
            String purchase = (String) obj.get("purchase");
            saleData.setNumber(number);
            saleData.setBookId(bookId);
            saleData.setPurchase(purchase);
            saleDataService.save(saleData);
        }
    }

    @RequestMapping("/updatesale")
    @ResponseBody
    public void updateSale(@RequestParam Integer number, @RequestParam String tableData){
        QueryWrapper<SaleData> saleDataQueryWrapper = new QueryWrapper<>();
        saleDataQueryWrapper.eq("number",number);
        saleDataService.remove(saleDataQueryWrapper);

        SaleData saleData = new SaleData();
        JSONArray jsonArray = JSONArray.parseArray(tableData);
        for(int i = 0; i < jsonArray.size(); i++){
            JSONObject obj= jsonArray.getJSONObject(i);
            String bookId = (String) obj.get("bookId");
            String purchase = (String) obj.get("purchase");
            saleData.setNumber(number);
            saleData.setBookId(bookId);
            saleData.setPurchase(purchase);
            saleDataService.save(saleData);
        }
    }

    @RequestMapping("/delsale")
    @ResponseBody
    public void delSale(@RequestParam Integer number){
        QueryWrapper<SaleData> saleDataQueryWrapper = new QueryWrapper<>();
        saleDataQueryWrapper.eq("number",number);
        saleDataService.remove(saleDataQueryWrapper);

        QueryWrapper<SaleHeader> saleHeaderQueryWrapper = new QueryWrapper<>();
        saleHeaderQueryWrapper.eq("number",number);
        saleHeaderService.remove(saleHeaderQueryWrapper);
    }
}