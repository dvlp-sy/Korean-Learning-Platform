from flask import Flask
from flask_restx import Api
from getYoutubeData import YoutubeData
from getWords import Words

app = Flask(__name__)
api = Api(app)

api.add_namespace(YoutubeData, "/")
api.add_namespace(Words, "/")

if __name__ == "__main__" :
    app.run(port = 8000, debug = True)