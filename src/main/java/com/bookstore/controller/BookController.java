package com.bookstore.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bookstore.entity.*;
import com.bookstore.entity.arrival.ArrivalData;
import com.bookstore.entity.input.InData;
import com.bookstore.entity.inspect.InspectData;
import com.bookstore.entity.order.OrderData;
import com.bookstore.entity.output.OutData;
import com.bookstore.entity.pick.PickData;
import com.bookstore.entity.sale.SaleData;
import com.bookstore.mapper.entity.BookMapper;
import com.bookstore.service.arrival.ArrivalDataService;
import com.bookstore.service.entity.BookService;
import com.bookstore.service.input.InDataService;
import com.bookstore.service.inspect.InspectDataService;
import com.bookstore.service.order.OrderDataService;
import com.bookstore.service.output.OutDataService;
import com.bookstore.service.pick.PickDataService;
import com.bookstore.service.sale.SaleDataService;
import com.bookstore.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private SaleDataService saleDataService;
    @Autowired
    private PickDataService pickDataService;
    @Autowired
    private OutDataService outDataService;
    @Autowired
    private ArrivalDataService arrivalDataService;
    @Autowired
    private InspectDataService inspectDataService;
    @Autowired
    private InDataService inDataService;
    @Autowired
    private OrderDataService orderDataService;
    @Autowired
    private BookMapper bookMapper;

    @RequestMapping("/bookdata")
    @ResponseBody
    public DataVO getBook(){
        return bookService.find();
    }

    @RequestMapping("/inventorydata")
    @ResponseBody
    public DataVO getInventory(){
        return bookService.findInventory();
    }

    @RequestMapping("/book")
    public String getView() {
        return "book";
    }

    @RequestMapping("/setbook")
    public String getViewSet() {
        return "setBook";
    }

    @RequestMapping("/editbook")
    public String getViewEdit() {
        return "editBook";
    }

    @RequestMapping("/inventory")
    public String getViewInventory() {
        return "inventory";
    }

    @RequestMapping("/getbookdata")
    @ResponseBody
    public Book getBookData(@RequestParam String number){
        Book book = bookService.getById(number);
        return book;
    }

    @RequestMapping("/bookdatasearch")
    @ResponseBody
    public DataVO getBookSearch(@RequestParam String search){
        DataVO dataVO = new DataVO<>();
        dataVO.setCode(0);
        dataVO.setMsg(" ");
        dataVO.setCount(bookMapper.selectCount(null));
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.like("book_id",search).or().like("book_name",search).or().like("author",search).or().like("publisher",search).or().like("price",search);
        List<Book> bookList = bookMapper.selectList(bookQueryWrapper);
        dataVO.setData(bookList);
        return dataVO;
    }

    @RequestMapping("/inventorydatasearch")
    @ResponseBody
    public DataVO getInventorySearch(@RequestParam String search){
        DataVO dataVO = new DataVO<>();
        dataVO.setCode(0);
        dataVO.setMsg(" ");
        dataVO.setCount(bookMapper.selectCount(null));
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.like("book_id",search).or().like("book_name",search).or().like("author",search).or().like("publisher",search).or().like("price",search).or().like("inventory",search);
        List<Book> bookList = bookMapper.selectList(bookQueryWrapper);
        dataVO.setData(bookList);
        return dataVO;
    }

    @RequestMapping("/submitbook")
    @ResponseBody
    public void submitBook(@RequestParam String bookData){
        JSONObject obj = JSON.parseObject(bookData);
        String bookId = (String) obj.get("bookId");
        String bookName = (String) obj.get("bookName");
        String author = (String) obj.get("author");
        String publisher = (String) obj.get("publisher");
        String price = (String) obj.get("price");
        Integer priceInt = Integer.parseInt(price);
        String inventory = (String) obj.get("inventory");
        Integer inventoryInt = Integer.parseInt(inventory);
        Book book = new Book();
        book.setBookId(bookId);
        book.setBookName(bookName);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setPrice(priceInt);
        book.setInventory(inventoryInt);
        bookService.save(book);
    }

    @RequestMapping("/updatebook")
    @ResponseBody
    public void updateBook(@RequestParam String bookData){
        JSONObject obj = JSON.parseObject(bookData);
        String bookId = (String) obj.get("bookId");
        String bookName = (String) obj.get("bookName");
        String author = (String) obj.get("author");
        String publisher = (String) obj.get("publisher");
        String price = (String) obj.get("price");
        Integer priceInt = Integer.parseInt(price);
        String inventory = (String) obj.get("inventory");
        Integer inventoryInt = Integer.parseInt(inventory);
        Book book = new Book();
        book.setBookId(bookId);
        book.setBookName(bookName);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setPrice(priceInt);
        book.setInventory(inventoryInt);
        bookService.updateById(book);
    }

    @RequestMapping("/delbook")
    @ResponseBody
    public void delBook(@RequestParam String number){
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.eq("book_id",number);
        bookService.remove(bookQueryWrapper);
    }

    @RequestMapping("/getbooksale")
    @ResponseBody
    public List getBookSale(@RequestParam Integer number) {
        List<SaleBookVO> saleBookVOList = new ArrayList<>();
        QueryWrapper<SaleData> saleDataQueryWrapper = new QueryWrapper<>();
        saleDataQueryWrapper.eq("number",number);
        List<SaleData> saleDataList = saleDataService.list(saleDataQueryWrapper);
        for (int i = 0; i < saleDataList.size(); i++){
            SaleData saleData = saleDataList.get(i);
            String BookId = saleData.getBookId();
            Book book = bookService.getById(BookId);
            SaleBookVO saleBookVO = new SaleBookVO();
            saleBookVO.setBookId(BookId);
            saleBookVO.setBookName(book.getBookName());
            saleBookVO.setAuthor(book.getAuthor());
            saleBookVO.setPublisher(book.getPublisher());
            saleBookVO.setPrice(book.getPrice());
            saleBookVO.setPurchase(saleData.getPurchase());
            saleBookVOList.add(saleBookVO);
        }
        return saleBookVOList;
    }

    @RequestMapping("/getbookpick")
    @ResponseBody
    public List getBookPick(@RequestParam Integer number) {
        List<PickBookVO> pickBookVOList = new ArrayList<>();
        QueryWrapper<SaleData> saleDataQueryWrapper = new QueryWrapper<>();
        saleDataQueryWrapper.eq("number",number);
        List<SaleData> saleDataList = saleDataService.list(saleDataQueryWrapper);
        for (int i = 0; i < saleDataList.size(); i++){
            SaleData saleData = saleDataList.get(i);
            String BookId = saleData.getBookId();
            Book book = bookService.getById(BookId);
            PickBookVO pickBookVO = new PickBookVO();
            pickBookVO.setBookId(BookId);
            pickBookVO.setBookName(book.getBookName());
            pickBookVO.setAuthor(book.getAuthor());
            pickBookVO.setPublisher(book.getPublisher());
            pickBookVO.setPrice(book.getPrice());
            pickBookVO.setInventory(book.getInventory());
            Integer purchase = Integer.parseInt(saleData.getPurchase());
            pickBookVO.setPurchase(purchase);
            Integer Short = purchase - book.getInventory();
            Integer ShortValue;
            if(Short > 0){
                ShortValue = Short;
            }else{
                ShortValue = 0;
            }
            pickBookVO.setShort(ShortValue);
            pickBookVOList.add(pickBookVO);
        }
        return pickBookVOList;
    }

    @RequestMapping("/getbookoutput")
    @ResponseBody
    public List getBookOut(@RequestParam Integer number) {
        List<OutBookVO> outBookVOList = new ArrayList<>();
        QueryWrapper<PickData> pickDataQueryWrapper = new QueryWrapper<>();
        pickDataQueryWrapper.eq("number",number);
        List<PickData> pickDataList = pickDataService.list(pickDataQueryWrapper);
        for (int i = 0; i < pickDataList.size(); i++){
            PickData pickData = pickDataList.get(i);
            String BookId = pickData.getBookId();
            Book book = bookService.getById(BookId);
            OutBookVO outBookVO = new OutBookVO();
            outBookVO.setBookId(BookId);
            outBookVO.setBookName(book.getBookName());
            outBookVO.setAuthor(book.getAuthor());
            outBookVO.setPublisher(book.getPublisher());
            outBookVO.setPrice(book.getPrice());
            Integer Inventory = book.getInventory();
            Integer Purchase = pickData.getPurchase();
            Integer Short = pickData.getShort();
            Integer Output;
            if(Short > 0){
                Output = Inventory;
            }else{
                Output = Purchase;
            }
            outBookVO.setOutput(Output);
            outBookVOList.add(outBookVO);
        }
        return outBookVOList;
    }

    @RequestMapping("/getbookarrival")
    @ResponseBody
    public List getBookArrival(@RequestParam Integer number) {
        List<ArrivalBookVO> arrivalBookVOList = new ArrayList<>();
        QueryWrapper<ArrivalData> arrivalDataQueryWrapper = new QueryWrapper<>();
        arrivalDataQueryWrapper.eq("number",number);
        List<ArrivalData> arrivalDataList = arrivalDataService.list(arrivalDataQueryWrapper);
        for (int i = 0; i < arrivalDataList.size(); i++){
            ArrivalData arrivalData = arrivalDataList.get(i);
            String BookId = arrivalData.getBookId();
            Book book = bookService.getById(BookId);
            ArrivalBookVO arrivalBookVO = new ArrivalBookVO();
            arrivalBookVO.setBookId(BookId);
            arrivalBookVO.setBookName(book.getBookName());
            arrivalBookVO.setAuthor(book.getAuthor());
            arrivalBookVO.setPublisher(book.getPublisher());
            arrivalBookVO.setPrice(book.getPrice());
            arrivalBookVO.setArrival(arrivalData.getArrival());
            arrivalBookVOList.add(arrivalBookVO);
        }
        return arrivalBookVOList;
    }

    @RequestMapping("/getbookinspect")
    @ResponseBody
    public List getBookInspect(@RequestParam Integer number) {
        List<InspectBookVO> inspectBookVOList = new ArrayList<>();
        QueryWrapper<ArrivalData> arrivalDataQueryWrapper = new QueryWrapper<>();
        arrivalDataQueryWrapper.eq("number",number);
        List<ArrivalData> arrivalDataList = arrivalDataService.list(arrivalDataQueryWrapper);
        for (int i = 0; i < arrivalDataList.size(); i++) {
            ArrivalData arrivalData = arrivalDataList.get(i);
            String BookId = arrivalData.getBookId();
            Book book = bookService.getById(BookId);
            InspectBookVO inspectBookVO = new InspectBookVO();
            inspectBookVO.setBookId(BookId);
            inspectBookVO.setBookName(book.getBookName());
            inspectBookVO.setAuthor(book.getAuthor());
            inspectBookVO.setPublisher(book.getPublisher());
            inspectBookVO.setPrice(book.getPrice());
            Integer arrival = Integer.parseInt(arrivalData.getArrival());
            inspectBookVO.setArrival(arrival);
            inspectBookVOList.add(inspectBookVO);
        }
        return inspectBookVOList;
    }

    @RequestMapping("/getbookinput")
    @ResponseBody
    public List getBookInput(@RequestParam Integer number) {
        List<InBookVO> inBookVOList = new ArrayList<>();
        QueryWrapper<InspectData> inspectDataQueryWrapper = new QueryWrapper<>();
        inspectDataQueryWrapper.eq("number",number);
        List<InspectData> inspectDataList = inspectDataService.list(inspectDataQueryWrapper);
        for (int i = 0; i < inspectDataList.size(); i++) {
            InspectData inspectData = inspectDataList.get(i);
            String BookId = inspectData.getBookId();
            Book book = bookService.getById(BookId);
            InBookVO inBookVO = new InBookVO();
            inBookVO.setBookId(BookId);
            inBookVO.setBookName(book.getBookName());
            inBookVO.setAuthor(book.getAuthor());
            inBookVO.setPublisher(book.getPublisher());
            inBookVO.setPrice(book.getPrice());
            Integer qualified = Integer.parseInt(inspectData.getQualified());
            inBookVO.setInput(qualified);
            inBookVOList.add(inBookVO);
        }
        return inBookVOList;
    }

    @RequestMapping("/getbookorder")
    @ResponseBody
    public List getBookOrder(@RequestParam Integer number) {
        List<OrderBookVO> orderBookVOList = new ArrayList<>();
        QueryWrapper<PickData> pickDataQueryWrapper = new QueryWrapper<>();
        pickDataQueryWrapper.eq("number",number);
        List<PickData> pickDataList = pickDataService.list(pickDataQueryWrapper);
        for (int i = 0; i < pickDataList.size(); i++) {
            PickData pickData = pickDataList.get(i);
            String BookId = pickData.getBookId();
            Book book = bookService.getById(BookId);
            OrderBookVO orderBookVO = new OrderBookVO();
            orderBookVO.setBookId(BookId);
            orderBookVO.setBookName(book.getBookName());
            orderBookVO.setAuthor(book.getAuthor());
            orderBookVO.setPublisher(book.getPublisher());
            orderBookVO.setPrice(book.getPrice());
            String orders = String.valueOf(pickData.getShort());
            orderBookVO.setOrders(orders);
            orderBookVOList.add(orderBookVO);
        }
        return orderBookVOList;
    }

    @RequestMapping("/getbookpickdata")
    @ResponseBody
    public List getBookPickData(@RequestParam Integer number) {
        List<PickBookVO> pickBookVOList = new ArrayList<>();
        QueryWrapper<PickData> pickDataQueryWrapper = new QueryWrapper<>();
        pickDataQueryWrapper.eq("number",number);
        List<PickData> pickDataList = pickDataService.list(pickDataQueryWrapper);
        for (int i = 0; i < pickDataList.size(); i++){
            PickData pickData = pickDataList.get(i);
            String BookId = pickData.getBookId();
            Book book = bookService.getById(BookId);
            PickBookVO pickBookVO = new PickBookVO();
            pickBookVO.setBookId(BookId);
            pickBookVO.setBookName(book.getBookName());
            pickBookVO.setAuthor(book.getAuthor());
            pickBookVO.setPublisher(book.getPublisher());
            pickBookVO.setPrice(book.getPrice());
            pickBookVO.setInventory(book.getInventory());
            pickBookVO.setPurchase(pickData.getPurchase());
            pickBookVO.setShort(pickData.getShort());
            pickBookVOList.add(pickBookVO);
        }
        return pickBookVOList;
    }

    @RequestMapping("/getbookoutputdata")
    @ResponseBody
    public List getBookOutputData(@RequestParam Integer number) {
        List<OutBookVO> outBookVOList = new ArrayList<>();
        QueryWrapper<OutData> outDataQueryWrapper = new QueryWrapper<>();
        outDataQueryWrapper.eq("number",number);
        List<OutData> outDataList = outDataService.list(outDataQueryWrapper);
        for (int i = 0; i < outDataList.size(); i++){
            OutData outData = outDataList.get(i);
            String BookId = outData.getBookId();
            Book book = bookService.getById(BookId);
            OutBookVO outBookVO = new OutBookVO();
            outBookVO.setBookId(BookId);
            outBookVO.setBookName(book.getBookName());
            outBookVO.setAuthor(book.getAuthor());
            outBookVO.setPublisher(book.getPublisher());
            outBookVO.setPrice(book.getPrice());
            outBookVO.setOutput(outData.getOutput());
            outBookVOList.add(outBookVO);
        }
        return outBookVOList;
    }

    @RequestMapping("/getbookinspectdata")
    @ResponseBody
    public List getBookInspectData(@RequestParam Integer number) {
        List<InspectBookVO> inspectBookVOList = new ArrayList<>();
        QueryWrapper<InspectData> inspectDataQueryWrapper = new QueryWrapper<>();
        inspectDataQueryWrapper.eq("number",number);
        List<InspectData> inspectDataList = inspectDataService.list(inspectDataQueryWrapper);
        for (int i = 0; i < inspectDataList.size(); i++){
            InspectData inspectData = inspectDataList.get(i);
            String BookId = inspectData.getBookId();
            Book book = bookService.getById(BookId);
            InspectBookVO inspectBookVO = new InspectBookVO();
            inspectBookVO.setBookId(BookId);
            inspectBookVO.setBookName(book.getBookName());
            inspectBookVO.setAuthor(book.getAuthor());
            inspectBookVO.setPublisher(book.getPublisher());
            inspectBookVO.setPrice(book.getPrice());
            inspectBookVO.setArrival(inspectData.getArrival());
            inspectBookVO.setQualified(inspectData.getQualified());
            inspectBookVOList.add(inspectBookVO);
        }
        return inspectBookVOList;
    }

    @RequestMapping("/getbookinputdata")
    @ResponseBody
    public List getBookInputData(@RequestParam Integer number) {
        List<InBookVO> inBookVOList = new ArrayList<>();
        QueryWrapper<InData> inDataQueryWrapper = new QueryWrapper<>();
        inDataQueryWrapper.eq("number",number);
        List<InData> inDataList = inDataService.list(inDataQueryWrapper);
        for (int i = 0; i < inDataList.size(); i++){
            InData inData = inDataList.get(i);
            String BookId = inData.getBookId();
            Book book = bookService.getById(BookId);
            InBookVO inBookVO = new InBookVO();
            inBookVO.setBookId(BookId);
            inBookVO.setBookName(book.getBookName());
            inBookVO.setAuthor(book.getAuthor());
            inBookVO.setPublisher(book.getPublisher());
            inBookVO.setPrice(book.getPrice());
            inBookVO.setInput(inData.getInput());
            inBookVOList.add(inBookVO);
        }
        return inBookVOList;
    }

    @RequestMapping("/getbookorderdata")
    @ResponseBody
    public List getBookOrderData(@RequestParam Integer number) {
        List<OrderBookVO> orderBookVOList = new ArrayList<>();
        QueryWrapper<OrderData> orderDataQueryWrapper = new QueryWrapper<>();
        orderDataQueryWrapper.eq("number",number);
        List<OrderData> orderDataList = orderDataService.list(orderDataQueryWrapper);
        for (int i = 0; i < orderDataList.size(); i++){
            OrderData orderData = orderDataList.get(i);
            String BookId = orderData.getBookId();
            Book book = bookService.getById(BookId);
            OrderBookVO orderBookVO = new OrderBookVO();
            orderBookVO.setBookId(BookId);
            orderBookVO.setBookName(book.getBookName());
            orderBookVO.setAuthor(book.getAuthor());
            orderBookVO.setPublisher(book.getPublisher());
            orderBookVO.setPrice(book.getPrice());
            orderBookVO.setOrders(orderData.getOrders());
            orderBookVOList.add(orderBookVO);
        }
        return orderBookVOList;
    }

    @RequestMapping("/getbookorderdatabyinventory")
    @ResponseBody
    public List getBookOrderDataByInventory(@RequestParam String number) {
        List<OrderBookVO> orderBookVOList = new ArrayList<>();
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.eq("book_id",number);
        List<Book> bookList = bookService.list(bookQueryWrapper);
        Book book = bookList.get(0);
        OrderBookVO orderBookVO = new OrderBookVO();
        orderBookVO.setBookId(book.getBookId());
        orderBookVO.setBookName(book.getBookName());
        orderBookVO.setAuthor(book.getAuthor());
        orderBookVO.setPublisher(book.getPublisher());
        orderBookVO.setPrice(book.getPrice());
        orderBookVOList.add(orderBookVO);
        return orderBookVOList;
    }
}
