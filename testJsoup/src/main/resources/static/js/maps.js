$(document).ready(function() {
	
	/*tamaño del mapa*/
	var itemsMainDiv = ('#mapid');
	var itemsMainView = ('#mapView');	
	var sampwidth = $(itemsMainDiv).width();
	$(itemsMainView).css({ 'width': sampwidth });
		
	/*responsive*/
	window.addEventListener('resize', function(){
		var sampwidth = $(itemsMainDiv).width();
		$(itemsMainView).css({ 'width': sampwidth });
	});
})

