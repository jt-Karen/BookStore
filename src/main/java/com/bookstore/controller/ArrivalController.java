package com.bookstore.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bookstore.entity.arrival.ArrivalData;
import com.bookstore.entity.arrival.ArrivalHeader;
import com.bookstore.mapper.arrival.ArrivalHeaderMapper;
import com.bookstore.service.arrival.ArrivalDataService;
import com.bookstore.service.arrival.ArrivalHeaderService;
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
public class ArrivalController {
    @Autowired
    private ArrivalHeaderService arrivalHeaderService;
    @Autowired
    private ArrivalDataService arrivalDataService;
    @Autowired
    private ArrivalHeaderMapper arrivalHeaderMapper;

    @RequestMapping("/arrival")
    public String getView() {
        return "arrival";
    }

    @RequestMapping("/setarrival")
    public String getViewSet() {
        return "setArrival";
    }

    @RequestMapping("/editarrival")
    public String getViewEdit() {
        return "editArrival";
    }

    @RequestMapping("/arrivaldata")
    @ResponseBody
    public DataVO getArrival(){
        return arrivalHeaderService.find();
    }

    @RequestMapping("/arrivaldatasearch")
    @ResponseBody
    public DataVO getArrivalSearch(@RequestParam String search){
        DataVO dataVO = new DataVO<>();
        dataVO.setCode(0);
        dataVO.setMsg(" ");
        dataVO.setCount(arrivalHeaderMapper.selectCount(null));
        QueryWrapper<ArrivalHeader> arrivalHeaderQueryWrapper = new QueryWrapper<>();
        arrivalHeaderQueryWrapper.like("number_view",search).or().like("account",search).or().like("time",search).or().like("state",search);
        List<ArrivalHeader> arrivalHeaderList = arrivalHeaderMapper.selectList(arrivalHeaderQueryWrapper);
        dataVO.setData(arrivalHeaderList);
        return dataVO;
    }

    @RequestMapping("/submitarrival")
    @ResponseBody
    public void submitArrival(@RequestParam String account, @RequestParam String publisherId, @RequestParam String tableData){
        ArrivalHeader arrivalHeader = new ArrivalHeader();
        arrivalHeader.setAccount(account);
        arrivalHeader.setPublisherId(publisherId);
        arrivalHeader.setState("未处理");
        arrivalHeaderService.save(arrivalHeader);
        Integer number = arrivalHeader.getNumber();
        String numberView = "arrival_" + number;
        arrivalHeader.setNumberView(numberView);
        arrivalHeaderService.updateById(arrivalHeader);

        ArrivalData arrivalData =new ArrivalData();
        JSONArray jsonArray = JSONArray.parseArray(tableData);
        for(int i = 0; i < jsonArray.size(); i++){
            JSONObject obj= jsonArray.getJSONObject(i);
            String bookId = (String) obj.get("bookId");
            String arrival = (String) obj.get("arrival");
            arrivalData.setNumber(number);
            arrivalData.setBookId(bookId);
            arrivalData.setArrival(arrival);
            arrivalDataService.save(arrivalData);
        }
    }

    @RequestMapping("/updatearrival")
    @ResponseBody
    public void updateArrival(@RequestParam Integer number, @RequestParam String tableData){
        QueryWrapper<ArrivalData> arrivalDataQueryWrapper = new QueryWrapper<>();
        arrivalDataQueryWrapper.eq("number",number);
        arrivalDataService.remove(arrivalDataQueryWrapper);

        ArrivalData arrivalData = new ArrivalData();
        JSONArray jsonArray = JSONArray.parseArray(tableData);
        for(int i = 0; i < jsonArray.size(); i++){
            JSONObject obj= jsonArray.getJSONObject(i);
            String bookId = (String) obj.get("bookId");
            String arrival = (String) obj.get("arrival");
            arrivalData.setNumber(number);
            arrivalData.setBookId(bookId);
            arrivalData.setArrival(arrival);
            arrivalDataService.save(arrivalData);
        }
    }

    @RequestMapping("/delarrival")
    @ResponseBody
    public void delArrival(@RequestParam Integer number){
        QueryWrapper<ArrivalData> arrivalDataQueryWrapper = new QueryWrapper<>();
        arrivalDataQueryWrapper.eq("number",number);
        arrivalDataService.remove(arrivalDataQueryWrapper);

        QueryWrapper<ArrivalHeader> arrivalHeaderQueryWrapper = new QueryWrapper<>();
        arrivalHeaderQueryWrapper.eq("number",number);
        arrivalHeaderService.remove(arrivalHeaderQueryWrapper);
    }
}
