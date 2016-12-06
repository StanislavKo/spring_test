<table width="100%" cellspacing="0">
	<tbody>
		<tr>
			<td width="100%" align="left">
				<div id="event_result"></div>
				<div id="jstree"></div>
			</td>
		</tr>
	</tbody>
</table>
<br />
<br />

<link rel="stylesheet"
	href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/themes/base/jquery-ui.css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.1/jquery.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
<link rel="stylesheet" href="css/jstree.style.min.css" />
<script type="text/javascript" src="js/jstree.js"></script>

<script type="text/javascript">
	$(function() {
		$.jstree.defaults.core.themes.variant = "large";
		$('#jstree')
		.on('changed.jstree', function (e, data) {
		    var i, j, r = [];
		    for(i = 0, j = data.selected.length; i < j; i++) {
		      r.push(data.instance.get_node(data.selected[i]).original.e1Id);
		    }
		  })
		.jstree( ${rubrics} );
	});
</script>
