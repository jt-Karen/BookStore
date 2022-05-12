package com.bookstore.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bookstore.entity.Book;
import com.bookstore.entity.pick.PickData;
import com.bookstore.entity.Publisher;
import com.bookstore.mapper.entity.PublisherMapper;
import com.bookstore.service.arrival.ArrivalHeaderService;
import com.bookstore.service.entity.BookService;
import com.bookstore.service.entity.PublisherService;
import com.bookstore.service.order.OrderHeaderService;
import com.bookstore.service.pick.PickDataService;
import com.bookstore.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
public class PublisherController {
    @Autowired
    private PublisherService publisherService;
    @Autowired
    private ArrivalHeaderService arrivalHeaderService;
    @Autowired
    private PickDataService pickDataService;
    @Autowired
    private BookService bookService;
    @Autowired
    private OrderHeaderService orderHeaderService;
    @Autowired
    private PublisherMapper publisherMapper;

    @RequestMapping("/publisherdata")
    @ResponseBody
    public DataVO getPublisher(){
        return publisherService.find();
    }

    @RequestMapping("/publisher")
    public String getView() {
        return "publisher";
    }

    @RequestMapping("/setpublisher")
    public String getViewSet() {
        return "setPublisher";
    }

    @RequestMapping("/editpublisher")
    public String getViewEdit() {
        return "editPublisher";
    }

    @RequestMapping("/getpublisherarrival")
    @ResponseBody
    public Publisher getPublisherArrival(@RequestParam Integer number){
        String PublisherId = arrivalHeaderService.getPublisherId(number);
        Publisher publisher = publisherService.getById(PublisherId);
        return publisher;
    }

    @RequestMapping("/getpublisherorderbypick")
    @ResponseBody
    public Publisher getPublisherOrderByPick(@RequestParam Integer number){
        QueryWrapper<PickData> pickDataQueryWrapper = new QueryWrapper<>();
        pickDataQueryWrapper.eq("number",number);
        List<PickData> pickDataList = pickDataService.list(pickDataQueryWrapper);
        PickData pickData = pickDataList.get(0);
        String BookId = pickData.getBookId();
        Book book = bookService.getById(BookId);
        String PublisherName = book.getPublisher();
        QueryWrapper<Publisher> publisherQueryWrapper = new QueryWrapper<>();
        publisherQueryWrapper.eq("Publisher_name",PublisherName);
        Publisher publisher = publisherService.getOne(publisherQueryWrapper);
        return publisher;
    }

    @RequestMapping("/getpublisherorderbyinventory")
    @ResponseBody
    public Publisher getPublisherOrderByInventory(@RequestParam String number){
        Book book = bookService.getById(number);
        String PublisherName = book.getPublisher();
        QueryWrapper<Publisher> publisherQueryWrapper = new QueryWrapper<>();
        publisherQueryWrapper.eq("Publisher_name",PublisherName);
        Publisher publisher = publisherService.getOne(publisherQueryWrapper);
        return publisher;
    }

    @RequestMapping("/getpublisherorder")
    @ResponseBody
    public Publisher getPublisherOrder(@RequestParam Integer number){
        String PublisherId = orderHeaderService.getPublisherId(number);
        Publisher publisher = publisherService.getById(PublisherId);
        return publisher;
    }

    @RequestMapping("/getpublisherdata")
    @ResponseBody
    public Publisher getPublisherData(@RequestParam String number){
        Publisher publisher = publisherService.getById(number);
        return publisher;
    }

    @RequestMapping("/publisherdatasearch")
    @ResponseBody
    public DataVO getPublisherSearch(@RequestParam String search){
        DataVO dataVO = new DataVO<>();
        dataVO.setCode(0);
        dataVO.setMsg(" ");
        dataVO.setCount(publisherMapper.selectCount(null));
        QueryWrapper<Publisher> publisherQueryWrapper = new QueryWrapper<>();
        publisherQueryWrapper.like("publisher_id",search).or().like("publisher_name",search).or().like("contact",search).or().like("tel",search).or().like("address",search);
        List<Publisher> publisherList = publisherMapper.selectList(publisherQueryWrapper);
        dataVO.setData(publisherList);
        return dataVO;
    }

    @RequestMapping("/submitpublisher")
    @ResponseBody
    public void submitPublisher(@RequestParam String publisherData){
        JSONObject obj = JSON.parseObject(publisherData);
        String publisherId = (String) obj.get("publisherId");
        String publisherName = (String) obj.get("publisherName");
        String contact = (String) obj.get("contact");
        String publisherTel = (String) obj.get("publisherTel");
        String publisherAddress = (String) obj.get("publisherAddress");
        Publisher publisher = new Publisher();
        publisher.setPublisherId(publisherId);
        publisher.setPublisherName(publisherName);
        publisher.setContact(contact);
        publisher.setTel(publisherTel);
        publisher.setAddress(publisherAddress);
        publisherService.save(publisher);
    }

    @RequestMapping("/updatepublisher")
    @ResponseBody
    public void updatePublisher(@RequestParam String publisherData){
        JSONObject obj = JSON.parseObject(publisherData);
        String publisherId = (String) obj.get("publisherId");
        String publisherName = (String) obj.get("publisherName");
        String contact = (String) obj.get("contact");
        String publisherTel = (String) obj.get("publisherTel");
        String publisherAddress = (String) obj.get("publisherAddress");
        Publisher publisher = new Publisher();
        publisher.setPublisherId(publisherId);
        publisher.setPublisherName(publisherName);
        publisher.setContact(contact);
        publisher.setTel(publisherTel);
        publisher.setAddress(publisherAddress);
        publisherService.updateById(publisher);
    }

    @RequestMapping("/delpublisher")
    @ResponseBody
    public void delPublisher(@RequestParam String number){
        QueryWrapper<Publisher> publisherDataQueryWrapper = new QueryWrapper<>();
        publisherDataQueryWrapper.eq("publisher_id",number);
        publisherService.remove(publisherDataQueryWrapper);
    }
}
