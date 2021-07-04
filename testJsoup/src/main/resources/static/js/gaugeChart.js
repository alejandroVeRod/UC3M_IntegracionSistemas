$(document).ready(function() {	
	/*<![CDATA[*/
	var gaugeOptions = {
		    chart: {
		        type: 'solidgauge'
		    },
		    title: null,
		    pane: {
		        center: ['50%', '75%'],
		        size: '100%',
		        startAngle: -90,
		        endAngle: 90,
		        background: {
		            backgroundColor:
		                Highcharts.defaultOptions.legend.backgroundColor || '#EEE',
		            innerRadius: '60%',
		            outerRadius: '100%',
		            shape: 'arc'
		        }
		    },

		    tooltip: {
		        enabled: false
		    },

		    // the value axis
		    yAxis: {
		        stops: [
		            [0.1, '#DF5353'], // green
		            [0.5, '#DDDF0D'], // yellow
		            [0.9, '#55BF3B'] // red
		        ],
		        lineWidth: 0,
		        minorTickInterval: null,
		        tickAmount: 2,
		        title: {
		            y: -50
		        },
		        labels: {
		            y: 16
		        }
		    },
		    plotOptions: {
		        solidgauge: {
		            dataLabels: {
		                y: 5,
		                borderWidth: 0,
		                useHTML: true
		            }
		        }
		    }
		};

		// The speed gauge
		var chartSpeed = Highcharts.chart('container-speed', Highcharts.merge(gaugeOptions, {
		    yAxis: {
		        min: 0,
		        max: 3,
		        title: {
		            text: 'Puntuación'
		        }
		    },

		    credits: {
		        enabled: false
		    },

		    series: [{
		        name: 'Speed',
		        data: [2.67],
		        dataLabels: {
		            format:
		                '<div style="text-align:center">' +
		                '<span style="font-size:18px">{y}</span><br/>' +
		                '<span style="font-size:12px;opacity:0.4"> </span>' +
		                '</div>'
		        },
		    }]

		}));
/*]]>*/
})