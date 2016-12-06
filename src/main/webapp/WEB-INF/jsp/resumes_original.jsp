<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table width="100%" cellspacing="0">
	<tbody>
		<tr>
			<td width="100%" align="center">
				<table cellspacing="0">
					<tbody>
						<tr>
							<td align="left" valign="top">
								<div id="event_result"></div>
								<div id="jstree"></div>
							</td>
							<td align="center" valign="top">
								<div id="grid_resumes_cont">
									<div id="grid_resumes"
										style="margin: 20px auto; display: inline-block;"></div>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</td>
		</tr>
	</tbody>
</table>
<br />
<br />

<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/themes/base/jquery-ui.css" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.1/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>

<link rel="stylesheet" href="css/pqgrid.min.css" />
<script type="text/javascript" src="js/pqgrid.dev.js"></script>

<link rel="stylesheet" href="css/jstree.style.min.css" />
<script type="text/javascript" src="js/jstree.js"></script>

<script type="text/javascript" src="js/react.js"></script>
<script type="text/javascript" src="js/react-dom.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/babel-standalone/6.10.3/babel.min.js"></script>

<script type="text/javascript">
	$(function() {
		var data = [];
		var obj = {
			width : <c:out value="${totalWidth900}" default="900"/>,
			height : 400,
			showTop : true,
			showBottom : true,
			collapsible : false,
			showHeader : false,
			roundCorners : false,
			rowBorders : true,
			columnBorders : false,
			editable : false,
			flexHeight : true,
			showHeader : true,
			//flexWidth: true,
			pageModel : {
				type : "local",
				rPP : 20,
				strRpp : "{0}",
				strDisplay : "{0} to {1} of {2}"
			},
			numberCell : {
				show : false
			},
			filterModel : {
				on : true,
				mode : "AND",
				header : true
			},
			stripeRows : false,
			title : "Log files",
			rowDblClick : function(event, ui) {
				var win = window.open('singleResume/' + ui.rowData["e1Id"]
						+ ".txt", '_blank');
				win.focus();
			}
		};
		obj.colModel = [ {
			title : "e1Id",
			width : 100,
			dataType : "string",
			dataIndx : "e1Id"
		}, {
			title : "Name",
			width : <c:out value="${nameWidth250}" default="250"/>,
			dataType : "string",
			align : "left",
			dataIndx : "name"
		}, {
			title : "Header",
			width : <c:out value="${headerWidth400}" default="400"/>,
			dataType : "string",
			align : "left",
			filter : {
				type : "textbox",
				condition : "contain",
				listeners : [ 'keyup' ]
			},
			dataIndx : "header"
		}, {
			title : "Wanted Salary",
			width : <c:out value="${salaryWidth150}" default="150"/>,
			dataType : "string",
			align : "left",
			filter : {
				type : "textbox",
				condition : "contain",
				listeners : [ 'keyup' ]
			},
			dataIndx : "wantedSalaryRub"
		} ];
		var sortMap = new Object;
		var sortObj = new Object;
		sortObj.dataIndx = "wantedSalaryRub";
		sortObj.dataType = "integer";
		sortMap.creationdate = sortObj;
		obj.dataModel = {
			data : data,
			location : "remote",
			sorting : "local",
			sortIndx : "wantedSalaryRub",
			sortDir : "down",
			sortMap : sortMap,
			url : "/rubrics/0/resumes.json",
			getData : function(dataJSON) {
				return {
					curPage : dataJSON.curPage,
					totalRecords : dataJSON.totalRecords,
					data : dataJSON.data
				};
			}
		};
		var gridRds = $("#grid_resumes").pqGrid(obj);

		$.jstree.defaults.core.themes.variant = "large";
		$('#jstree')
		.on('changed.jstree', function (e, data) {
		    var i, j, r = [];
		    for(i = 0, j = data.selected.length; i < j; i++) {
		      r.push(data.instance.get_node(data.selected[i]).original.e1Id);
		    }
		    document.newResume.rubricId.value = r.join(', ');
			$.get( "/rubrics/" + r.join(', ') + "/resumes.json", function( dataInitial ) {
				data = dataInitial;
				
				obj.dataModel = {
						data : data,
						location : "remote",
						sorting : "local",
						sortIndx : "wantedSalaryRub",
						sortDir : "down",
						sortMap : sortMap,
						url : "/rubrics/" + r.join(', ') + "/resumes.json",
						getData : function(dataJSON) {
							return {
								curPage : dataJSON.curPage,
								totalRecords : dataJSON.totalRecords,
								data : dataJSON.data
							};
						}
					};
		        var parentElement = document.getElementById('grid_resumes_cont');
		        while (parentElement.hasChildNodes()) {
		        	parentElement.removeChild(parentElement.lastChild);
		        } 
		        var node = document.createElement("div");
		        node.id = "grid_resumes";
		        parentElement.appendChild(node);
		        
				gridRds = $("#grid_resumes").pqGrid(obj);
			});
		  })
		.jstree( ${rubrics} );
	});
</script>