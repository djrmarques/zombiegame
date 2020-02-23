const width = 500;
const height = 500;

// Store game Json
var globalGameJson;

// Define the colors
const radius = 10;
const lineStroke = 2;
const ashPos = "blue";
const ashTarget = "green";
const zombiePos = "red";
const zombieTarget = "red";
const humanPos = "yellow";

const ashRange = 2000;
const zombieRange = 400;

// Transition duration
const duration = 500;

// Define the scales
const x = d3.scaleLinear().domain([0, 16000]).range([radius * 2, width]);
const y = d3.scaleLinear().domain([0, 9000]).range([height, radius * 2]);

// Variable with the turn
var turn = 0;

// function that outputs 0 if the target is dead, else the radius
function deadRadius(isDead) {
    if (isDead === 1) return 0; else return radius
}

// function that outputs 0 if the the character is dead
function deadStroke(isDead) {
    if (isDead === 1) return 0; else return lineStroke
}


// Run the create SVG function when the page fully loads
function createSVG() {
    let svg = d3.select("#game-viz").append("svg")
        .attr("width", width)
        .attr("id", "svgViz")
        .attr("height", height);

    svg.append("rect")
        .attr("x", 0)
        .attr("y", 0)
        .attr("width", width)
        .attr("height", height)
        .attr("fill", "none")
        .attr('stroke', 'black');
}

// Gets the filename from the server and calls plotGame
function getFile(fileName) {
    $.post("/get_game_file", {fileName: fileName}).done(function (json) {
        plotGame(json)
    })
}

// Creates the d3 svg objects and the svg element
function createPos(turnData, selectName, fill, attackRange) {

    let svg = d3.select("#svgViz");

    // /* Draw the position */
    svg.selectAll(selectName)
        .data(turnData[selectName])
        .enter()
        .append("circle")
        .attr("cx", function (d) {
            return x(d["posX"]);
        })
        .attr("cy", function (d) {
            return y(d["posY"]);
        })
        .attr("id", function (d) {
            return d["id"];
        })
        .attr("fill", fill)
        .attr("class", selectName)
        .attr("r", radius)

    if (selectName == "zombies" || selectName == "Ash") {
        svg.selectAll(selectName + "Line")
            .data(turnData[selectName])
            .enter()
            .append("line")
            .attr("x1", function (d) {
                return x(d["posX"]);
            })
            .attr("x2", function (d) {
                return x(d["targetX"]);
            })
            .attr("y1", function (d) {
                return y(d["posY"]);
            })
            .attr("y2", function (d) {
                return y(d["targetY"]);
            })
            .attr("fill", "none")
            .attr("stroke", fill)
            .attr("opacity", "0.5")
            .attr("stroke-width", lineStroke)
            .attr("class", selectName + "Line")
    }
}


// Plots the game using d3.js
function plotGame(gameJson) {

    $("#turn").text(0)
    globalGameJson = gameJson;

    const nTurns = gameJson.length;

    // Change the slider maximum value
    $("#turnSlider").attr("max", nTurns);

    /* Remove th svg after a game file is selected */
    d3.select("#svgViz").remove();
    createSVG();

    let svg = d3.select("#svgViz");

    let turnData = gameJson[0];
    /* Zombie location */

    createPos(turnData, "zombies", zombiePos, zombieRange);
    createPos(turnData, "humans", humanPos, 0);
    createPos(turnData, "Ash", ashPos, ashRange);
}

function updatePos(turn, turnData, selectName, fill) {

    let svg = d3.select("#svgViz");

    let char = svg.selectAll("." + selectName).data(turnData[selectName]);
    char.transition().duration(duration)
        .attr("cx", function (d) {
            return x(d["posX"]);
        })
        .attr("cy", function (d) {
            return y(d["posY"]);
        })
        .attr("r", function (d) {
            return deadRadius(d["isDead"])
        })


    if (selectName == "zombies" || selectName == "Ash") {
        svg.selectAll(selectName + "Line")
            .data(turnData[selectName])
            .transition().duration(duration)
            .attr("x1", function (d) {
                return x(d["posX"]);
            })
            .attr("x2", function (d) {
                return x(d["targetX"]);
            })
            .attr("y1", function (d) {
                return y(d["posY"]);
            })
            .attr("y2", function (d) {
                return y(d["targetY"]);
            })
            .attr("stroke-width", 1.5)
    }
}

// Plots the position specific for a given turn
    function plotTurn(turn) {

        let turnData = globalGameJson[turn]
        console.log("Turn: ", turn)
        console.log(turnData)

        $("#turn").text(turn)


        /* Zombie location */
        // let zombies = svg.selectAll(".zombies").data(turnData["zombies"]);
        updatePos(turn, turnData, "zombies", zombiePos);

        /* Ash location */
        // let ash = svg.selectAll(".Ash").data(turnData["Ash"]);
        updatePos(turn, turnData, "Ash", ashPos);

        /* Human location */
        // let humans = svg.selectAll(".humans").data(turnData["humans"]);
        updatePos(turn, turnData, "humans", humanPos);

    }



