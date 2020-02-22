const width = 500;
const height = 500;

// Define the colors
const radius = 10
const ashPos = "blue"
const ashTarget = "green"
const  zombiePos= "red"
const zombieTarget = "ref"
const humanPos = "yellow"

// Define the scales
const x = d3.scaleLinear().domain([0, 16000]).range([radius*2, width]);
const y = d3.scaleLinear().domain([0, 9000]).range([height, radius*2]);

// Run the create SVG function when the page fully loads
function createSVG(){
    d3.select("#game-viz").append("svg")
        .attr("width", width)
        .attr("id", "svgViz")
        .attr("height", height);
}

// Gets the filename from the server and calls plotGame
function getFile(fileName) {
    $.post("/get_game_file", {fileName: fileName}).done(function(json){plotGame(json)})
};


// Plots the game using d3.js
function plotGame(gameJson){

    var turn = 0 // This will change over the time

    var svg = d3.select("#svgViz")

    turnData = gameJson[0]
    /* Zombie location */

    var zombies = svg.selectAll("zombies")
        .data(turnData["zombies"])
        .enter()
        .append("circle")
        .attr("cx", function(d){return x(d["posX"]);})
        .attr("cy", function(d){return y(d["posY"]);})
        .attr("id", function(d){return d["id"];})
        .attr("fill", zombiePos)
        .attr("r", radius)

    /* Human location */
    var humans = svg.selectAll("humans")
        .data(turnData["humans"])
        .enter()
        .append("circle")
        .attr("cx", function(d){return x(d["posX"]);})
        .attr("cy", function(d){return y(d["posY"]);})
        .attr("id", function(d){return d["id"];})
        .attr("fill", humanPos)
        .attr("r", radius)

    /* Ash location */
    var humans = svg.selectAll("ash")
        .data(turnData["Ash"])
        .enter()
        .append("circle")
        .attr("cx", function(d){return x(d["posX"]);})
        .attr("cy", function(d){return y(d["posY"]);})
        .attr("id", "ashCircle")
        .attr("fill", ashPos)
        .attr("r", radius)

    turn = 1
    console.log(gameJson)
    while (turn < gameJson.length){
        plotTurn(turn, gameJson[turn], svg, x, y)
        turn++
    }
}
// Plots the position specific for a given turn
function plotTurn(turn, turnData, svg, x, y){

    console.log("Turn: ", turn)
    /* Zombie location */
    var zombies = svg.selectAll("zombies")
        .data(turnData["zombies"])
        .enter()
        .append("circle")
        .attr("cx", function(d){return x(d["posX"]);})
        .attr("cy", function(d){return y(d["posY"]);})
        .attr("id", function(d){return d["id"];})
        .attr("fill", zombiePos)
        .attr("r", radius)

    /* Human location */
    var humans = svg.selectAll("humans")
        .data(turnData["humans"])
        .enter()
        .append("circle")
        .attr("cx", function(d){return x(d["posX"]);})
        .attr("cy", function(d){return y(d["posY"]);})
        .attr("id", function(d){return d["id"];})
        .attr("fill", humanPos)
        .attr("r", radius)

    /* Ash location */
    var humans = svg.selectAll("ash")
        .data(turnData["Ash"])
        .enter()
        .append("circle")
        .attr("cx", function(d){ return x(d["posX"]);})
        .attr("cy", function(d){return y(d["posY"]);})
        .attr("id", "ashCircle")
        .attr("fill", ashPos)
        .attr("r", radius)
}


