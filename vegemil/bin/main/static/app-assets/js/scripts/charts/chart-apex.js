/*=========================================================================================
    File Name: chart-apex.js
    Description: Apexchart Examples
    ----------------------------------------------------------------------------------------
    Item Name: Vuexy  - Vuejs, HTML & Laravel Admin Dashboard Template
    Author: PIXINVENT
    Author URL: http://www.themeforest.net/user/pixinvent
==========================================================================================*/
$(function () {
  'use strict';

  var flatPicker = $('.flat-picker'),
    isRtl = $('html').attr('data-textdirection') === 'rtl',
    chartColors = {
      column: {
        series1: '#ff386c',
        series2: '#4285f4',
	    series3: '#fbb900',
        bg: '#cbcbcb'
      },
      success: {
        shade_100: '#7eefc7',
        shade_200: '#06774f'
      },
      donut: {
        series1: '#2fd862',
        series2: '#4285f4',
        series3: '#fbb900',
        series4: '#2b9bf4',
        series5: '#FFA1A1'
      },
      area: {
        series3: '#fbb900',
        series2: '#4285f4',
        series1: '#2fd862'
      }
    };

  // heat chart data generator
  function generateDataHeat(count, yrange) {
    var i = 0;
    var series = [];
    while (i < count) {
      var x = 'w' + (i + 1).toString();
      var y = Math.floor(Math.random() * (yrange.max - yrange.min + 1)) + yrange.min;

      series.push({
        x: x,
        y: y
      });
      i++;
    }
    return series;
  }

  // Init flatpicker
  if (flatPicker.length) {
    var date = new Date();
    flatPicker.each(function () {
      $(this).flatpickr({
        mode: 'range',
        defaultDate: ['2021-12-01', '2022-12-01']
      });
    });
  }

  // Area Chart
  // --------------------------------------------------------------------
  var areaChartEl = document.querySelector('#line-area-chart'),
    areaChartConfig = {
      chart: {
        height: 400,
        type: 'area',
        parentHeightOffset: 0,
        toolbar: {
          show: false
        }
      },
      dataLabels: {
        enabled: false
      },
      stroke: {
        show: false,
        curve: 'straight'
      },
      legend: {
        show: true,
        position: 'top',
        horizontalAlign: 'start'
      },
      grid: {
        xaxis: {
          lines: {
            show: true
          }
        }
      },
      colors: [chartColors.area.series1, chartColors.area.series2, chartColors.area.series3],
      series: [
        {
          name: 'NAVER',
          data: [100, 120, 90, 170, 130, 160, 140, 240, 220, 180, 270, 280, 375]
        },
        {
          name: 'Google',
          data: [60, 80, 70, 110, 80, 100, 90, 180, 160, 140, 200, 220, 275]
        },
        {
          name: 'Daum',
          data: [20, 40, 30, 70, 40, 60, 50, 140, 120, 100, 140, 180, 220]
        }
      ],
      xaxis: {
        categories: [
          '21/12',
          '22/01',
          '22/02',
          '22/03',
          '22/04',
          '22/05',
          '22/06',
          '22/07',
          '22/08',
          '22/09',
          '22/10',
          '22/11',
          '22/12'
        ]
      },
      fill: {
        opacity: 1,
        type: 'solid'
      },
      tooltip: {
        shared: false
      },
      yaxis: {
        opposite: isRtl
      }
    };
  if (typeof areaChartEl !== undefined && areaChartEl !== null) {
    var areaChart = new ApexCharts(areaChartEl, areaChartConfig);
    areaChart.render();
  }
  
  
  // Scatter Chart
  // --------------------------------------------------------------------
  var scatterChartEl = document.querySelector('#scatter-chart'),
    scatterChartConfig = {
      chart: {
        height: 400,
        type: 'scatter',
        zoom: {
          enabled: true,
          type: 'xy'
        },
        parentHeightOffset: 0,
        toolbar: {
          show: false
        }
      },
      grid: {
        xaxis: {
          lines: {
            show: true
          }
        }
      },
      legend: {
        show: true,
        position: 'top',
        horizontalAlign: 'start'
      },
      colors: [window.colors.solid.warning, window.colors.solid.primary, window.colors.solid.success],
      series: [
        {
          name: 'Angular',
          data: [
            [5.4, 170],
            [5.4, 100],
            [6.3, 170],
            [5.7, 140],
            [5.9, 130],
            [7.0, 150],
            [8.0, 120],
            [9.0, 170],
            [10.0, 190],
            [11.0, 220],
            [12.0, 170],
            [13.0, 230]
          ]
        },
        {
          name: 'Vue',
          data: [
            [14.0, 220],
            [15.0, 280],
            [16.0, 230],
            [18.0, 320],
            [17.5, 280],
            [19.0, 250],
            [20.0, 350],
            [20.5, 320],
            [20.0, 320],
            [19.0, 280],
            [17.0, 280],
            [22.0, 300],
            [18.0, 120]
          ]
        },
        {
          name: 'React',
          data: [
            [14.0, 290],
            [13.0, 190],
            [20.0, 220],
            [21.0, 350],
            [21.5, 290],
            [22.0, 220],
            [23.0, 140],
            [19.0, 400],
            [20.0, 200],
            [22.0, 90],
            [20.0, 120]
          ]
        }
      ],
      xaxis: {
        tickAmount: 10,
        labels: {
          formatter: function (val) {
            return parseFloat(val).toFixed(1);
          }
        }
      },
      yaxis: {
        opposite: isRtl
      }
    };
  if (typeof scatterChartEl !== undefined && scatterChartEl !== null) {
    var scatterChart = new ApexCharts(scatterChartEl, scatterChartConfig);
    scatterChart.render();
  }

  // Line Chart
  // --------------------------------------------------------------------
  var lineChartEl = document.querySelector('#line-chart'),
    lineChartConfig = {
      chart: {
        height: 400,
        type: 'line',
        zoom: {
          enabled: false
        },
        parentHeightOffset: 0,
        toolbar: {
          show: false
        }
      },
      series: [
        {
          data: [280, 200, 220, 180, 270, 250, 70, 90, 200, 150, 160, 100, 150, 100, 50]
        }
      ],
      markers: {
        strokeWidth: 7,
        strokeOpacity: 1,
        strokeColors: [window.colors.solid.white],
        colors: [window.colors.solid.warning]
      },
      dataLabels: {
        enabled: false
      },
      stroke: {
        curve: 'straight'
      },
      colors: [window.colors.solid.warning],
      grid: {
        xaxis: {
          lines: {
            show: true
          }
        },
        padding: {
          top: -20
        }
      },
      tooltip: {
        custom: function (data) {
          return (
            '<div class="px-1 py-50">' +
            '<span>' +
            data.series[data.seriesIndex][data.dataPointIndex] +
            '%</span>' +
            '</div>'
          );
        }
      },
      xaxis: {
        categories: [
          '7/12',
          '8/12',
          '9/12',
          '10/12',
          '11/12',
          '12/12',
          '13/12',
          '14/12',
          '15/12',
          '16/12',
          '17/12',
          '18/12',
          '19/12',
          '20/12',
          '21/12'
        ]
      },
      yaxis: {
        opposite: isRtl
      }
    };
  if (typeof lineChartEl !== undefined && lineChartEl !== null) {
    var lineChart = new ApexCharts(lineChartEl, lineChartConfig);
    lineChart.render();
  }

  // Bar Chart 유입상위
  // --------------------------------------------------------------------
  var barChartEl = document.querySelector('#bar-chart'),
    barChartConfig = {
      chart: {
        height: 400,
        type: 'bar',
        parentHeightOffset: 0,
        toolbar: {
          show: false
        }
      },
      plotOptions: {
        bar: {
          horizontal: true,
          barHeight: '30%',
          endingShape: 'rounded'
        }
      },
      grid: {
        xaxis: {
          lines: {
            show: false
          }
        },
        padding: {
          top: -15,
          bottom: -10
        }
      },
      colors: window.colors.solid.info,
      dataLabels: {
        enabled: false
      },
      series: [
        {
          data: [700, 600, 480, 450, 380, 310, 150]
        }
      ],
      xaxis: {
        categories: ['메인', '베지밀 5060 시니어두유', 'TF1000', '건강맘', '베지밀 담백한 에이', '베지밀 루테인 두유', '영유아식 샘플신청']
      },
      yaxis: {
        opposite: isRtl
      }
    };
  if (typeof barChartEl !== undefined && barChartEl !== null) {
    var barChart = new ApexCharts(barChartEl, barChartConfig);
    barChart.render();
  }


  // Bar Chart-2 이탈상위
  // --------------------------------------------------------------------
  var barChartEl = document.querySelector('#bar-chart-2'),
    barChartConfig = {
      chart: {
        height: 400,
        type: 'bar',
        parentHeightOffset: 0,
        toolbar: {
          show: false
        }
      },
      plotOptions: {
        bar: {
          horizontal: true,
          barHeight: '30%',
          endingShape: 'rounded'
        }
      },
      grid: {
        xaxis: {
          lines: {
            show: false
          }
        },
        padding: {
          top: -15,
          bottom: -10
        }
      },
      colors: window.colors.solid.info,
      dataLabels: {
        enabled: false
      },
      series: [
        {
          data: [700, 600, 480, 450, 380, 310, 150]
        }
      ],
      xaxis: {
        categories: ['메인', '샘플신청', 'TF1000', '건강맘', '베지밀 담백한 에이', '베지밀 루테인 두유', '영유아식 샘플신청']
      },
      yaxis: {
        opposite: isRtl
      }
    };
  if (typeof barChartEl !== undefined && barChartEl !== null) {
    var barChart = new ApexCharts(barChartEl, barChartConfig);
    barChart.render();
  }


  // Candlestick Chart
  // --------------------------------------------------------------------
  var candlestickEl = document.querySelector('#candlestick-chart'),
    candlestickChartConfig = {
      chart: {
        height: 400,
        type: 'candlestick',
        parentHeightOffset: 0,
        toolbar: {
          show: false
        }
      },
      series: [
        {
          data: [
            {
              x: new Date(1538778600000),
              y: [150, 170, 50, 100]
            },
            {
              x: new Date(1538780400000),
              y: [200, 400, 170, 330]
            },
            {
              x: new Date(1538782200000),
              y: [330, 340, 250, 280]
            },
            {
              x: new Date(1538784000000),
              y: [300, 330, 200, 320]
            },
            {
              x: new Date(1538785800000),
              y: [320, 450, 280, 350]
            },
            {
              x: new Date(1538787600000),
              y: [300, 350, 80, 250]
            },
            {
              x: new Date(1538789400000),
              y: [200, 330, 170, 300]
            },
            {
              x: new Date(1538791200000),
              y: [200, 220, 70, 130]
            },
            {
              x: new Date(1538793000000),
              y: [220, 270, 180, 250]
            },
            {
              x: new Date(1538794800000),
              y: [200, 250, 80, 100]
            },
            {
              x: new Date(1538796600000),
              y: [150, 170, 50, 120]
            },
            {
              x: new Date(1538798400000),
              y: [110, 450, 10, 420]
            },
            {
              x: new Date(1538800200000),
              y: [400, 480, 300, 320]
            },
            {
              x: new Date(1538802000000),
              y: [380, 480, 350, 450]
            }
          ]
        }
      ],
      xaxis: {
        type: 'datetime'
      },
      yaxis: {
        tooltip: {
          enabled: true
        },
        opposite: isRtl
      },
      grid: {
        xaxis: {
          lines: {
            show: true
          }
        },
        padding: {
          top: -23
        }
      },
      plotOptions: {
        candlestick: {
          colors: {
            upward: window.colors.solid.success,
            downward: window.colors.solid.danger
          }
        },
        bar: {
          columnWidth: '40%'
        }
      }
    };
  if (typeof candlestickEl !== undefined && candlestickEl !== null) {
    var candlestickChart = new ApexCharts(candlestickEl, candlestickChartConfig);
    candlestickChart.render();
  }

  // Heat map chart
  // --------------------------------------------------------------------
  var heatmapEl = document.querySelector('#heatmap-chart'),
    heatmapChartConfig = {
      chart: {
        height: 350,
        type: 'heatmap',
        parentHeightOffset: 0,
        toolbar: {
          show: false
        }
      },
      plotOptions: {
        heatmap: {
          enableShades: false,

          colorScale: {
            ranges: [
              {
                from: 0,
                to: 10,
                name: '0-10',
                color: '#b9b3f8'
              },
              {
                from: 11,
                to: 20,
                name: '10-20',
                color: '#aba4f6'
              },
              {
                from: 21,
                to: 30,
                name: '20-30',
                color: '#9d95f5'
              },
              {
                from: 31,
                to: 40,
                name: '30-40',
                color: '#8f85f3'
              },
              {
                from: 41,
                to: 50,
                name: '40-50',
                color: '#8176f2'
              },
              {
                from: 51,
                to: 60,
                name: '50-60',
                color: '#7367f0'
              }
            ]
          }
        }
      },
      dataLabels: {
        enabled: false
      },
      legend: {
        show: true,
        position: 'bottom'
      },
      grid: {
        padding: {
          top: -25
        }
      },
      series: [
        {
          name: 'SUN',
          data: generateDataHeat(24, {
            min: 0,
            max: 60
          })
        },
        {
          name: 'MON',
          data: generateDataHeat(24, {
            min: 0,
            max: 60
          })
        },
        {
          name: 'TUE',
          data: generateDataHeat(24, {
            min: 0,
            max: 60
          })
        },
        {
          name: 'WED',
          data: generateDataHeat(24, {
            min: 0,
            max: 60
          })
        },
        {
          name: 'THU',
          data: generateDataHeat(24, {
            min: 0,
            max: 60
          })
        },
        {
          name: 'FRI',
          data: generateDataHeat(24, {
            min: 0,
            max: 60
          })
        },
        {
          name: 'SAT',
          data: generateDataHeat(24, {
            min: 0,
            max: 60
          })
        }
      ],
      xaxis: {
        labels: {
          show: false
        },
        axisBorder: {
          show: false
        },
        axisTicks: {
          show: false
        }
      }
    };
  if (typeof heatmapEl !== undefined && heatmapEl !== null) {
    var heatmapChart = new ApexCharts(heatmapEl, heatmapChartConfig);
    heatmapChart.render();
  }

  // Radialbar Chart
  // --------------------------------------------------------------------
  var radialBarChartEl = document.querySelector('#radialbar-chart'),
    radialBarChartConfig = {
      chart: {
        height: 370,
        type: 'radialBar'
      },
      colors: [chartColors.donut.series1, chartColors.donut.series2, chartColors.donut.series4],
      plotOptions: {
        radialBar: {
          size: 185,
          hollow: {
            size: '25%'
          },
          track: {
            margin: 15
          },
          dataLabels: {
            name: {
              fontSize: '2rem',
              fontFamily: 'Montserrat'
            },
            value: {
              fontSize: '1rem',
              fontFamily: 'Montserrat'
            },
            total: {
              show: true,
              fontSize: '1rem',
              label: 'Comments',
              formatter: function (w) {
                return '80%';
              }
            }
          }
        }
      },
      grid: {
        padding: {
          top: -35,
          bottom: -30
        }
      },
      legend: {
        show: true,
        position: 'bottom'
      },
      stroke: {
        lineCap: 'round'
      },
      series: [80, 50, 35],
      labels: ['Comments', 'Replies', 'Shares']
    };
  if (typeof radialBarChartEl !== undefined && radialBarChartEl !== null) {
    var radialChart = new ApexCharts(radialBarChartEl, radialBarChartConfig);
    radialChart.render();
  }


});

var flatPicker = $('.flat-picker'),
    isRtl = $('html').attr('data-textdirection') === 'rtl',
    chartColors = {
      column: {
        series1: '#ff386c',
        series2: '#4285f4',
	    series3: '#fbb900',
        bg: '#cbcbcb'
      },
      success: {
        shade_100: '#7eefc7',
        shade_200: '#06774f'
      },
      donut: {
        series1: '#2fd862',
        series2: '#4285f4',
        series3: '#fbb900',
        series4: '#2b9bf4',
        series5: '#FFA1A1'
      },
      area: {
        series3: '#fbb900',
        series2: '#4285f4',
        series1: '#2fd862'
      }
    };

// 남녀 성비
function radarChart(manCount, womenCount) {
  var radarChartEl = document.querySelector('#radar-chart'),
    radarChartConfig = {
      chart: {
        height: 425,
        type: 'radar',
        toolbar: {
          show: false
        },
        parentHeightOffset: 0,
        dropShadow: {
          enabled: false,
          blur: 8,
          left: 1,
          top: 1,
          opacity: 0.2
        }
      },
      legend: {
        show: true,
        position: 'bottom'
      },
      yaxis: {
        show: false
      },
      series: [
        {
          name: '남자',
          data: manCount	//실데이터 영역
        },
        {
          name: '여자',
          data: womenCount	//실데이터 영역
        }
      ],
      colors: [chartColors.donut.series1, chartColors.donut.series3],
      xaxis: {
        categories: ['10대', '20대', '30대', '40대', '50대', '60대', '70대 이상']
      },
      fill: {
        opacity: [1, 0.8]
      },
      stroke: {
        show: false,
        width: 0
      },
      markers: {
        size: 0
      },
      grid: {
        show: false,
        padding: {
          top: -20,
          bottom: -20
        }
      }
    };
  if (typeof radarChartEl !== undefined && radarChartEl !== null) {
    var radarChart = new ApexCharts(radarChartEl, radarChartConfig);
    radarChart.render();
  }
}

//모바일 기기 월유입
function columnChart(iosList,androidList, etcList, yymmList){

  var columnChartEl = document.querySelector('#column-chart'),
    columnChartConfig = {
      chart: {
        height: 400,
        type: 'bar',
        stacked: true,
        parentHeightOffset: 0,
        toolbar: {
          show: false
        }
      },
      plotOptions: {
        bar: {
          columnWidth: '15%',
          colors: {
            backgroundBarColors: [
              chartColors.column.bg,
              chartColors.column.bg,
              chartColors.column.bg,
              chartColors.column.bg,
              chartColors.column.bg
            ],
            backgroundBarRadius: 8
          }
        }
      },
      dataLabels: {
        enabled: false
      },
      legend: {
        show: true,
        position: 'top',
        horizontalAlign: 'start'
      },
      colors: [chartColors.column.series1, chartColors.column.series2, chartColors.column.series3],
      stroke: {
        show: true,
        colors: ['transparent']
      },
      grid: {
        xaxis: {
          lines: {
            show: true
          }
        }
      },
      series: [
        {
          name: 'ios',
          data: iosList	// 실데이터
        },
        {
          name: 'android',
          data: androidList	// 실데이터
        },
		{
          name: 'etc',
          data: etcList	// 실데이터
        }
		 
      ],
      xaxis: {
        categories: yymmList	// 실데이터
      },
      fill: {
        opacity: 1
      },
      yaxis: {
        opposite: isRtl
      }
    };
  if (typeof columnChartEl !== undefined && columnChartEl !== null) {
    var columnChart = new ApexCharts(columnChartEl, columnChartConfig);
    columnChart.render();
  }
}

// 디바이스별 유입
function donutChart(pc, mobile, tablet) {
	var total = pc + mobile + tablet;
	var pcRatio = Math.round(pc/total*100);
	var mobileRatio = Math.round(mobile/total*100);
	var tabletRatio = Math.round(tablet/total*100);
	var maxDevice = "PC";
	var maxRatio = pcRatio;
	var temp = 0;
	if(maxRatio < mobileRatio) {
		maxRatio = mobileRatio;
		maxDevice = "Mobile";
		if(maxRatio < tabletRatio ) {
			maxRatio = tabletRatio;
			maxDevice = "Tablet";
		}
	} else if(maxRatio < tabletRatio ) {
		maxRatio = tabletRatio;
		maxDevice = "Tablet";
	}

  var donutChartEl = document.querySelector('#donut-chart'),
    donutChartConfig = {
      chart: {
        height: 410,
        type: 'donut'
      },
      legend: {
        show: true,
        position: 'bottom'
      },
      labels: [ 'PC',' Mobile', 'Tablet' ],
      series: [pcRatio, mobileRatio, tabletRatio],	//실데이터 영역
      colors: [
        chartColors.donut.series1,
        chartColors.donut.series2,
        chartColors.donut.series3,
      ],
      dataLabels: {
        enabled: true,
        formatter: function (val, opt) {
          return parseInt(val) + '%';
        }
      },
      plotOptions: {
        pie: {
          donut: {
            labels: {
              show: true,
              name: {
                fontSize: '2rem',
                fontFamily: 'Mobile'
              },
              value: {
                fontSize: '1rem',
                fontFamily: 'PC',
                formatter: function (val) {
                  return parseInt(val) + '%';
                }
              },
              total: {
                show: true,
                fontSize: '1.5rem',
                label: maxDevice,
                formatter: function (w) {
                  return maxRatio+'%';
                }
              }
            }
          }
        }
      },
      responsive: [
        {
          breakpoint: 992,
          options: {
            chart: {
              height: 380
            }
          }
        },
        {
          breakpoint: 576,
          options: {
            chart: {
              height: 320
            },
            plotOptions: {
              pie: {
                donut: {
                  labels: {
                    show: true,
                    name: {
                      fontSize: '1.5rem'
                    },
                    value: {
                      fontSize: '1rem'
                    },
                    total: {
                      fontSize: '1.5rem'
                    }
                  }
                }
              }
            }
          }
        }
      ]
    };
  if (typeof donutChartEl !== undefined && donutChartEl !== null) {
    var donutChart = new ApexCharts(donutChartEl, donutChartConfig);
    donutChart.render();
  }
}