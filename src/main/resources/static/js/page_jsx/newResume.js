function submit_form() {
	var str = $("#resume").serialize();

	$.ajax({
		type : "post",
		data: str,
		url: "/rubrics/newResume",
		async : false,
		dataType : "json",
		success : function(dataJSON) {
			// alert("dataJSON:" + dataJSON);
			showErrors(dataJSON.msg, dataJSON.bindingResult);
		}
	});
}

function showErrors(msg, bindingResult) {
	var Message = React.createClass({
	  render: function() {
	    return (
	      <div>
	        {msg}
	      </div>
	    );
	  }
	});

	ReactDOM.render(
		<Message/>,
		document.getElementById('newResume-alert-single-msg')
	);

	// particular error messages
	
	var fields = ["rubricId", "e1Id", "name", "header", "wantedSalaryRub"];
	fields.forEach(function(item, i, fields) {
		var EmptyDiv = React.createClass({
			  render: function() {
			    return (
			      <div>
			      </div>
			    );
			  }
			});
		ReactDOM.render(
				<EmptyDiv/>,
				document.getElementById('newResume-error-' + item)
			);
	});
	
	if (bindingResult == null) {
		document.newResume.e1Id.value = "";
		document.newResume.name.value = "";
		document.newResume.header.value = "";
		document.newResume.wantedSalaryRub.value = "";
		return;
	}
	
	for (var errorFieldKey in bindingResult) {
		if (bindingResult.hasOwnProperty(errorFieldKey)) {
			// callback(errorField, bindingResult[errorField]);
			var Errors = React.createClass({
				render: function() {
					var data = this.props.data;
					var ErrorFieldItemTemplate = data.map(function(item, index) {
						return ( 
							<div key = {index}>
								<p className = "news__author" >{item}: </p>
							</div>
						)
					})

					return ( 
						<div className = "news">{ErrorFieldItemTemplate}</div>
					);
				}
			});
			var ErrorField = React.createClass({
				render: function() {
					return ( 
						<div>
							<Errors data = {bindingResult[errorFieldKey]}/>
						</div>
					);
				}
			});
			ReactDOM.render(
					<ErrorField/>,
					document.getElementById('newResume-error-' + errorFieldKey)
				);
		}
	}
	
}
