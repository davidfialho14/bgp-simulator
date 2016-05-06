// data provided by the simulator -- DO NOT EDIT --
var labels = [];
var messageCounts = [];
var detectionCounts = [];
const SAMPLE_COUNT = labels.length;
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

function countForEach(values) {
    var messageCountCumulativeData = arrayOfZeros(Math.max.apply(null, values));

    for (var i = 0; i < values.length; i++) {
        messageCountCumulativeData[values[i] - 1]++;
    }

    return messageCountCumulativeData;
}

function accumulatedDistribution(values) {
    var counts = countForEach(values);

    var probabilities = [];
    var previousProbability = 0;
    for (var i = 0; i < counts.length; i++) {
        var probability = counts[i] / SAMPLE_COUNT + previousProbability;
        probabilities.push(probability);
        previousProbability = probability;
    }

    return probabilities;
}

function complementaryAccumulatedDistribution(values) {
    var counts = countForEach(values);

    var probabilities = [];
    var previousProbability = 1;
    for (var i = 0; i < counts.length; i++) {
        var probability = previousProbability - counts[i] / SAMPLE_COUNT;
        probabilities.push(probability >= 0 ? probability : 0);
        previousProbability = probability;
    }

    console.log(probabilities);

    return probabilities;
}

function probabilityDensity(values) {
    var counts = countForEach(values);

    var probabilities = [];
    for (var i = 0; i < counts.length; i++) {
        probabilities.push(counts[i] / SAMPLE_COUNT);
    }

    console.log(probabilities);

    return probabilities;
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

// ----- BEGIN MAIN -------------

// message count cumulative function

var messageCountCumulativeLabels = range(1, Math.max.apply(null, messageCounts));
console.log(messageCountCumulativeLabels);

// message count histogram

var messageCountChart = null;

var messageHistogramRadioButton = $("#messageHistogramRadioButton");

messageHistogramRadioButton.click(function () {
    clearChart(messageCountChart);
    messageCountChart = loadChart($("#messageCountChart"), "Message Count Histogram",
        labels, messageCounts, 'bar', RED);

    $("#messageCountMinimum").text("Minimum: " + (Math.min.apply(null, messageCounts)));
    $("#messageCountAverage").text("Average: " + avg(messageCounts));
    $("#messageCountMaximum").text("Maximum: " + Math.max.apply(null, messageCounts));
});

$("#messageCumulativeRadioButton").click(function () {
    clearChart(messageCountChart);

    var labels = range(1, Math.max.apply(null, messageCounts));

    messageCountChart = loadChart($("#messageCountChart"), "Message Count Cumulative Distribution",
        labels, accumulatedDistribution(messageCounts), 'line', RED);

    clearBasicStats();
});

$("#messageComplementaryCumulativeRadioButton").click(function () {
    clearChart(messageCountChart);

    var labels = range(1, Math.max.apply(null, messageCounts));

    messageCountChart = loadChart($("#messageCountChart"), "Message Count Complementary Cumulative Distribution",
        labels, complementaryAccumulatedDistribution(messageCounts), 'line', RED);

    clearBasicStats();
});

$("#messageProbabilityDensityRadioButton").click(function () {
    clearChart(messageCountChart);

    var labels = range(1, Math.max.apply(null, messageCounts));

    messageCountChart = loadChart($("#messageCountChart"), "Message Count Probability Density",
        labels, probabilityDensity(messageCounts), 'line', RED);

    clearBasicStats();
});

messageHistogramRadioButton.click();

// detection count histogram

var detectionCountChart = null;

$("#detectionCountMinimum").text("Minimum: " + (Math.min.apply(null, detectionCounts)));
$("#detectionCountAverage").text("Average: " + avg(detectionCounts));
$("#detectionCountMaximum").text("Maximum: " + Math.max.apply(null, detectionCounts));

var barDetectionHistogramRadioButton = $("#barDetectionHistogramRadioButton");

barDetectionHistogramRadioButton.click(function () {
    clearChart(detectionCountChart);
    detectionCountChart = loadChart($("#detectionCountChart"), "Detection Count Histogram",
        labels, detectionCounts, 'bar', BLUE);
});

$("#lineDetectionHistogramRadioButton").click(function () {
    clearChart(detectionCountChart);
    detectionCountChart = loadChart($("#detectionCountChart"), "Detection Count Histogram",
        labels, detectionCounts, 'line', BLUE);
});

barDetectionHistogramRadioButton.click();

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

/**
 * Creates an array with a range of items from the start to end value.
 *
 * @param start
 * @param end
 * @returns {Array}
 */
function range(start, end) {
    var array = [];
    for (var i = start; i <= end; i++) {
        array.push(i);
    }

    return array;
}

/**
 * Creates an array n zeros.
 *
 * @param n number of zeros in the array.
 * @returns {Array}
 */
function arrayOfZeros(n) {
    var array = [];
    for (var i = 0; i < n; i++) {
        array.push(0);
    }

    return array;
}
