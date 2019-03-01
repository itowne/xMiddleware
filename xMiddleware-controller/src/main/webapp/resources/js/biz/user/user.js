/**
 * 请求Url
 */
var dataUrl = ctx+"/user/userList";
/**
 * table columns
 */
var columns = [ 
//{
//	'title' : '用户ID',
//	'name' : 'userId',
//	'data' : 'userId'
//}, 
{
	'title' : '登录名',
	'name' : 'loginName',
	'data' : 'loginName'
} ,{
	'title' : '姓名',
	'name' : 'realName',
	'data' : 'realName'
}, {
	'title' : '手机号',
	'name' : 'mobile',
	'data' : 'mobile'
}, {
	'title' : '角色',
	'name' : 'userRoleLabel',
	'data' : 'userRoleLabel'
}, {
	'title' : '创建时间',
	'name' : 'createTimeLabel',
	'data' : 'createTimeLabel'
} ];
function columnHandler(data, row, meta){
	var userId = meta.userId;
	var ln = meta.loginName
	var modify='<button type="button" class="btn btn-primary btn-xs" onclick="edit(\''+userId+'\')"><i class="fa fa-pencil"></i>修改</button>';
	var del='<button type="button" class="btn btn-danger btn-xs" onclick="del(\''+userId+'\')"><i class="fa fa-trash-o"></i>删除</button>';
	var opt = modify+del;
	var optSpan='';
	if(ln==loginName){
		optSpan='<span style="display:inline">'+modify+'</span>';
	}else{
		optSpan='<span style="display:inline">'+opt+'</span>';
	}
	return optSpan;
}
/**
 * 操作
 */
var columnDefs = [{
	title : '操作',
	targets : [ 5 ],
	render:function(data, row, meta){
		return columnHandler(data, row, meta);
	}
}];
/**
 * 初始化table
 */
initDataTable("datatable", dataUrl, columns, columnDefs);