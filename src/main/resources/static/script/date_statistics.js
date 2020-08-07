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

// Themes begin
        am4core.useTheme(am4themes_animated);
// Themes end

// Create chart instance
        var chart = am4core.create("chartdiv", am4charts.XYChart);

// Add data
        chart.data = [];


// Set input format for the dates
        chart.dateFormatter.inputDateFormat = "yyyy-MM-dd";

        var labels = []
        var values = []
        if (data === undefined) {
            values = [63.56, 64.98, 73, 63.56, 64.98, 73, 63.56, 64.98, 73]
            labels = ['21.08.2018', '22.08.2018', '23.08.2018', '21.08.2018', '22.08.2018', '23.08.2018', '21.08.2018', '22.08.2018', '23.08.2018']
            for (var i = 0; i < values.length; i++) {
                chart.data.push({
                    date: labels[i],
                    value: values[i]
                })
            }
        } else {
            for (var i = 0; i < data.length; i++) {
                chart.data.push(data[i].date.slice(0, 10))
                chart.data.push(data[i].value)

                chart.data.push({
                    date: data[i].date.slice(0, 10),
                    value: data[i].value
                })
            }
        }
        console.log("data full" +  chart.data.sort());
// Create axes
        var dateAxis = chart.xAxes.push(new am4charts.DateAxis());
        var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());

// Create series
        var series = chart.series.push(new am4charts.LineSeries());
        series.dataFields.valueY = "value";
        series.dataFields.dateX = "date";
        series.tooltipText = "{value}"
        series.strokeWidth = 2;
        series.minBulletDistance = 15;

// Drop-shaped tooltips
        series.tooltip.background.cornerRadius = 20;
        series.tooltip.background.strokeOpacity = 0;
        series.tooltip.pointerOrientation = "vertical";
        series.tooltip.label.minWidth = 40;
        series.tooltip.label.minHeight = 40;
        series.tooltip.label.textAlign = "middle";
        series.tooltip.label.textValign = "middle";

// Make bullets grow on hover
        var bullet = series.bullets.push(new am4charts.CircleBullet());
        bullet.circle.strokeWidth = 2;
        bullet.circle.radius = 4;
        bullet.circle.fill = am4core.color("#fff");

        var bullethover = bullet.states.create("hover");
        bullethover.properties.scale = 1.3;

// Make a panning cursor
        chart.cursor = new am4charts.XYCursor();
        chart.cursor.behavior = "panXY";
        chart.cursor.xAxis = dateAxis;
        chart.cursor.snapToSeries = series;

// Create vertical scrollbar and place it before the value axis
        chart.scrollbarY = new am4core.Scrollbar();
        chart.scrollbarY.parent = chart.leftAxesContainer;
        chart.scrollbarY.toBack();

// Create a horizontal scrollbar with previe and place it underneath the date axis
        chart.scrollbarX = new am4charts.XYChartScrollbar();
        chart.scrollbarX.series.push(series);
        chart.scrollbarX.parent = chart.bottomAxesContainer;

        dateAxis.start = 0.79;
        dateAxis.keepSelection = true;
}


