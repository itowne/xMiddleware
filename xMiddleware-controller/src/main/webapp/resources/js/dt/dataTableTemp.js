/**
 * init dataTable 
 * @param {Object} id
 * @param {Object} url
 * @param {Object} columns
 * @param {Object} columnDefs
 */
function initDataTable(id, url,columns, columnDefs) {
	var table=$("#" + id).dataTable({
		columns: columns,
		columnDefs: columnDefs,
		bServerSide: true,
		iDisplayStart:0,
		iDisplayLength: 10,
		bScrollCollapse: true,
		bAutoWidth:false,
		bProcessing: true,
		pagingType: "full_numbers",
		searching: false,
		bLengthChange: false,
		ordering: false,
        //lengthChange: true,
        //lengthMenu: [5, 10, 15, 20],
		destroy: true,
		ajax: {
			type: "POST",
			url: url,
			dataSrc: "aaData",
			data: function(reqParam) {
			}
		},
		language: {
			lengthMenu: "每页  _MENU_ 条记录",
			zeroRecords: "没有找到记录",
			info: "当前第 _START_ - _END_ 条　共计 _TOTAL_ 条",
			infoEmpty: "记录数为0",
			infoFiltered: "(从 _MAX_ 条记录过滤)",
			processing: "正在获取数据，请稍后...",
			paginate: {
				first: "首页",
				last: "末页",
				previous: "上一页",
				next: "下一页"
			}
		}
	});
	return table;
}