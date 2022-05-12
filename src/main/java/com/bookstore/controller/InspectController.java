package com.bookstore.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bookstore.entity.arrival.ArrivalHeader;
import com.bookstore.entity.inspect.InspectData;
import com.bookstore.entity.inspect.InspectHeader;
import com.bookstore.mapper.inspect.InspectHeaderMapper;
import com.bookstore.service.arrival.ArrivalHeaderService;
import com.bookstore.service.inspect.InspectDataService;
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
public class InspectController {
    @Autowired
    private ArrivalHeaderService arrivalHeaderService;
    @Autowired
    private InspectHeaderService inspectHeaderService;
    @Autowired
    private InspectDataService inspectDataService;
    @Autowired
    private InspectHeaderMapper inspectHeaderMapper;

    @RequestMapping("/setinspect")
    public String getViewSet() {
        return "setInspect";
    }

    @RequestMapping("/inspect")
    public String getView() {
        return "inspect";
    }

    @RequestMapping("/editinspect")
    public String getViewEdit() {
        return "editInspect";
    }

    @RequestMapping("/checkinspect")
    public String getViewCheck() {
        return "checkInspect";
    }

    @RequestMapping("/inspectquery")
    public String getViewQuery() {
        return "inspectQuery";
    }

    @RequestMapping("/inspectdata")
    @ResponseBody
    public DataVO getInspect(){
        return inspectHeaderService.find();
    }

    @RequestMapping("/inspectquerydata")
    @ResponseBody
    public DataVO getInspectQuery(){
        return inspectHeaderService.findQuery();
    }

    @RequestMapping("/inspectdatasearch")
    @ResponseBody
    public DataVO getInspectSearch(@RequestParam String search){
        DataVO dataVO = new DataVO<>();
        dataVO.setCode(0);
        dataVO.setMsg(" ");
        dataVO.setCount(inspectHeaderMapper.selectCount(null));
        QueryWrapper<InspectHeader> inspectHeaderQueryWrapper = new QueryWrapper<>();
        inspectHeaderQueryWrapper.like("number_view",search).or().like("relate_number",search).or().like("account",search).or().like("time",search).or().like("state_input",search);
        List<InspectHeader> inspectHeaderList = inspectHeaderMapper.selectList(inspectHeaderQueryWrapper);
        dataVO.setData(inspectHeaderList);
        return dataVO;
    }

    @RequestMapping("/inspectquerydatasearch")
    @ResponseBody
    public DataVO getInspectQuerySearch(@RequestParam String search){
        DataVO dataVO = new DataVO<>();
        dataVO.setCode(0);
        dataVO.setMsg(" ");
        dataVO.setCount(inspectHeaderMapper.selectCount(null));
        QueryWrapper<InspectHeader> inspectHeaderQueryWrapper = new QueryWrapper<>();
        inspectHeaderQueryWrapper.like("number_view",search).or().like("relate_number",search).or().like("account",search).or().like("time",search).or().like("state_consult",search);
        List<InspectHeader> inspectHeaderList = inspectHeaderMapper.selectList(inspectHeaderQueryWrapper);
        dataVO.setData(inspectHeaderList);
        return dataVO;
    }

    @RequestMapping("/submitinspect")
    @ResponseBody
    public void submitInspect(@RequestParam Integer number, @RequestParam String account, @RequestParam String publisherId, @RequestParam String tableData){
        InspectHeader inspectHeader = new InspectHeader();
        String arrivalNumberView = arrivalHeaderService.getNumberView(number);
        inspectHeader.setRelateNumber(arrivalNumberView);
        inspectHeader.setAccount(account);
        inspectHeader.setPublisherId(publisherId);
        inspectHeader.setStateInput("未处理");
        inspectHeader.setStateConsult("已处理");
        inspectHeaderService.save(inspectHeader);
        Integer inspectNumber = inspectHeader.getNumber();
        String inspectNumberView = "inspect_" + inspectNumber;
        inspectHeader.setNumberView(inspectNumberView);
        inspectHeaderService.updateById(inspectHeader);

        InspectData inspectData = new InspectData();
        JSONArray jsonArray = JSONArray.parseArray(tableData);
        int flag = 0;
        for(int i = 0; i < jsonArray.size(); i++){
            JSONObject obj= jsonArray.getJSONObject(i);
            String bookId = (String) obj.get("bookId");
            Integer arrival = (Integer) obj.get("arrival");
            String qualified = (String) obj.get("qualified");
            Integer qualifiedInt = Integer.parseInt(qualified);
            if (arrival > qualifiedInt){
                flag = 1;
            }
            inspectData.setNumber(inspectNumber);
            inspectData.setBookId(bookId);
            inspectData.setArrival(arrival);
            inspectData.setQualified(qualified);
            inspectDataService.save(inspectData);
        }
        if (flag == 1){
            inspectHeader.setStateConsult("未处理");
            inspectHeaderService.updateById(inspectHeader);
        }
        ArrivalHeader arrivalHeader = new ArrivalHeader();
        arrivalHeader.setNumber(number);
        arrivalHeader.setState("已处理");
        arrivalHeaderService.updateById(arrivalHeader);
    }

    @RequestMapping("/updateinspect")
    @ResponseBody
    public void updateInspect(@RequestParam Integer number, @RequestParam String tableData){
        InspectHeader inspectHeader = inspectHeaderService.getById(number);

        QueryWrapper<InspectData> inspectDataQueryWrapper = new QueryWrapper<>();
        inspectDataQueryWrapper.eq("number",number);
        inspectDataService.remove(inspectDataQueryWrapper);

        InspectData inspectData = new InspectData();
        JSONArray jsonArray = JSONArray.parseArray(tableData);
        int flag = 0;
        for(int i = 0; i < jsonArray.size(); i++){
            JSONObject obj= jsonArray.getJSONObject(i);
            String bookId = (String) obj.get("bookId");
            Integer arrival = (Integer) obj.get("arrival");
            String qualified = (String) obj.get("qualified");
            Integer qualifiedInt = Integer.parseInt(qualified);
            if (arrival > qualifiedInt){
                flag = 1;
            }
            inspectData.setNumber(number);
            inspectData.setBookId(bookId);
            inspectData.setArrival(arrival);
            inspectData.setQualified(qualified);
            inspectDataService.save(inspectData);
        }
        if (flag == 1){
            inspectHeader.setStateConsult("未处理");
            inspectHeaderService.updateById(inspectHeader);
        }
    }

    @RequestMapping("/delinspect")
    @ResponseBody
    public void delInspect(@RequestParam Integer number){
        QueryWrapper<InspectData> inspectDataQueryWrapper = new QueryWrapper<>();
        inspectDataQueryWrapper.eq("number",number);
        inspectDataService.remove(inspectDataQueryWrapper);

        QueryWrapper<InspectHeader> inspectHeaderQueryWrapper = new QueryWrapper<>();
        inspectHeaderQueryWrapper.eq("number",number);
        inspectHeaderService.remove(inspectHeaderQueryWrapper);
    }

    @RequestMapping("/consultinspect")
    @ResponseBody
    public void consultInspect(@RequestParam Integer number){
        InspectHeader inspectHeader = inspectHeaderService.getById(number);
        inspectHeader.setStateConsult("已处理");
        inspectHeaderService.updateById(inspectHeader);
    }
}
