function dummyOperation(values) {
    return values;
}

function accumulatedDistribution(values) {
    var counts = valuesCount(values);

    var probabilities = [];
    var previousProbability = 0;
    for (var i = 0; i < counts.length; i++) {
        var probability = counts[i] / values.length + previousProbability;
        probabilities.push(probability);
        previousProbability = probability;
    }

    return probabilities;
}

function complementaryAccumulatedDistribution(values) {
    var counts = valuesCount(values);

    var probabilities = [];
    var previousProbability = 1;
    for (var i = 0; i < counts.length; i++) {
        var probability = previousProbability - counts[i] / values.length;
        probabilities.push(probability >= 0 ? probability : 0);
        previousProbability = probability;
    }

    return probabilities;
}

function probabilityDensity(values) {
    var counts = valuesCount(values);

    var probabilities = [];
    for (var i = 0; i < counts.length; i++) {
        probabilities.push(counts[i] / values.length);
    }

    return probabilities;
}

function maxLabels(values) {
    return range(1, Math.max.apply(null, values));
}

function lengthLabels(values) {
    return range(1, values.length)
}
