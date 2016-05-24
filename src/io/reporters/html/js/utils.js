
function range(start, end) {
    var array = [];
    for (var i = start; i <= end; i++) {
        array.push(i);
    }

    return array;
}

function arrayOfZeros(n) {
    var array = [];
    for (var i = 0; i < n; i++) {
        array.push(0);
    }

    return array;
}

function avg(values) {
    var sum = 0;
    for (var i = 0; i < values.length; i++) {
        sum += values[i];
    }

    return sum / values.length;
}

function valuesCount(values) {
    var valuesCount = arrayOfZeros(Math.max.apply(null, values));

    for (var i = 0; i < values.length; i++) {
        valuesCount[values[i] - 1]++;
    }

    return valuesCount;
}

function clearChart(chart) {
    if (chart != null) {
        chart.destroy();
    }
}

function clearBasicStats() {
    $("#messageCountMinimum").text("");
    $("#messageCountAverage").text("");
    $("#messageCountMaximum").text("");
}

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
