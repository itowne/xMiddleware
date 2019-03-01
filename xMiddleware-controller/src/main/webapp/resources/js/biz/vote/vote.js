/**
 * 请求Url
 */
var dataUrl = ctx+"/vote/voteList";
/**
 * table columns
 */
var columns = [ 
//{
//	'title' : '投票ID',
//	'name' : 'voteId',
//	'data' : 'voteId'
//}, 
{
	'title' : '投票标题',
	'name' : 'voteTitle',
	'data' : 'voteTitle'
}, {
	'title' : '创建时间',
	'name' : 'createTimeLabel',
	'data' : 'createTimeLabel'
} ];
function columnHandler(data, row, meta){
	var voteId = meta.voteId;
	var modify='<button type="button" class="btn btn-primary btn-xs" onclick="edit(\''+voteId+'\')"><i class="fa fa-pencil"></i>修改</button>';
	var del='<button type="button" class="btn btn-danger btn-xs" onclick="del(\''+voteId+'\')"><i class="fa fa-trash-o"></i>删除</button>';
	var opt = modify+del;
	var optSpan='<span style="display:inline">'+opt+'</span>';
	return optSpan;
}
/**
 * 操作
 */
var columnDefs = [{
	title : '操作',
	targets : [ 2 ],
	render:function(data, row, meta){
		return columnHandler(data, row, meta);
	}
}];
/**
 * 初始化table
 */
initDataTable("datatable", dataUrl, columns, columnDefs);