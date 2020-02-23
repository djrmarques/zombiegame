const width = 500;
const height = 500;

// Define the colors
const radius = 10
const ashPos = "blue"
const ashTarget = "green"
const zombiePos = "red"
const zombieTarget = "ref"
const humanPos = "yellow"

// Transition duration
const duration = 500

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
};


// Plots the game using d3.js
function plotGame(gameJson) {

    /* Remove th svg after a game file is selected */
    d3.select("#svgViz").remove()
    createSVG()

    var svg = d3.select("#svgViz")

    turnData = gameJson[0]
    /* Zombie location */

    svg.selectAll("zombies")
        .data(turnData["zombies"])
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
        .attr("fill", zombiePos)
        .attr("class", "zombie")
        .attr("r", radius)

    /* Human location */
    svg.selectAll("humans")
        .data(turnData["humans"])
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
        .attr("class", "human")
        .attr("fill", humanPos)
        .attr("r", radius)

    /* Ash location */
    svg.selectAll("ash")
        .data(turnData["Ash"])
        .enter()
        .append("circle")
        .attr("cx", function (d) {
            return x(d["posX"]);
        })
        .attr("cy", function (d) {
            return y(d["posY"]);
        })
        .attr("id", "ashCircle")
        .attr("class", "ash")
        .attr("fill", ashPos)
        .attr("r", radius)

    turn = 1
    while (turn < gameJson.length) {
        console.log("\nTurn: ", turn)
        console.log(gameJson[turn])
        plotTurn(turn, gameJson[turn], svg)
        turn++
    }
}

// Plots the position specific for a given turn
function plotTurn(turn, turnData, svg) {

    /* Zombie location */
    var zombies = svg.selectAll(".zombie").data(turnData["zombies"])
    zombies.transition().delay(duration*turn).duration(duration)
        .attr("cx", function (d) {
            return x(d["posX"]);
        })
        .attr("cy", function (d) {
            return y(d["posY"]);
        })
        .attr("id", function (d) {
            return d["id"];
        })
        .attr("fill", zombiePos)
        .attr("r", function (d) {return deadRadius(d["isDead"])})


    /* Human location */
    var humans = svg.selectAll(".human").data(turnData["humans"])
    humans.transition().delay(duration*turn).duration(duration)
        .attr("cx", function (d) {
            return x(d["posX"]);
        })
        .attr("cy", function (d) {
            return y(d["posY"]);
        })
        .attr("id", function (d) {
            return d["id"];
        })
        .attr("fill", humanPos)
        .attr("r", function (d) {return deadRadius(d["isDead"]);})

    /* Ash location */
    var ash = svg.selectAll(".ash").data(turnData["Ash"])
    ash.transition().delay(duration*turn).duration(duration)
        .attr("cx", function (d) {
            return x(d["posX"]);
        })
        .attr("cy", function (d) {
            return y(d["posY"]);
        })
        .attr("id", function (d) {
            return d["id"];
        })
        .attr("fill", ashPos)
        .attr("r", function (d) {return deadRadius(d["isDead"]);})
}


