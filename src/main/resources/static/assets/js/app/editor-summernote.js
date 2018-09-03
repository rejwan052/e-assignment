/**
 * 
 */

$(document).ready(function() {
	$('#summernote').summernote({
        height: 200,
        placeholder: 'write assignment instructions here...',
        fontNames: ["Helvetica", "sans-serif", "Arial", "Arial Black", "Comic Sans MS", "Courier New","Times New Roman"],
        fontNamesIgnoreCheck: ["Helvetica", "sans-serif", "Arial", "Arial Black", "Comic Sans MS", "Courier New","Times New Roman"],
        dialogsFade: true,
		toolbar: [
	          // [groupName, [list of button]]
	          ["style", ["style"]],
	          ['font', ['strikethrough', 'superscript', 'subscript']],
	          ['fontname', ['fontname']],
	          ['fontsize', ['fontsize']],
	          ['para', ['ul', 'ol', 'paragraph']],
	          ['height', ['height']],
	          ['insert', ['link', 'table', 'hr']],
	          ['Misc', ['fullscreen', 'undo', 'redo', 'help']]
	        ]
      });
});