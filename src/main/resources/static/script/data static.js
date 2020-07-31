var ctx = document.getElementById('myChart').getContext('2d');
var chart = new Chart(ctx, {
    // The type of chart we want to create
    type: 'line',
    color: 'red',
    borderWidth:30,
    // The data for our dataset
    data: {
        labels: ['21.08.2018','22.08.2018','23.08.2018', '21.08.2018','22.08.2018','23.08.2018', '21.08.2018','22.08.2018','23.08.2018'],
        datasets: [{
            label: 'My First dataset',
            borderColor: '#00CED1',
            data: [63.56, 64.98, 73, 63.56, 64.98, 73, 63.56, 64.98, 73]
        }]
    },

    // Configuration options go here
    options: { scales: {
        gridLines: {
            show: true,
            color: "red",
        },
    }
}

});