/*<![CDATA[*/
	var serverContext = '[[@{/}]]';

	$(document).ready(function () {
	    
		$('#updateUserInfoForm').submit(function(event) {
			event.preventDefault();
			updateUserInfo(event);
	    });

	});

	function updateUserInfo(event){
	    var formData= $('#updateUserInfoForm').serialize();
	    $.post(serverContext + "user/updatePersonalInfo",formData ,function(data){
	    	successMsg(data.message);
	    }).fail(function(data) {
	        if(data.responseJSON.error.indexOf("InternalError") > -1){
	        	errorMsg(data.responseJSON.message);
	        }else{
	        	var errors = $.parseJSON(data.responseJSON.message);
	            $.each( errors, function( index,item ){
	                $("#"+item.field+"Error").show().html(item.defaultMessage);
	            });
	        }
	    });
	}

/*]]>*/ 	