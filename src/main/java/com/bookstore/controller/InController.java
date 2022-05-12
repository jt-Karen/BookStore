package com.bookstore.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bookstore.entity.*;
import com.bookstore.entity.input.InData;
import com.bookstore.entity.input.InHeader;
import com.bookstore.entity.inspect.InspectHeader;
import com.bookstore.mapper.input.InHeaderMapper;
import com.bookstore.service.entity.BookService;
import com.bookstore.service.input.InDataService;
import com.bookstore.service.input.InHeaderService;
import com.bookstore.service.inspect.InspectHeaderService;
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
public class InController {
    @Autowired
    private InspectHeaderService inspectHeaderService;
    @Autowired
    private InHeaderService inHeaderService;
    @Autowired
    private InDataService inDataService;
    @Autowired
    private BookService bookService;
    @Autowired
    private InHeaderMapper inHeaderMapper;

    @RequestMapping("/setinput")
    public String getViewSet() {
        return "setInput";
    }

    @RequestMapping("/checkinput")
    public String getViewCheck() {
        return "checkInput";
    }

    @RequestMapping("/input")
    public String getView() {
        return "input";
    }

    @RequestMapping("/inputdata")
    @ResponseBody
    public DataVO getInput(){
        return inHeaderService.find();
    }

    @RequestMapping("/inputdatasearch")
    @ResponseBody
    public DataVO getInputSearch(@RequestParam String search){
        DataVO dataVO = new DataVO<>();
        dataVO.setCode(0);
        dataVO.setMsg(" ");
        dataVO.setCount(inHeaderMapper.selectCount(null));
        QueryWrapper<InHeader> inHeaderQueryWrapper = new QueryWrapper<>();
        inHeaderQueryWrapper.like("number_view",search).or().like("relate_number",search).or().like("account",search).or().like("time",search).or().like("state",search);
        List<InHeader> inHeaderList = inHeaderMapper.selectList(inHeaderQueryWrapper);
        dataVO.setData(inHeaderList);
        return dataVO;
    }

    @RequestMapping("/submitinput")
    @ResponseBody
    public void submitInput(@RequestParam Integer number, @RequestParam String account, @RequestParam String tableData){
        InHeader inHeader = new InHeader();
        String inspectNumberView = inspectHeaderService.getNumberView(number);
        inHeader.setRelateNumber(inspectNumberView);
        inHeader.setAccount(account);
        inHeader.setState("未处理");
        inHeaderService.save(inHeader);
        Integer inputNumber = inHeader.getNumber();
        String inputNumberView = "in_" + inputNumber;
        inHeader.setNumberView(inputNumberView);
        inHeaderService.updateById(inHeader);

        InData inData = new InData();
        JSONArray jsonArray = JSONArray.parseArray(tableData);
        for(int i = 0; i < jsonArray.size(); i++){
            JSONObject obj= jsonArray.getJSONObject(i);
            String bookId = (String) obj.get("bookId");
            Integer input = (Integer) obj.get("input");
            inData.setNumber(inputNumber);
            inData.setBookId(bookId);
            inData.setInput(input);
            inDataService.save(inData);
        }

        InspectHeader inspectHeader = new InspectHeader();
        inspectHeader.setNumber(number);
        inspectHeader.setStateInput("已处理");
        inspectHeaderService.updateById(inspectHeader);
    }

    @RequestMapping("/delinput")
    @ResponseBody
    public void delInput(@RequestParam Integer number){
        QueryWrapper<InData> inDataQueryWrapper = new QueryWrapper<>();
        inDataQueryWrapper.eq("number",number);
        inDataService.remove(inDataQueryWrapper);

        QueryWrapper<InHeader> inHeaderQueryWrapper = new QueryWrapper<>();
        inHeaderQueryWrapper.eq("number",number);
        inHeaderService.remove(inHeaderQueryWrapper);
    }

    @RequestMapping("/inputimpl")
    @ResponseBody
    public void InputImpl(@RequestParam Integer number){
        QueryWrapper<InData> inDataQueryWrapper = new QueryWrapper<>();
        inDataQueryWrapper.eq("number",number);
        List<InData> inDataList = inDataService.list(inDataQueryWrapper);
        for (int i = 0; i < inDataList.size(); i++){
            InData inData = inDataList.get(i);
            String BookId = inData.getBookId();
            Book book = bookService.getById(BookId);
            Integer inventory = book.getInventory() + inData.getInput();
            book.setInventory(inventory);
            bookService.updateById(book);

            InHeader inHeader = new InHeader();
            inHeader.setNumber(number);
            inHeader.setState("已处理");
            inHeaderService.updateById(inHeader);
        }
    }
}
