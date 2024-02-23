import info
import requests
import re
from flask import jsonify
from flask_restx import Resource, Namespace
from youtube_transcript_api import YouTubeTranscriptApi


YoutubeData = Namespace("YoutubeData")

def parse_duration(duration) :
    match = re.match(r'PT(\d+H)?(\d+M)?(\d+S)?', duration)
    hours = int(match.group(1)[:-1]) if match.group(1) else 0
    minutes = int(match.group(2)[:-1]) if match.group(2) else 0
    seconds = int(match.group(3)[:-1]) if match.group(3) else 0
    return hours*3600 + minutes*60 + seconds

@YoutubeData.route("/getYoutubeData/<video_id>", methods=["GET"])
class GetYoutubeData(Resource) :
    def get(self, video_id):
        GOOGLE_API_KEY = info.GOOGLE_API_KEY
        url = f"https://www.googleapis.com/youtube/v3/videos?id={video_id}&key={GOOGLE_API_KEY}&part=snippet,contentDetails,statistics,status"
        response = requests.get(url)
        video_data = response.json()

        video = video_data["items"][0]

        title = video["snippet"]["title"]
        creator = video["snippet"]["channelTitle"]
        duration = parse_duration(video["contentDetails"]["duration"])
        youtubeViews = video["statistics"]["viewCount"]
        categoryId = video["snippet"]["categoryId"]
        
        transcriptList = YouTubeTranscriptApi.list_transcripts(video_id)
        transcript = transcriptList.find_transcript(['ko']).fetch()
        transcriptData = []

        for i in range(len(transcript)) :
            data = transcript[i]
            start = data["start"]
            text = data["text"]

            if (i == len(transcript)-1) :
                start = data["start"]
                t_duration = data["duration"]
            else :
                nextStart = transcript[i+1]["start"]
                t_duration = nextStart-start
            
            transcriptData.append(
                {
                    "text" : text,
                    "start" : start,
                    "duration" : t_duration
                }
            )

        return jsonify(
            {
                "title" : title,
                "creator" : creator,
                "duration" : duration,
                "youtubeViews" : youtubeViews,
                "category" : categoryId,
                "transcriptData" : transcriptData
            }
        )

