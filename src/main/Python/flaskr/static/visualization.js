const width = 500;
const height = 500;

// Define the colors
const radius = 10;
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
var turn = 0

// function that outputs 0 if the target is dead, else the radious
function deadRadius(isDead){if (isDead==1) return 0; else return radius}

// Run the create SVG function when the page fully loads
function createSVG() {
    svg = d3.select("#game-viz").append("svg")
        .attr("width", width)
        .attr("id", "svgViz")
        .attr("height", height)

    svg.append("rect")
        .attr("x", 0)
        .attr("y", 0)
        .attr("width", width)
        .attr("height", height)
        .attr("fill", "none")
        .attr('stroke', 'black')
    ;
}

// Gets the filename from the server and calls plotGame
function getFile(fileName) {
    $.post("/get_game_file", {fileName: fileName}).done(function (json) {
        plotGame(json)
    })
}

// Creates the d3 svg objects and the svg element
function createPos(turnData, selectName, fill){

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
}


// Plots the game using d3.js
function plotGame(gameJson) {

    /* Remove th svg after a game file is selected */
    d3.select("#svgViz").remove();
    createSVG();

    let svg = d3.select("#svgViz");

    let turnData = gameJson[0];
    /* Zombie location */

    createPos(turnData, "zombies", zombiePos)
    createPos(turnData, "humans", humanPos)
    createPos(turnData, "Ash", ashPos)

    turn = 1;
    while (turn < gameJson.length) {
        console.log("\nTurn: ", turn);
        console.log(gameJson[turn]);
        plotTurn(turn, gameJson[turn], svg);
        turn++
    }
}


function updatePos(turn, turnData, char, fill, attackRange){
    char.transition().delay(duration*turn).duration(duration)
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
        .attr("r", function (d) {return deadRadius(d["isDead"])})
}

// Plots the position specific for a given turn
function plotTurn(turn, turnData, svg) {

    /* Zombie location */
    let zombies = svg.selectAll(".zombies").data(turnData["zombies"])
    updatePos(turn, turnData, zombies, zombiePos, zombieRange)

    /* Human location */
    let humans = svg.selectAll(".human").data(turnData["humans"])
    updatePos(turn, turnData, humans, humanPos, 0)

    /* Ash location */
    let ash = svg.selectAll(".Ash").data(turnData["Ash"])
    updatePos(turn, turnData, ash, ashPos, ashRange)
}



