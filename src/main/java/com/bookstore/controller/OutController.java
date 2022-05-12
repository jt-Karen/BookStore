package com.bookstore.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bookstore.entity.*;
import com.bookstore.entity.output.OutData;
import com.bookstore.entity.output.OutHeader;
import com.bookstore.entity.pick.PickHeader;
import com.bookstore.mapper.output.OutHeaderMapper;
import com.bookstore.service.entity.BookService;
import com.bookstore.service.output.OutDataService;
import com.bookstore.service.output.OutHeaderService;
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
public class OutController {
    @Autowired
    private OutHeaderService outHeaderService;
    @Autowired
    private OutDataService outDataService;
    @Autowired
    private PickHeaderService pickHeaderService;
    @Autowired
    private BookService bookService;
    @Autowired
    private OutHeaderMapper outHeaderMapper;

    @RequestMapping("/setoutput")
    public String getViewSet() {
        return "setOutput";
    }

    @RequestMapping("/output")
    public String getView() {
        return "output";
    }

    @RequestMapping("/checkoutput")
    public String getViewCheck() {
        return "checkOutput";
    }

    @RequestMapping("/outputdata")
    @ResponseBody
    public DataVO getOutput(){
        return outHeaderService.find();
    }

    @RequestMapping("/outputdatasearch")
    @ResponseBody
    public DataVO getOutputSearch(@RequestParam String search){
        DataVO dataVO = new DataVO<>();
        dataVO.setCode(0);
        dataVO.setMsg(" ");
        dataVO.setCount(outHeaderMapper.selectCount(null));
        QueryWrapper<OutHeader> outHeaderQueryWrapper = new QueryWrapper<>();
        outHeaderQueryWrapper.like("number_view",search).or().like("relate_number",search).or().like("account",search).or().like("time",search).or().like("state",search);
        List<OutHeader> outHeaderList = outHeaderMapper.selectList(outHeaderQueryWrapper);
        dataVO.setData(outHeaderList);
        return dataVO;
    }

    @RequestMapping("/submitoutput")
    @ResponseBody
    public void submitOut(@RequestParam Integer number, @RequestParam String account, @RequestParam String tableData){
        OutHeader outHeader = new OutHeader();
        String pickNumberView = pickHeaderService.getNumberView(number);
        outHeader.setRelateNumber(pickNumberView);
        outHeader.setAccount(account);
        outHeader.setState("未处理");
        outHeaderService.save(outHeader);
        Integer outNumber = outHeader.getNumber();
        String outNumberView = "out_" + outNumber;
        outHeader.setNumberView(outNumberView);
        outHeaderService.updateById(outHeader);

        OutData outData = new OutData();
        JSONArray jsonArray = JSONArray.parseArray(tableData);
        for(int i = 0; i < jsonArray.size(); i++){
            JSONObject obj= jsonArray.getJSONObject(i);
            String bookId = (String) obj.get("bookId");
            Integer output = (Integer) obj.get("output");
            outData.setNumber(outNumber);
            outData.setBookId(bookId);
            outData.setOutput(output);
            outDataService.save(outData);
        }
        PickHeader pickHeader = new PickHeader();
        pickHeader.setNumber(number);
        pickHeader.setStateOutput("已处理");
        pickHeaderService.updateById(pickHeader);
    }

    @RequestMapping("/deloutput")
    @ResponseBody
    public void delOutput(@RequestParam Integer number){
        QueryWrapper<OutData> outDataQueryWrapper = new QueryWrapper<>();
        outDataQueryWrapper.eq("number",number);
        outDataService.remove(outDataQueryWrapper);

        QueryWrapper<OutHeader> outHeaderQueryWrapper = new QueryWrapper<>();
        outHeaderQueryWrapper.eq("number",number);
        outHeaderService.remove(outHeaderQueryWrapper);
    }

    @RequestMapping("/outputimpl")
    @ResponseBody
    public void OutputImpl(@RequestParam Integer number){
        QueryWrapper<OutData> outDataQueryWrapper = new QueryWrapper<>();
        outDataQueryWrapper.eq("number",number);
        List<OutData> outDataList = outDataService.list(outDataQueryWrapper);
        for (int i = 0; i < outDataList.size(); i++){
            OutData outData = outDataList.get(i);
            String BookId = outData.getBookId();
            Book book = bookService.getById(BookId);
            Integer inventory = book.getInventory() - outData.getOutput();
            book.setInventory(inventory);
            bookService.updateById(book);

            OutHeader outHeader = new OutHeader();
            outHeader.setNumber(number);
            outHeader.setState("已处理");
            outHeaderService.updateById(outHeader);
        }
    }
}
