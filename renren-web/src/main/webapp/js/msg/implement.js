$(function () {
    $("#jqGrid").jqGrid({
        url: '../msg/implement/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'implementId', index: "implement_id",width: 15, key: true ,hidden : true},
            { label: '服务名称', name: 'implementName', width: 60 ,sortable: false},
            { label: 'BeanID', name: 'beanName', width: 60 ,sortable: false},
            { label: '描述', name: 'describe', width: 100 ,sortable: false}
            
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
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });

    

});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
        implement: {
        }
	},
	methods: {
		
		add: function(){
			vm.showList = false;
			vm.titile ="新增服务";
			vm.implement={};
		},
		update:function(){
			var implementId = getSelectedRow();
			if(implementId ==null){
				return;
			}
			vm.showList = false;
			vm.titile = "修改服务"
			$.get("../msg/implement/info/"+implementId,function(r){
				vm.implement = r.implement;
			});
		},
		saveOrUpdate: function (event) {
			var url = vm.implement.implementId==null ? "../msg/implement/save":"../msg/implement/update";
			
			$.ajax({
				type: "POST",
			    url: url,
			    contentType: "application/json",
			    data: JSON.stringify(vm.implement),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
        del: function () {
            var implementIds = getSelectedRows();
            if(implementIds == null){
                return ;
            }
            
            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: "../msg/implement/delete",
                    contentType: "application/json",
                    data: JSON.stringify(implementIds),
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
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});