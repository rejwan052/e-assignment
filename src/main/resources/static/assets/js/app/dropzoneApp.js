(function($) {
	
	$("div#avatar-upload").addClass('dropzone');
	
	var userId = $("#userId").val();
	console.log("userId :"+userId);
	
	Dropzone.autoDiscover = false;
	var myDropzone = new Dropzone("div#avatar-upload", {
		url : "/upload",
		autoProcessQueue : false,
		/*uploadMultiple : false,*/
		maxFilesize : 0.1, // MB
		maxFiles : 1,
		/*parallelUploads : 1,*/
		dictDefaultMessage : '<span></span> <span>To attach files, drag and drop here</span><br/> <span>OR</span><br/><span>Just Click</span>',
		addRemoveLinks : true,
		init : function() {
			
			/*this.on("maxfilesexceeded", function(file) {
				this.removeAllFiles();
				this.addFile(file);
			})
			this.on("maxfilesreached", function(file) {
				console.log("maxfilesreached", file);
			});*/
			this.on("addedfile", function(file) {
				/*console.log("added", file);*/
				if(this.files.length > 1) {
		            this.removeFile(this.files[0]);
		        }
			});
			$('#avatar-upload-button').on("click", function(e) {

				myDropzone.processQueue();
				
			});
			this.on("error", function(file, response) {
                errorMsg(response);
            });
			this.on("success", function(files, serverResponse) {
				successMsg("User Photo Upload Successfully");
			});
			this.on("complete", function(file) {
				this.removeFile(file);
				
				$.get("/avatar/"+userId,function(data, status){
			        console.log("Data: " + data + "\nStatus: " + status);
			    });
				
			});

		}
	});

	/*var mockFile = {
	  name: 'default image',
	  accepted: true,
	  size: '123456',
	  type: 'image/jpeg',
	  status: Dropzone.ADDED,
	  url: 'http://lorempixel.com/400/400/',
	};

	// Call the default addedfile event handler
	myDropzone.emit("addedfile", mockFile);
	// And optionally show the thumbnail of the file:
	myDropzone.emit("thumbnail", mockFile, myDropzone.createThumbnailFromUrl(mockFile, mockFile.url));
	// Add file to list of files
	myDropzone.files.push(mockFile);
	// Call complete to remove the loader
	myDropzone.emit("complete", mockFile);*/
	
	
	
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
