$(function () {
    $("#jqGrid").jqGrid({
        url: '../msg/template/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'templateId',index: "template_id", width: 15, key: true },
            { label: '模版名称', name: 'templateName', width: 60 ,sortable: false},
            { label: '模版内容', name: 'templateBody', width: 120 ,sortable: false},
            { label: '模版签名', name: 'signature', width: 40 ,sortable: false},
            { label: '绑定通道', name: 'channelId',index: "channel_id", width: 40 }
            
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
		channelList:{},
        template: {
        	parameterNo:0,
        	signature:''
        }
	},
	methods: {
		
		add: function(){
			vm.showList = false;
			vm.titile ="新增通道";
			vm.template={parameterNo:0,signature:''};
			this.getChannelList();
			
		},
		update:function(){
			var templateId = getSelectedRow();
			if(templateId ==null){
				return;
			}
			vm.showList = false;
			vm.titile = "修改通道"
			$.get("../msg/template/info/"+templateId,function(r){
				vm.template = r.template;
			});
			this.getChannelList();
		},
		saveOrUpdate: function (event) {
			var url = vm.template.templateId==null ? "../msg/template/save":"../msg/template/update";
			
			$.ajax({
				type: "POST",
			    url: url,
			    contentType: "application/json",
			    data: JSON.stringify(vm.template),
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
            var templateIds = getSelectedRows();
            if(templateIds == null){
                return ;
            }
            
            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: "../msg/template/delete",
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
        updateSignature: function(r){
        	vm.template.signature=r;
        },
        getChannelList: function(){
			$.get("../msg/channel/select", function(r){
				vm.channelList = r.list;
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