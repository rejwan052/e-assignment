(function($) {
	
	$("div#assignment-documents-dropzone").addClass('dropzone');
	
	Dropzone.autoDiscover = false;
	var form = $("#assignment-documents-form");
	var actionUrl = form.attr('action');
	console.log("assignment documents upload action url :"+actionUrl);
	
	var assignmentDocumentsDropzone = new Dropzone("div#assignment-documents-dropzone", {
		url:actionUrl,
		acceptedFiles: ".doc,.docx,.pdf,.zip,.rar",
		autoProcessQueue : false,
		uploadMultiple : true,
		maxFilesize : 25, // MB
		parallelUploads : 5,
		maxFiles : 100,
		dictDefaultMessage : '<span></span> <span>To attach files, drag and drop here</span><br/> <span>OR</span><br/><span>Just Click</span>',
		addRemoveLinks : true,
		dictRemoveFile: 'Remove',
		init : function() {
			
			this.on("addedfile", function(file) {
				/*console.log("added", file);*/
				var ext = file.name.split('.').pop();
				console.log("added file extension :"+ext);
			    if (ext == "pdf") {
			        $(file.previewElement).find(".dz-image").append( '<i class="icon fa-file-pdf-o" aria-hidden="true"></i>' );
			    } else if (ext.indexOf("doc") != -1) {
			        $(file.previewElement).find(".dz-image").append( '<i class="icon fa-file-word-o" aria-hidden="true"></i>' );
			    } else if (ext.indexOf("xls") != -1) {
			        $(file.previewElement).find(".dz-image").append( '<i class="icon fa-file-excel-o" aria-hidden="true"></i>' );
			    }else if (ext.indexOf("zip") != -1) {
			        $(file.previewElement).find(".dz-image").append( '<i class="icon fa-file-zip-o" aria-hidden="true"></i>' );
			    }
			});
					
			$('#assignment-documents-upload-button').on("click", function(e) {
	
				assignmentDocumentsDropzone.processQueue();
				
			});
			
			this.on("error", function(file, response) {
	            errorMsg(response);
	        });
			
			this.on("successmultiple", function(files, serverResponse) {
				successMsg("Assignment Document(s) Upload Successfully");
			});
			
			this.on("complete", function(file) {
				this.removeFile(file);
				retrieveAssignmentDocuments();
			});

		}
	});
	
	
	function retrieveAssignmentDocuments() {
		var url = '/teacher/assignment';
		if ($('#assignmentId').val() != '') {
			url = url + '/' + $('#assignmentId').val()+'/documents?isFragment=true';
		}
		
		console.log("Assignment documents url: "+url);
		
		$("#assignmentDocuments").load(url);
	}
	
	
	function successMsg(msg){
		
		$.notify(msg, {
			icon: 'glyphicon glyphicon-ok-circle',
			type: "success",
			placement: {
				from: "bottom",
				align: "right"
			},
			offset: 20,
			spacing: 10,
			z_index: 1031,
			delay: 5000,
			timer: 1000,
			delay: 5000,
			animate: {
				enter: 'animated fadeInDown',
				exit: 'animated fadeOutUp'
			},
			newest_on_top: true
		});
	}

	function errorMsg(msg){
		
		$.notify(msg, {
			type: "danger",
			icon: 'glyphicon glyphicon-warning-sign',
			delay: 5000,
			placement: {
				from: "bottom",
				align: "right"
			},
			offset: 20,
			spacing: 10,
			z_index: 1031,
			delay: 5000,
			timer: 1000,
			delay: 5000,
			animate: {
				enter: 'animated fadeInDown',
				exit: 'animated fadeOutUp'
			},
			newest_on_top: true
		});
		
	}

})(jQuery);
