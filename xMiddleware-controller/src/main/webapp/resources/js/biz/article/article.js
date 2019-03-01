/**
 * 请求Url
 */
var dataUrl = ctx+"/article/articleList";
/**
 * table columns
 */
var columns = [ 
//{
//	'title' : '内容ID',
//	'name' : 'articleId',
//	'data' : 'articleId'
//},
	{
	'title' : '内容标题',
	'name' : 'title',
	'data' : 'title',
	'width':'49%'
}, {
	'title' : '创建时间',
	'name' : 'createTimeLabel',
	'data' : 'createTimeLabel'
} ];
function columnHandler(data, row, meta){
	var articleId = meta.articleId;
	var isTop = meta.isTop;
	var modify='<button type="button" class="btn btn-primary btn-xs" onclick="edit(\''+articleId+'\')"><i class="fa fa-pencil"></i>修改</button>';
	var del='<button type="button" class="btn btn-danger btn-xs" onclick="del(\''+articleId+'\')"><i class="fa fa-trash-o"></i>删除</button>';
	var view ='<button type="button" class="btn btn-info btn-xs" onclick="view(\''+articleId+'\')"><i class="fa fa-eye"></i>预览</button>';
	var artApply='<button type="button" class="btn btn-success btn-xs" onclick="applyList(\''+articleId+'\')"><i class="fa fa-check-circle-o"></i>报名</button>';
	if(isTop=="YES"){
		var isTop = '<button type="button" class="btn btn-danger btn-xs" onclick="isTop(\''+articleId+'\',\'NO\')"><i class="fa fa-thumb-tack"></i>不置顶</button>';
	}else{
		var isTop = '<button type="button" class="btn btn-default btn-xs" onclick="isTop(\''+articleId+'\',\'YES\')"><i class="fa fa-thumb-tack"></i>置顶</button>';
	}
	var opt = view+artApply+modify+isTop+del;
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