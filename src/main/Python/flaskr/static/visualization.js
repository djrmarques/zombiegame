// Gets the filename from the server and calls plotGame
function getFile(fileName) {
    $.post("/get_game_file", {fileName: fileName}).done(function(json){plotGame(json)})
};

// Plots the game using d3.js
function plotGame(gameJson){
    console.log(gameJson)
}


