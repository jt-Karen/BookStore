$(function() {
    layui.use(['table','form'], function(){
        var table = layui.table,
            form = layui.form;

        var jsonSetBook = JSON.parse(localStorage.getItem("setBook"));

        // tableIns=table.render({
        //     elem: '#setBook'
        //     ,data: JSON.parse(localStorage.getItem("getSetBook"))
        //     ,cols: [
        //         [
        //             {field:'bookId', title:'书号', width:150, sort: true}
        //             ,{field:'bookName', title:'书名', width:150, sort: true}
        //             ,{field:'author', title:'作者', width:150, sort: true}
        //             ,{field:'publisher', title:'出版社', width:150, sort: true}
        //             ,{field:'price', title:'单价', width:150, sort: true}
        //             ,{field:'purchase', title:'数量', width:150, sort: true, edit: 'text'}
        //             ,{field:'total', title:'总价', width:150, sort: true}
        //         ]
        //     ]
        //     , page: false
        // });

        $.each(jsonSetBook, function (i, n) {
            var row = $("#template").clone();
            row.find("#bookId").text(n[0].bookId);
            row.find("#bookName").text(n[0].bookName);
            row.find("#author").text(n[0].author);
            row.find("#publisher").text(n[0].publisher);
            row.find("#price").text(n[0].price);
            row.find("#purchase").text(n[0].purchase);
            row.find("#total").text(n[0].total);
            row.appendTo("#setBook");//添加到模板的容器中
        });

        //监听保存
        form.on('submit(salePreserve)', function(data){
            //异步请求提交数据
            var url = "";
            $.post(url,data.field,function (response) {

            })
            return false;
        });
    });

    //添加顾客
    function setCustomer(){
        layer.open({
            type:2,
            title: "添加顾客",
            fixed:false,
            resize :false,
            shadeClose: true,
            area: ['90%','90%'],
            content:'/setcustomer',
        });
    }

    // var data = JSON.parse(localStorage.getItem("setCustomer"));
    // $("#customerId").val(data.customerId);
    // $("#customerName").val(data.name);
    // $("#customerTel").val(data.tel);
    // $("#customerAddress").val(data.address);

    //添加图书
    function setBook(){
        layer.open({
            type:2,
            title: "添加图书",
            fixed:false,
            resize :false,
            shadeClose: true,
            area: ['90%','90%'],
            content:'/setbook',
            // end:function(){
            //     load($('#setBook'));
            // }
        });
    }

    // function load(obj){
    //     //重新加载table
    //     tableIns.reload({
    //         where: obj.field
    //     });
    // }
});