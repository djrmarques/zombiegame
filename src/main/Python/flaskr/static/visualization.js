// Gets the filename from the server and calls plotGame
function getFile(fileName) {
    $.post("/get_game_file", {fileName: fileName}).done(function(json){plotGame(json)})
};

// Plots the game using d3.js
function plotGame(gameJson){

    const width = 500;
    const height = 500;
    var turn = 0 // This will change over the time

    var svg = d3.select("#game-viz").append("svg")
        .attr("width", width)
        .attr("height", height);

    // Define the scales
    var x = d3.scaleLinear().domain([0, 16000]).range([10, width]);
    var y = d3.scaleLinear().domain([0, 9000]).range([10, height]);

    // x(20); // 80
    // x(50); // 320

    plotTurn(turn, gameJson[turn], svg, x, y)
}

// Plots the position specific for a given turn
function plotTurn(turn, turnData, svg, x, y){

    // Define the colors
    const radious = 50
    const ashPos = "blue"
    const ashTarget = "green"
    const  zombiePos= "red"
    const zombieTarget = "ref"
    const humanPos = "yellow"

    // console.log("Turn:", turn)
    // console.log(turnData["zombies"])
    var ash = svg.selectAll("zombies")
        .data(turnData)
        .enter()
        .append("circle")
        .attr("cx", function(d){console.log(d); return x(d["Zombies"][0]["posX"]);})
        .attr("cy", function(d){return y(d["Zombies"][0]["posY"]);})
        .attr("r", radious)
        .attr("fill", zombiePos)
}


