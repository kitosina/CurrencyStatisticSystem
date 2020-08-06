var app = angular.module("DATE_STATISTICS",[]);

app.controller("DATE_CONTROLLER",function($scope, $http){
    graphics();
//   /range/{dateAfter}/{dateBefore}/{quotedTitleCurrency}/{basicTitleCurrency}
    $scope.url = "/currency/range/";
    $scope.paramRequest = {
        dateAfter: "",
        dateBefore: "",
        currencyCouple: "",
    }

    $scope.analysisGlobalData = function () {
        console.log('call');
        if (($scope.paramRequest.dateAfter !== "") && ($scope.paramRequest.dateBefore !== "") && ($scope.paramRequest.currencyCouple !== "")) {
            var updateDateAfter = validateDate($scope.paramRequest.dateAfter);
            var updateDateBefore = validateDate($scope.paramRequest.dateBefore)
            // console.log(updateDateAfter);
            var urlRequest = $scope.url + updateDateAfter + "/" + updateDateBefore + "/" + $scope.paramRequest.currencyCouple;
            $http({
                url: urlRequest,
                method: "GET"
            }).then(function (response) {
                console.log(response.data);
                this.graphics(response.data);

            })
        } else alert("Please input currency!");
    }

});

function validateDate(date) {
   var year = date.getFullYear();

    var month = null;
    if (date.getMonth() + 1 > 9) {
        month = date.getMonth() + 1
    } else {
        month = '0' + `${date.getMonth() + 1}`
    }

    var day = null;
    if (date.getDate() > 9) {
        day = date.getDate()
    } else {
        day = '0' + date.getDate()
    }

   return  `${year}-${month}-${day}`
}

function graphics(data) {
    // var chart = am4core.create(
    //     document.getElementById("chartdiv"),
    //     am4charts.XYChart
    // );

    // var chartData = []
    // var labels = []
    // var values = []
    // if (data === undefined) {
    //     values = [63.56, 64.98, 73, 63.56, 64.98, 73, 63.56, 64.98, 73]
    //     labels = ['21.08.2018', '22.08.2018', '23.08.2018', '21.08.2018', '22.08.2018', '23.08.2018', '21.08.2018', '22.08.2018', '23.08.2018']
    //     for (var i = 0; i < values.length; i++) {
    //         chartData.push({
    //             date: labels[i],
    //             value: values[i]
    //         })
    //     }
    // } else {
    //     for (var i = 0; i < data.length; i++) {
    //         labels.push(data[i].date.slice(0, 10))
    //         values.push(data[i].value)
    //
    //         chartData.push({
    //             date: data[i].date.slice(0, 10),
    //             value: data[i].value
    //         })
    //     }
    // }
    // chart.data = chartData
    // chart.data = [{
    //     "country": "Lithuania",
    //     "litres": 501
    // }, {
    //     "country": "Czechia",
    //     "litres": 301
    // }, {
    //     "country": "Ireland",
    //     "litres": 201
    // }, {
    //     "country": "Germany",
    //     "litres": 165
    // }, {
    //     "country": "Australia",
    //     "litres": 139
    // }, {
    //     "country": "Austria",
    //     "litres": 128
    // }, {
    //     "country": "UK",
    //     "litres": 99
    // }, {
    //     "country": "Belgium",
    //     "litres": 60
    // }, {
    //     "country": "The Netherlands",
    //     "litres": 50
    // }];
    // var categoryAxis = chart.xAxes.push(new am4charts.CategoryAxis());
    // categoryAxis.dataFields.category = "country";
    // var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
    // var categoryAxis = chart.xAxes.push(new am4charts.CategoryAxis());
    // categoryAxis.dataFields.category = "country";
    // categoryAxis.title.text = "Countries";
    //
    // var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
    // valueAxis.title.text = "Litres sold (M)";
    //
    // var series = chart.series.push(new am4charts.ColumnSeries());
    // series.name = "Sales";
    // series.columns.template.tooltipText = "Series: {name}\nCategory: {categoryX}\nValue: {valueY}";
    // series.columns.template.fill = am4core.color("#104547"); // fill
    // series.dataFields.valueY = "litres";
    // series.dataFields.categoryX = "country";

    // var dateAxis = chart.xAxes.push(new am4charts.DateAxis());
    // var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
    // var series = chart.series.push(new am4charts.LineSeries());
    // series.dataFields.dateX = "date";
    // series.dataFields.valueY = "value";
    // chart.cursor = new am4charts.XYCursor();
    //
    // var scrollbarX = new am4charts.XYChartScrollbar();
    // scrollbarX.series.push(series);
    // chart.scrollbarX = scrollbarX;


    // var ctx = document.getElementById('myChart').getContext('2d');
    // var chart = new Chart(ctx, {
    //     // The type of chart we want to create
    //     type: 'line',
    //     color: 'red',
    //     borderWidth: 30,
    //     // The data for our dataset
    //     data: {
    //         labels: labels,
    //         datasets: [{
    //             label: 'My First dataset',
    //             borderColor: '#00CED1',
    //             data: values
    //         }]
    //     },
    //
    //     // Configuration options go here
    //     options: {
    //         display: true,
    //         scales: {
    //             gridLines: {
    //                 show: true,
    //                 color: "red",
    //             },
    //         }
    //     }

    // });



    // Use themes
    am4core.useTheme(am4themes_animated);

// Create chart instance
    var chart = am4core.create("chartdiv", am4charts.XYChart);
    var colorSet = new am4core.ColorSet("red");
    chart.paddingRight = 20;

// Add data
    chart.data = [{
        "date": new Date(2018, 3, 20),
        "value": 90,
    }, {
        "date": new Date(2018, 3, 21),
        "value": 102,
    }, {
        "date": new Date(2018, 3, 22),
        "value": 123,
    }, {
        "date": new Date(2018, 3, 23),
        "value": 190,
    }, {
        "date": new Date(2018, 3, 24),
        "value": 55,
    }, {
        "date": new Date(2018, 3, 25),
        "value": 81,
    }, {
        "date": new Date(2018, 3, 26),
        "value": 123,
    }, {
        "date": new Date(2018, 3, 27),
        "value": 63,
    }, {
        "date": new Date(2018, 3, 28),
        "value": 113,
    }, {
        "date": new Date(2018, 3, 29),
        "value": 113,
    }, {
        "date": new Date(2018, 3, 30),
        "value": 113,
    }, {
        "date": new Date(2018, 3, 31),
        "value": 113,
    }, {
        "date": new Date(2018, 4, 1),
        "value": 113,
    }, {
        "date": new Date(2018, 4, 2),
        "value": 113,
    }, {
        "date": new Date(2018, 4, 3),
        "value": 113,
    }, {
        "date": new Date(2018, 4, 4),
        "value": 113,
    }];

// Create axes
    var dateAxis = chart.xAxes.push(new am4charts.DateAxis());
    dateAxis.renderer.minGridDistance = 50;
    dateAxis.renderer.grid.template.location = 0.5;
    dateAxis.startLocation = 0.5;
    dateAxis.endLocation = 0.5;

// Create value axis
    var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());

// Create series
    var series1 = chart.series.push(new am4charts.LineSeries());
    series1.dataFields.valueY = "value";
    series1.dataFields.dateX = "date";
    series1.strokeWidth = 3;
    series1.tensionX = 0.8;
    series1.bullets.push(new am4charts.CircleBullet());
    series1.connect = false;
    series1.fill = am4core.color("green");
    series1.stroke = am4core.color("red");

    var scrollbarX = new am4charts.XYChartScrollbar();
    scrollbarX.series.push(series1);
    chart.scrollbarX = scrollbarX;
}


