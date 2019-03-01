/**
 * 请求Url
 */
var dataUrl = ctx + "/artApply/artApplyList/"+articleId;
/**
 * table columns
 */
var columns = [{
	'title': '报名人',
	'name': 'applyName',
	'data': 'applyName'
}, {
	'title': '手机',
	'name': 'mobile',
	'data': 'mobile'
}, {
	'title': '报名时间',
	'name': 'createTimeLabel',
	'data': 'createTimeLabel'
}];
/**
 * 操作
 */
var columnDefs = [];
/**
 * 初始化table
 */
initDataTable("datatable", dataUrl, columns, columnDefs);