(function($) {
	
	$.ajaxSetup({
		error : function(jqXHR, textStatus, errorThrown) {
			console.log("ajax setup error: "+jqXHR+" jqXHR.status :"+jqXHR.status);
	        if (jqXHR.responseJSON.status == 901) {
	        	sessionTimeOutModal();
	        } 
	    }
	});
	
	function sessionTimeOutModal()
	{
		$("#sessionTimeOutModal").modal({
		});
	}

})(jQuery);

