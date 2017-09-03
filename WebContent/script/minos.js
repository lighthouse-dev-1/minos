window.onload = function(){
	var fileArea  = document.getElementById('drag-drop-area');
	var fileInput = document.getElementById('fileInput');

	fileArea.addEventListener('dragover', function(evt) {
	    evt.preventDefault();
	    fileArea.classList.add ('dragover');
	});

	fileArea.addEventListener('dragleave' , function(evt) {
        evt.preventDefault();
	    fileArea.classList.remove('dragover');
	});

	fileArea.addEventListener('drop' , function(evt) {
	    evt.preventDefault();
	    fileArea.classList.remove('dragenter');
	    var files = evt.dataTransfer.files;
	    fileInput.files = files;
	});
}

addTask = function() {
    var lastTask   = $('.task').last();
    var html       = lastTask.html();
	lastTask.after('<div class=task>' + html + '</div>');
	
	var currentCnt = $('#taskCnt').val();
	$('#taskCnt').val( Number(currentCnt)+1 );
};

delTask = function(me){
    if($(me).parents().find('.task').length <= 1){
        alert('Atleast one task is necessary.');
        return false;
    }
  
    $(me).parent().parent().remove();  

    var currentCnt = $('#taskCnt').val();
	$('#taskCnt').val( Number(currentCnt)-1 );
};