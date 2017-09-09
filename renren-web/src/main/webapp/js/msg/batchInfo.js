$(function () {
    $("#jqGrid").jqGrid({
        url: '../msg/batchInfo/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'batchInfoId',index: "batchInfo_id", width: 15, key: true ,hidden : true},
            { label: '操作用户', name: 'username', width: 60 },
            { label: '发送时间', name: 'createTime', width: 60 },
            { label: '短信总数', name: 'totalNumber', width: 40 },
            { label: '通过校验', name: 'checkNumber', width: 40 },
            { label: '成功发送', name: 'sendNumber', width: 40 }
        ],
		viewrecords: true,
        height: 696,
        rowNum: 20,
		rowList : [20,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
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
    new AjaxUpload('#upload', {
        action: '../msg/batchInfo/upload',
        name: 'file',
        autoSubmit:true,
        responseType:"json",
        onSubmit:function(file, extension){
            if (!(extension && /^(xls)$/.test(extension.toLowerCase()))){
                alert('只支持xls格式的文件！');
                return false;
            }
        },
        onComplete : function(file, r){
            if(r.code == 0){
                alert(r.msg);
                vm.reload();
            }else{
                alert(r.msg);
            }
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{},
	methods: {
		el:'#rrapp',
		info: function(){
			var batchInfoId = getSelectedRow();
			if(batchInfoId == null){
				return ;
			}
			location.href = "batchInfo_detail.html?batchInfoId="+batchInfoId;
		},
        del: function () {
            var templateIds = getSelectedRows();
            if(templateIds == null){
                return ;
            }
            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: "../msg/batchInfo/delete",
                    contentType: "application/json",
                    data: JSON.stringify(templateIds),
                    success: function(r){
                        if(r.code === 0){
                            alert('操作成功', function(){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
		reload: function () {
			vm.showList = true;
			$("#jqGrid2")==null;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});