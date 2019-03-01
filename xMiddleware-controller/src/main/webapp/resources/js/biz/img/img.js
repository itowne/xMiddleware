/**
 * 请求Url
 */
var dataUrl = ctx+"/img/imgList";
/**
 * table columns
 */
var columns = [ 
//{
//	'title' : '图片ID',
//	'name' : 'imgId',
//	'data' : 'imgId'
//}, 
{
	'title' : '图片标题',
	'name' : 'imgName',
	'data' : 'imgName'
}, {
	'title' : '创建时间',
	'name' : 'createTimeLabel',
	'data' : 'createTimeLabel'
} ];
function columnHandler(data, row, meta){
	var imgId = meta.imgId;
	var modify='<button type="button" class="btn btn-primary btn-xs" onclick="edit(\''+imgId+'\')"><i class="fa fa-pencil"></i>修改</button>';
	var del='<button type="button" class="btn btn-danger btn-xs" onclick="del(\''+imgId+'\')"><i class="fa fa-trash-o"></i>删除</button>';
	var view ='<button type="button" class="btn btn-info btn-xs" onclick="view(\''+imgId+'\')"><i class="fa fa-eye"></i>预览</button>';
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