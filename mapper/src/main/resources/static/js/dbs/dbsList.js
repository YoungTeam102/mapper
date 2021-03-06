layui.config({
    version: false
    ,debug: false
}).use(['element', 'table', 'layer', 'jquery', 'form'], function(){
    var element = layui.element,
        table = layui.table,
        layer = layui.layer,
        $ = layui.jquery,
        form = layui.form;

    table.render({
        id: 'dbList'
        ,elem: '#dbList'
        ,height: 332
        ,url: _contextPath + '/database/pageDatabase'
        ,page: true
        ,cols: [[
            {checkbox: true}
            ,{field: 'selfDefineName', title: '自定义名称'}
            ,{field: 'databaseName', title: '数据库名'}
            ,{field: 'databaseAddress', title: '数据库链接'}
            ,{field: 'databasePort', title: '数据库端口'}
            ,{field: 'databaseType', title: '数据库类型', templet:'#databaseType'}
            ,{field: 'packageUrl', title: '生成包路径'}
            ,{field: 'updatedAt', title: '最后更新时间', sort: true}
            ,{fixed: 'right', width:150, align:'center', toolbar: '#dbListTool'}
        ]]
    });

    $("#submitBtn").click(function () {
        var vdbsName = $("#dbsName").val();
        table.reload('dbList', {
            where: {
                dbsName: vdbsName
            }
            ,page: {
                curr: 1
            }
        });
    });

    //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
    table.on('tool(dbList)', function(obj){
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象

        if(layEvent === 'del'){ //删除
            layer.confirm('真的要删除这个数据库吗', function(index){
                $.ajax({
                    type: 'GET',
                    url: "/mapper/database/delDatabase/"+obj.data.id,
                    success: function (result) {
                        if(result.code == 200){
                            layer.msg("删除成功");
                        }else {
                            layer.msg(result.message);
                        }
                    },
                    dataType: "json"
                });
                //删除对应行（tr）的DOM结构，并更新缓存
                obj.del();
                layer.close(index);
            });
        } else if(layEvent === 'edit'){ //编辑
            layer.open({
                type: 2,
                title: '编辑数据库',
                content: '/mapper/to/editDatabase/'+obj.data.id,
                area: ['800px', '650px'],
                anim: 6,
                btn: ['关闭'],
                yes: function(index, layero){
                    //如果设定了yes回调，需进行手工关闭
                    layer.close(index);
                    //重新访问，刷新列表
                    window.location = '/mapper/to/databaseList';
                }
            });
        }
    });

    $("#addBtn").click(function () {
        layer.open({
            type: 2,
            title: '新增数据库',
            content: '/mapper/to/addDatabase/',
            area: ['800px', '650px'],
            anim: 6,
            btn: ['关闭'],
            yes: function(index, layero){
                //如果设定了yes回调，需进行手工关闭
                layer.close(index);
                //重新访问，刷新列表
                window.location = '/mapper/to/databaseList';
            }
        });
    });
});