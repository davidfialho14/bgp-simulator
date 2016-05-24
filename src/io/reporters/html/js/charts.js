const RED = {
    background: "rgba(255,99,132,0.2)",
    border: "rgba(255,99,132,1)",
    hoverBackground: "rgba(255,99,132,0.4)",
    hoverBorder: "rgba(255,99,132,1)"
};

const BLUE = {
    background: "rgba(75,192,192,0.2)",
    border: "rgba(75,192,192,1)",
    hoverBackground: "rgba(75,192,192,0.4)",
    hoverBorder: "rgba(75,192,192,1)"
};

function loadChart(canvas, label, labels, data, type, color) {

    return new Chart(canvas, {
        type: type,
        data: {
            labels: labels,
            datasets: [
                {
                    label: label,
                    backgroundColor: color["background"],
                    borderColor: color["border"],
                    borderWidth: 1,
                    hoverBackgroundColor: color["hoverBackground"],
                    hoverBorderColor: color["hoverBorder"],
                    data: data
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true,
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero:true
                    }
                }]
            }
        }
    });
}

function avg(values) {
    var sum = 0;
    for (var i = 0; i < values.length; i++) {
        sum += values[i];
    }

    return sum / values.length;
}
