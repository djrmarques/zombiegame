from flask import Flask, render_template
import os

app = Flask(__name__)


@app.route('/')
def hello():
    json_files = [file for file in os.listdir("../../../../output") if file.endswith(".json")]
    return render_template("main.html", files=json_files)


if __name__ == '__main__':
    app.run(debug=True)
