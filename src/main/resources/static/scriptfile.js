$(document).ready(function() {

	$("#addMovieForm").submit(function(event) {
		event.preventDefault();

		var movie = {};
		movie["name"] = $("#movieName").val();
		
		$("#addButton").prop("disabled", true);
		console.log("test: " + JSON.stringify(movie));

		$.ajax({
			type: "POST",
			contentType: "application/json",
			url: "/movies",
			data: JSON.stringify(movie),
			dataType: 'text',
			cache: false,
			timeout: 600000,
			success: function(data) {
				console.log("SUCCESS : " + data);
				location.reload();
			},
			error: function(e) { 
				console.log("ERROR : ", e);
			}
		});

	});

});