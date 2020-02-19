function getFile(fileName) {
    $.post("/get_game_file", {fileName: fileName}).done(function(json){console.log(json)})
};

function plotGame(gameJson){
    console.log(gameJson)
}


