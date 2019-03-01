/**
 * 请求Url
 */
var dataUrl = ctx+"/vdo/vdoList";
/**
 * table columns
 */
var columns = [ 
//{
//	'title' : '视频ID',
//	'name' : 'vdoId',
//	'data' : 'vdoId'
//},
{
	'title' : '视频标题',
	'name' : 'vdoName',
	'data' : 'vdoName'
}, {
	'title' : '创建时间',
	'name' : 'createTimeLabel',
	'data' : 'createTimeLabel'
} ];
function columnHandler(data, row, meta){
	var vdoId = meta.vdoId;
	var modify='<button type="button" class="btn btn-primary btn-xs" onclick="edit(\''+vdoId+'\')"><i class="fa fa-pencil"></i>修改</button>';
	var del='<button type="button" class="btn btn-danger btn-xs" onclick="del(\''+vdoId+'\')"><i class="fa fa-trash-o"></i>删除</button>';
	//var opt = view+modify+del;
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