$(function () {
    $("#jqGrid").jqGrid({
        url: '../msg/channel/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'channelId',index: "channel_id", width: 10, key: true ,hidden : true},
            { label: '通道名称', name: 'channelName',index: "channel_Name", width: 40 ,sortable: false},
			{ label: '状态', name: 'status', width: 15,formatter:function(value, options, row){
				return value === 1 ? 
					'<span class="label label-success">正常</span>' : 
					'<span class="label label-danger">禁用</span>';
				}
			},
            { label: '登录名', name: 'username', width: 25 ,sortable: false},
            { label: '登录密码', name: 'password', width: 70 ,sortable: false},
            { label: '发送url', name: 'url4send', width: 100 ,sortable: false},
            { label: '查询url', name: 'url4query', width: 100 ,sortable: false},
            { label: '签名', name: 'signature', width: 15 ,sortable: false},
            { label: '余额', name: 'balance', width: 15}
            
        ],
		viewrecords: true,
        height: 696,
        rowNum: 15,
		rowList : [15,30],
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
		userList:{},
		implementList:{},
        channel: {
        	status:1,
        	balance:0,
        	userIdList:[]
        }
	},
	methods: {
		
		add: function(){
			vm.showList = false;
			vm.titile ="新增通道";
			vm.userList={};
			vm.channel={status:1,balance:0,userIdList:[]};
			this.getUserList();
			this.getImplementList();
		},
		update:function(){
			var channelId = getSelectedRow();
			if(channelId ==null){
				return;
			}
			vm.showList = false;
			vm.titile = "修改通道"
				
			$.get("../msg/channel/info/"+channelId,function(r){
				vm.channel = r.channel;
			});
			this.getUserList();
			this.getImplementList();
		},
		saveOrUpdate: function (event) {
			var url = vm.channel.channelId==null ? "../msg/channel/save":"../msg/channel/update";
			
			$.ajax({
				type: "POST",
			    url: url,
			    contentType: "application/json",
			    data: JSON.stringify(vm.channel),
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
            var channelIds = getSelectedRows();
            if(channelIds == null){
                return ;
            }
            
            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: "../msg/channel/delete",
                    contentType: "application/json",
                    data: JSON.stringify(channelIds),
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
        updateBalance: function(){
        	$.ajax({
                type: "GET",
                url: "../msg/channel/updateBalance",
                contentType: "application/json",
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
		},
        getUserList: function(){
			$.get("../msg/channel/selectUser", function(r){
				vm.userList = r.list;
			});
		},
		getImplementList: function(){
			$.get("../msg/implement/select", function(r){
				vm.implementList = r.list;
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