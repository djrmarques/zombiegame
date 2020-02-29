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

// fontsize
const fontSize = 10

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

function deadText(isDead){
    if (isDead === 1) return 0; else return fontSize;
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

    if (selectName == "zombies" || selectName == "humans") {
        svg.selectAll(selectName + "Text")
            .data(turnData[selectName])
            .enter()
            .append("text")
            .attr("x", function (d) {
                return x(d["posX"])-radius/2;
            })
            .attr("y", function (d) {
                return y(d["posY"])+radius/2;
            })
            .text(function (d) {
                return d["id"];
            })
            .attr("class", selectName + "Text")
            .attr("font-size", fontSize)
    }
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

function updatePos(turn, turnData, selectName, fill) {

    // Print the position of everyone on the window


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
        svg.selectAll("." + selectName + "Line")
            .data(turnData[selectName])
            .transition()
            .duration(duration)
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
            .attr("stroke-width", function (d) {
                return deadStroke(d["isDead"])
            })
    }

    if (selectName == "zombies" || selectName == "humans") {
        svg.selectAll("."+selectName + "Text")
            .data(turnData[selectName])
            .transition().duration(duration)
            .attr("x", function (d) {
                return x(d["posX"])-radius/2;
            })
            .attr("y", function (d) {
                return y(d["posY"])+radius/2;
            })
            .attr("font-size", function(d) {
            return deadText(d["isDead"]);
            })
    }
}

// Plots the position specific for a given turn
function plotTurn(turn) {

    let turnData = globalGameJson[turn]
    printStatus(turnData)

    // Display which turn it is
    $("#turn").text(turn)


    /* Zombie location */
    updatePos(turn, turnData, "zombies", zombiePos);

    /* Ash location */
    updatePos(turn, turnData, "Ash", ashPos);

    /* Human location */
    updatePos(turn, turnData, "humans", humanPos);

}

// Print the status of every element on the screen
function printStatus(turnData){
    // console.log("Turn: ", turn);
    // console.log(turnData);
    // console.log(turnData);

    // Reset the content of the status
    $("#status-log").content = "";

    const reducer = (acc, item) => {return acc + item};

    // Empty the previous logs
    $("#status-log").empty();

    // Print Ash status
    let ashStats = turnData["Ash"][0];
    let ashString = Object.keys(ashStats).map(d => {return d + ": " + ashStats[d] + " | "}).reduce(reducer, "");
    $("#status-log").append("ASH: " + ashString);

    let i;
    for (i=0; i<turnData["zombies"].length; i++){
        let zombieStats = turnData["zombies"][i];
        let zombieString = Object.keys(zombieStats).map(d => {return d + ": " + zombieStats[d] + " | "}).reduce(reducer, "");
        $("#status-log").append("<p> Zombie" + i + ": " + zombieString + "<p>");
    }
}



