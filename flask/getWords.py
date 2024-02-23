import info
import requests
import re
from flask import jsonify
from flask_restx import Resource, Namespace

Words = Namespace("Words")

headers = { 
    "Referer" : info.REFERER,
    "User-Agent": info.USER_AGENT
}

@Words.route("/getWords/<sentence>")
class getWords(Resource) :
    def get(self, sentence) :
        wordsData = []
        url = f"https://ko.dict.naver.com/api3/koko/search?query={sentence}"
        response = requests.get(url, headers = headers)

        if (response.status_code == 200) :
            data = response.json()
            words = data["searchResultMap"]["searchResultListMap"]["WORD"]["items"]

        for word in words :
            parsed_word = re.sub('<[^<]+?>', '', word["expEntry"])
            print(parsed_word)

            examplesData = []
            url = f"https://ko.dict.naver.com/api3/koko/search?query={parsed_word}"
            response = requests.get(url, headers = headers)

            if (response.status_code == 200) :
                data = response.json()
                examples = data["searchResultMap"]["searchResultListMap"]["EXAMPLE"]["items"]
            
            for example in examples :
                parsed_example = re.sub('<[^<]+?>', '', example["expExample1"])
                examplesData.append(parsed_example)

            wordsData.append(
                {
                    "word" : parsed_word,
                    "examples" : examplesData
                }
            )
        
        return jsonify(
            {
                "wordsData" : wordsData
            }
        )