const width = 700;
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

// fontsize
const fontSize = 10

// Variable with the turn
var turn = 0;

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

// Plots the game using d3.js
function plotGame(gameJson) {

    $("#turn").text(0)
    globalGameJson = gameJson;

    const nTurns = gameJson.length;

    // Change the slider maximum value
    $("#turnSlider").attr("max", nTurns-1);

    /* Remove th svg after a game file is selected */
    d3.select("#svgViz").remove();
    createSVG();

    let turnData = gameJson[0];
    /* Zombie location */

    createPos(turnData, "zombies", zombiePos, zombieRange);
    createPos(turnData, "humans", humanPos, 0);
    createPos(turnData, "Ash", ashPos, ashRange);
}


// Plots the position specific for a given turn
function plotTurn(turn) {

    let turnData = globalGameJson[turn]
    printStatus(turnData)

    // Display which turn it is
    $("#turn").text(turn)


    /* Zombie location */
    updatePos(turn, turnData, "zombies", zombiePos, 400);

    /* Ash location */
    updatePos(turn, turnData, "Ash", ashPos, 2000);

    /* Human location */
    updatePos(turn, turnData, "humans", humanPos, 0);

}

// Print the status of every element on the screen
function printStatus(turnData){
    // console.log("Turn: ", turn);
    // console.log(turnData);
    // console.log(turnData);

    const reducer = (acc, item) => {return acc + item};

    // Empty the previous logs
    $("#status-log").empty();

    // Print Ash status
    let ashStats = turnData["Ash"][0];
    let ashString = Object.keys(ashStats).map(d => {return d + ": " + ashStats[d] + " | "}).reduce(reducer, "");
    $("#status-log").append("<p> Ash: " + ashString + "<p>");

    let i;
    for (i=0; i<turnData["zombies"].length; i++){
        let zombieStats = turnData["zombies"][i];
        let zombieString = Object.keys(zombieStats).map(d => {return d + ": " + zombieStats[d] + " | "}).reduce(reducer, "");
        $("#status-log").append("<p> Zombie" + i + ": " + zombieString + "<p>");
    }
}

