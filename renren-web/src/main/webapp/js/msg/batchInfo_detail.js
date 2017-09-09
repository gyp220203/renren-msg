$(function () {
	$("#jqGrid").jqGrid({
        url: '../msg/batchInfo/info/'+getUrlParam("batchInfoId"),
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'msgId',index: "msg_id", width: 15, key: true ,hidden : true},
            { label: '状态', name: 'status', width: 30,formatter:function(value, options, row){
                	if(value===1){
                		return '<span class="label label-success">待发送</span>'
                	}
                	if(value===2){
                		return '<span class="label label-success">完成</span>'
                	}
                	if(value===9){
                		return '<span class="label label-danger">错误</span>'
                	}
                	return null
            	}
			},
            { label: '错误信息', name: 'statusMsg', width: 60 },
            { label: '电话号码', name: 'telNumber', width: 60 },
            { label: '模版名称', name: 'templateName', width: 60 },
            { label: '通道名称', name: 'channelName', width: 80 },
            { label: '内容', name: 'content' },
            { label: '花费', name: 'cost', width: 30 },
            { label: '返回消息', name: 'responseMsg' },
            { label: '创建时间', name: 'createTime', width: 85 },
            { label: '发送时间', name: 'sendTime', width: 85 }
        ],
        autowidth:true,
		viewrecords: true,
        height: 696,
        rowNum: 20,
		rowList : [20,50],
        rownumbers: true, 
        rownumWidth: 25, 
        multiselect: false,
        pager: "#jqGridPager",
        jsonReader : {
        	//服务器发送来的page对象
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
        	//代表页数
            page:"page", 
            //代表行数
            rows:"limit", 
            //代表顺序
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });

}


);
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) {
    	console.log(unescape(r[2]));
    	return unescape(r[2]);}
    return null; //返回参数值
}
var vm = new Vue({
	el:'#rrapp',
	data:{
	},
	methods: {
		back: function (event) {
			history.go(-1);
		}
	}
});