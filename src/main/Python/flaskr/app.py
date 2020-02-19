from flask import Flask, render_template, request, jsonify
import json
import os

app = Flask(__name__)


@app.route('/')
def hello():
    json_files = [file for file in os.listdir("../../../../output") if file.endswith(".json")]
    return render_template("main.html", files=json_files)


@app.route('/get_game_file', methods=["GET", "POST"])
def get_game_file():
    game_file_name = request.values["fileName"]
    game_file_path = os.path.join("../../../../output", game_file_name)
    with open(game_file_path, "r") as f:
        game_json = json.load(f)

    return jsonify(game_json)


if __name__ == '__main__':
    app.run(debug=True)
