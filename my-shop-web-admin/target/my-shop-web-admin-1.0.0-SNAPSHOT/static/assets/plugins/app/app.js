var App=function(){
    //ICheck
    var _masterCheckbox;
    var _checkbox;
    //用于存放ID的数组
    var _idArray;
    /**
     * 私有方法 初始化iCheck
     */
    var handlerInitCheck=function(){
        //激活
        $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
            checkboxClass: 'icheckbox_minimal-blue',
            radioClass   : 'iradio_minimal-blue'
        })
    }
    //获取控制端Checkbook
    _masterCheckbox=$('input[type="checkbox"].minimal.icheck_master');
    //获取全部Checkbox
    _checkbox=$('input[type="checkbox"].minimal');
    /**
     * Checkbox全选功能
     */
    var handlerCheckboxAll=function () {
        _masterCheckbox.on("ifClicked",function (e) {
            //未选中
            if(e.target.checked){
                _checkbox.iCheck("uncheck")
            }
            // 选中
            else {
                _checkbox.iCheck("check")
            }
        });
    };
    /**
     * 批量删除
     */
    var handlerDeleteMulti=function (url) {
        _idArray=new Array();
        //将选中元素的ID放入数组
        _checkbox.each(function () {
            var _id=$(this).attr("id");
            if(_id!=null&&_id!="undefine"&&$(this).is(":checked")){
                _idArray.push(_id);
            }
        });
        //对数组进行判断
        if(_idArray.length===0){
            $("#modal-message").html("您还没有选择一条数据，至少选择一条数据");
        }
        else{
            $("#modal-message").html("您确定删除所选数据");
        }
        $("#modal-default").modal("show");
        $("#btnModalOk").bind("click",function () {
            del();
        });

        /**
         * 当前函数的私有函数，删除数据
         */
        function del(){
            $("#modal-default").modal("hide");
            //如果没有选择数据项处理
            if(_idArray.length===0){

            }
            //删除操作
            else{
                setTimeout(function () {
                    $.ajax({
                        "url":url,
                        "type":"POST",
                        //设置为同步
                        "data":{"ids":_idArray.toString()},
                        "dataType":"JSON",
                        "success":function(data){
                            //删除成功
                            if(data.status===200){
                                window.location.reload();
                            }
                            else {
                                $("#btnModalOk").unbind("click");
                                $("#btnModalOk").bind("click",function () {
                                    $("#modal-default").modal("hide");
                                });

                                $("#modal-message").html(data.message);
                                $("#modal-default").modal("show");

                            }
                        }
                    });
                },500);

            }
        }
    };

    return  {
        init:function (){
            handlerInitCheck();
            handlerCheckboxAll();
        },
        getCheckbox:function () {
            return _checkbox;
        },
        deleteMulti:function (url) {
            handlerDeleteMulti(url);
        }

    }
}();
$(document).ready(function () {
    App.init();
});