function drawChart(id, km, uso, consumo, precio, evaluacion){
	var fecha = new Date();
	var anyo = fecha.getFullYear();
	//console.log(item);
	/*<![CDATA[*/
		Highcharts.chart(id, {
	        chart: {
	        	renderTo: id,
	            polar: true,
	            type: 'line'
	        },
	        accessibility: {
	            description: 'A spiderweb chart compares the allocated budget against actual spending within an organization. The spider chart has six spokes. Each spoke represents one of the 6 departments within the organization: sales, marketing, development, customer support, information technology and administration. The chart is interactive, and each data point is displayed upon hovering. The chart clearly shows that 4 of the 6 departments have overspent their budget with Marketing responsible for the greatest overspend of $20,000. The allocated budget and actual spending data points for each department are as follows: Sales. Budget equals $43,000; spending equals $50,000. Marketing. Budget equals $19,000; spending equals $39,000. Development. Budget equals $60,000; spending equals $42,000. Customer support. Budget equals $35,000; spending equals $31,000. Information technology. Budget equals $17,000; spending equals $26,000. Administration. Budget equals $10,000; spending equals $14,000.'
	        },
	        title: {
	            text: '',
//	            x: -80
	        },
	        pane: {
	            size: '50%' /*80*/
	        },
	        xAxis: {	        	
	            categories: ['Km', 'Uso', 'Consumo', 'Precio', 'Evaluación'],	
	            labels: {
	                formatter: function () {
	                    if ('Km' === this.value) {	  
	                    	return'<img src="../css/fontawesome-free-5.11.2/svgs/solid/road.svg" alt="" style="vertical-align: middle; width: 20px; height: 20px"/>';
	                    } 
	                    if ('Uso' === this.value) {	                    	
	                    	return'<img src="../css/fontawesome-free-5.11.2/svgs/solid/clock.svg" alt="" style="vertical-align: middle; width: 20px; height: 20px"/>';
	                    } 
	                    if ('Consumo' === this.value) {	                    	
	                    	return'<img src="../css/fontawesome-free-5.11.2/svgs/solid/gas-pump.svg" alt="" style="vertical-align: middle; width: 20px; height: 20px"/>';
	                    } 
	                    if ('Precio' === this.value) {	                    	
	                    	return'<img src="../css/fontawesome-free-5.11.2/svgs/solid/euro-sign.svg" alt="" style="vertical-align: middle; width: 20px; height: 20px"/>';
	                    } 
	                    if ('Evaluación' === this.value) {	                    	
	                    	return'<img src="../css/fontawesome-free-5.11.2/svgs/solid/clipboard.svg" alt="" style="vertical-align: middle; width: 20px; height: 20px"/>';
	                    } 
	                },
	                useHTML: true
	            },
	            tickmarkPlacement: 'on',
	            lineWidth: 0
	        },
	        yAxis: {
	            gridLineInterpolation: 'polygon',
	            lineWidth: 0,
	            min: 0
	        },
	        tooltip: {
	            shared: true,
	            pointFormat: '<span style="color:{series.color}">{series.name}: <b>{point.y:,.0f}</b><br/>'
	        },
	        legend: {
	            align: 'right',
	            verticalAlign: 'middle'
	        },
	        series: [{
	        	name: 'Valor',
	            //data: [parseInt(km), parseInt(uso), parseInt(consumo), parseInt(precio), parseInt(evaluacion)],
	            pointPlacement: 'on',
	            colorByPoint:true,
	            data: [{
                    name: 'Kilometraje (km)',
                    y: parseInt(km),
                },{
                    name: 'Consumo (km/L)',
                    y: parseInt(consumo)
                },{
                    name: 'Antiguedad (años)',
                    y:parseInt(anyo-uso)
                },{
                    name: 'Precio (euros)',
                    y:parseInt(precio)
                },{
                    name: 'Evaluación (1-3)',
                    y: parseInt(evaluacion)
                }]
	        }],

	        responsive: {
	            rules: [{
	                condition: {
	                    maxWidth: 300
	                },
	                chartOptions: {
	                    legend: {
	                        align: 'center',
	                        verticalAlign: 'bottom'
	                    },
	                    pane: {
	                        size: '50%'
	                    }
	                }
	            }]
	        }

	    });
	 
	/*]]>*/
}