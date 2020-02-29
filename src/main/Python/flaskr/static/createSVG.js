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

    printStatus(turnData)
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
