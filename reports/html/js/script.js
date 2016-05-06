// data provided by the simulator -- DO NOT EDIT --
var labels = [];
var messageCounts = [];
var detectionCounts = [];
// -- END OF DATA --

// -- Collection of colors for the charts --

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

// ----- BEGIN MAIN -------------

// message count histogram

var messageCountChart = loadMessageCountHistogram('bar');

$("#barMessageHistogramRadioButton").click(function () {
    messageCountChart.destroy();
    messageCountChart = loadMessageCountHistogram('bar');
});

$("#lineMessageHistogramRadioButton").click(function () {
    messageCountChart.destroy();
    messageCountChart = loadMessageCountHistogram('line');
});

$("#messageCountMinimum").append(Math.min.apply(null, messageCounts));
$("#messageCountAverage").append(avg(messageCounts));
$("#messageCountMaximum").append(Math.max.apply(null, messageCounts));

// detection count histogram

var detectionCountChart = loadDetectionCountHistogram('bar');

$("#detectionCountMinimum").append(Math.min.apply(null, detectionCounts));
$("#detectionCountAverage").append(avg(detectionCounts));
$("#detectionCountMaximum").append(Math.max.apply(null, detectionCounts));

$("#barDetectionHistogramRadioButton").click(function () {
    detectionCountChart.destroy();
    detectionCountChart = loadDetectionCountHistogram('bar');
});

$("#lineDetectionHistogramRadioButton").click(function () {
    detectionCountChart.destroy();
    detectionCountChart = loadDetectionCountHistogram('line');
});

// ----- END MAIN ---------------

// -- Loading charts --

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

function loadMessageCountHistogram(type) {
    return loadChart($("#messageCountChart"), "message count", labels, messageCounts, type, RED);
}

function loadDetectionCountHistogram(type) {
    return loadChart($("#detectionCountChart"), "detection count", labels, detectionCounts, type, BLUE);
}

// -- Util functions --

/**
 * Computes average of the values in an array.
 *
 * @param values array of values.
 * @returns {number} average of the values.
 */
function avg(values) {
    var sum = 0;
    for (var i = 0; i < values.length; i++) {
        sum += values[i];
    }

    return sum / values.length;
}


