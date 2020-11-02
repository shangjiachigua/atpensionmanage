// 送餐情况
$(function(){
	// 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main1'));
	var dataArr1=['2020-01','2020-02','2020-03','2020-04','2020-05','2020-06','2020-07','2020-08'];
	
    // 指定图表的配置项和数据
    var option = {
		color:['#1C9399','#F8695B'],
		legend: {
			selectedMode: false, //取消图例上的点击事件
			icon: 'rect',
			left: '0',
			top:'0',
			itemHeight: 10, //改变圆圈大小
			itemWidth: 10,
			itemGap: 40, // 设置间距
			textStyle: {
				fontSize: 14,
				color: '#666',
				padding: [3, 0, 0, 5]
			},
			data: ['中饭','晚饭'],
		},
        grid: {
	    	top:'50',
            left: '0',   //图表距边框的距离
            right: '0',
            bottom: '0',
            containLabel: true
        },
	    xAxis: {
	        type: 'category',
	        /*boundaryGap: false,*/
	        
            axisLabel: {
                textStyle: {
                	color:'#666',
                    fontSize: 14,
				},
            },
            axisTick:{
                show:false  //去掉y轴刻度线
            },
            
            //坐标轴颜色
            axisLine:{
                show:true,
				lineStyle: {
					color: 'rgba(0, 0, 0, 0.45)',
					width: '1'
				}
            },
	        data: dataArr1
	    },
	    yAxis: [
		    {
		    	type: 'value',
		    	min:0,
                max:600,
                interval:200,
		        splitLine: {
		            show: true ,
		            lineStyle: {
		            	color: '#eee',
		            	width: '1'
		            }
		        },
	            axisLabel: {
	            	textStyle: {
	            		color:'#666',
	                    fontSize: 14,
					},
		            
	            },
	            
	            axisTick:{
	                show:false  //去掉y轴刻度线
	            },
		        //坐标轴颜色
	            axisLine:{
	                show:false,
	            },
		    }
	    ],
	    series: [
	    {
	        name:'中饭',
            type:'bar',
            barWidth: '24',
            barGap:'20%',/*多个并排柱子设置柱子之间的间距*/
  	        arCategoryGap:0,/*多个并排柱子设置柱子之间的间距*/
            itemStyle : {
				normal : {
					barBorderRadius:[2, 2, 0, 0],
					label: {		                       
						show: true,//是否展示
						position: 'top',
						textStyle: {
							fontSize: 14,	
							color:'#000',
						}		                    
					},
					color: '#1C9399',
				}
			},
	        data: [110, 49, 250, 100, 450,110, 49, 250,]
	    },
	    {
	        name:'晚饭',
            type:'bar',
            barWidth: '24',
            baibarGap:1,
            itemStyle : {
				normal : {
					barBorderRadius:[2, 2, 0, 0],
					label: {		                       
						show: true,//是否展示
						position: 'top',
						textStyle: {
							fontSize: 14,		    
							color:'#000',
						}		                    
					},
					color: '#F8695B',
				}
			},
	        data: [450, 120, 360, 150, 350,450, 120, 360,]
	    },]
	};
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
})



// 近七天血糖记录
$(function() {
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('main2'));
	// 指定图表的配置项和数据
	var option = {
		grid: {
			top: '30',
			left: '0', //图表距边框的距离
			right: '0',
			bottom: '0',
			containLabel: true
		},
		/* legend:{
        	selectedMode:false,//取消图例上的点击事件
			top:0,
	    	left:'0',
            itemGap: 25, // 设置间距
            textStyle: { 
            	fontSize:12,
				color:'#fff',
            	padding: [2, 0, 0, 5]
            },
        	ddata:['乒乓球馆','食堂','图书馆']
        }, */
		xAxis: {
			type: 'category',
			/*boundaryGap: false,*/

			axisLabel: {
				textStyle: {
					color: '#666', //坐标值得具体的颜色
					fontSize: 14
				},
				/*//文字倾斜
				interval:0,
				rotate:-40*/
			},
			axisTick: {
				show: false //去掉y轴刻度线
			},
			splitLine: {
				show: true,
				
			},
			//坐标轴颜色
			axisLine: {
				show: false,
				lineStyle: {
					color: 'rgba(0, 0, 0, 0.1)',
					width: '1'
				}
			},
			data: ['09:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00']
		},
		yAxis: [{
			type: 'value',
			min: 0,
			splitLine: {
				show: true,
				lineStyle: {
					color: 'rgba(0, 0, 0, 0.1)',
					width: '1'
				}
			},
			axisLabel: {
				textStyle: {
					color: '#666', //坐标值得具体的颜色
					fontSize: 14
				},

			},

			axisTick: {
				show: false //去掉y轴刻度线
			},
			//坐标轴颜色
			axisLine: {
				show: false,
			},
		}, ],
		series: [{
			name: '处理中',
			type: 'line',
			// symbol: 'circle',
			symbolSize: 3,
			smooth: true,
			itemStyle: {
				normal: {
					label: {
						show: true, //是否展示
						position: 'top',
						textStyle: {
							fontSize: 14,
							color: '#000',
						}
					},
					borderColor: '#1C9399', //折点边框颜色
					borderWidth: 1, //折点边框
				}
			},
			lineStyle: {
				normal: {
					color: '#1C9399',
					width: '2',
				}
			},
			areaStyle: {
				normal: {
					//颜色渐变函数 前四个参数分别表示四个位置依次为左、下、右、上
					color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [{

						offset: 0,
						color: 'rgba(28, 147, 153, 0)'
					}, {
						offset: 0,
						color: 'rgba(28, 147, 153, 0)'
					}, {
						offset: 1,
						color: 'rgba(28, 147, 153, 0.5)'
					}])

				}
			}, //区域颜色渐变

			data: [250, 190, 480, 330, 250, 190, 480, 150, 280]
		}]
	};
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
})

// 血压记录
$(function(){
	// 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main3'));
	var dataArr1=['2020-01','2020-02','2020-03','2020-04','2020-05','2020-06','2020-07','2020-08'];
	
    // 指定图表的配置项和数据
    var option = {
		color:['#1C9399','#F8695B'],
		legend: {
			selectedMode: false, //取消图例上的点击事件
			icon: 'rect',
			left: '0',
			top:'0',
			itemHeight: 10, //改变圆圈大小
			itemWidth: 10,
			itemGap: 40, // 设置间距
			textStyle: {
				fontSize: 14,
				color: '#666',
				padding: [3, 0, 0, 5]
			},
			data: ['中饭','晚饭'],
		},
        grid: {
	    	top:'50',
            left: '0',   //图表距边框的距离
            right: '0',
            bottom: '0',
            containLabel: true
        },
	    xAxis: {
	        type: 'category',
	        /*boundaryGap: false,*/
	        
            axisLabel: {
                textStyle: {
                	color:'#666',
                    fontSize: 14,
				},
            },
            axisTick:{
                show:false  //去掉y轴刻度线
            },
            
            //坐标轴颜色
            axisLine:{
                show:true,
				lineStyle: {
					color: 'rgba(0, 0, 0, 0.45)',
					width: '1'
				}
            },
	        data: dataArr1
	    },
	    yAxis: [
		    {
		    	type: 'value',
		    	min:0,
                max:600,
                interval:200,
		        splitLine: {
		            show: true ,
		            lineStyle: {
		            	color: '#eee',
		            	width: '1'
		            }
		        },
	            axisLabel: {
	            	textStyle: {
	            		color:'#666',
	                    fontSize: 14,
					},
		            
	            },
	            
	            axisTick:{
	                show:false  //去掉y轴刻度线
	            },
		        //坐标轴颜色
	            axisLine:{
	                show:false,
	            },
		    }
	    ],
	    series: [
	    {
	        name:'中饭',
            type:'line',
            
            itemStyle : {
				normal : {
					barBorderRadius:[2, 2, 0, 0],
					label: {		                       
						show: true,//是否展示
						position: 'top',
						textStyle: {
							fontSize: 14,	
							color:'#000',
						}		                    
					},
					color: '#1C9399',
				}
			},
	        data: [110, 49, 250, 100, 450,110, 49, 250,]
	    },
	    {
	        name:'晚饭',
            type:'line',
            itemStyle : {
				normal : {
					barBorderRadius:[2, 2, 0, 0],
					label: {		                       
						show: true,//是否展示
						position: 'top',
						textStyle: {
							fontSize: 14,		    
							color:'#000',
						}		                    
					},
					color: '#F8695B',
				}
			},
	        data: [450, 120, 360, 150, 350,450, 120, 360,]
	    },]
	};
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
})
