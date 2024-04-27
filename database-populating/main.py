import pymongo
from pymongo import MongoClient
import os
import base64
import json
from requests import post, get
CLIENT_ID= "9ae889ff3843458cbf365530b585f9a3"
CLIENT_SECRET= "ca07f779424c428e9dd2b80beb9dbe50"
API_URL = "https://api.spotify.com/v1/"

cluster = MongoClient("mongodb+srv://emiliomoya620:<password>@cluster0.46qe8uw.mongodb.net/")
db = cluster["music-rater-db"]
collection_albums = db["albums"]
collection_artists = db["artists"]
#collection_reviews = db["reviews"]
#collection_roles = db["roles"]
collection_songs = db["songs"]
#collection_users = db["users"]

#collection_songs.insert_one({})
#collection_songs.insert_many([{}, {}])

def get_token():
    auth_string = CLIENT_ID + ":" + CLIENT_SECRET
    print(auth_string)
    auth_bytes  = auth_string.encode("utf-8")
    auth_base64 = str(base64.b64encode(auth_bytes), "utf-8")

    url = "https://accounts.spotify.com/api/token"
    headers = {
        "Authorization": "Basic " + auth_base64,
        "Content-Type": "application/x-www-form-urlencoded"
    }

    data = {"grant_type": "client_credentials"}
    result = post(url, headers=headers, data=data)
    json_result = result.json()#json.loads(result.content)
    print(f"json result: {json_result}")
    token = json_result["access_token"]
    return token

def get_auth_header(token):
    return {"Authorization": "Bearer " + token}

def search_for_artist(token, artist_name):
    url = "https://api.spotify.com/v1/search" #API_URL + "search"
    headers = get_auth_header(token)
    query = f"?q={artist_name}&type=artist&limit=1" 
    query_url = url+query
    result = get(query_url, headers=headers)
    json_result = result.json()["artists"]["items"]
    if len(json_result) == 0:
        print("no artist found")
        return None
    return json_result[0]

def artists_albums(token, artist_id):
    url = f"{API_URL}artists/{artist_id}/albums"
    query = "?include_groups=album"
    headers = get_auth_header(token)
    query_url = url+query
    result = get(query_url, headers=headers).json()
    return result

def albums_tracks(token, album_id):
    url = f"{API_URL}/albums/{album_id}/tracks"
    headers = get_auth_header(token)
    result = get(url, headers=headers).json()
    return result


def search_for_album(token, album_id):
    url = f"{API_URL}albums/{album_id}"
    headers = get_auth_header(token)
    result = get(url, headers=headers).json()
    return result


def parse_songs(token, album_id):
    tracks = albums_tracks(token, album_id)
    songs = tracks["items"]
    object_ids = []
    for song in songs:
        song_info = {
            
        }

    
    return object_ids
    

def parse_albums(token, albums):
    items = albums["items"]
    for album in items:
        album_id = album["id"]
        songs_object_ids = parse_songs(token, album_id)
        album_json = search_for_album(token, album_id)
        album_info = {
            "title": album_json['name'],
            "releaseDate": album_json["release_date"],
            "score": 0,
            "tracklist": songs_object_ids,
            "genres": album_json["genres"],
            "reviews": [],
            "coverArt": album_json["images"]["url"],
            "_class": "com.project.tempotalk.models.Album"    
                }
    album_object_ids = []
    return album_object_ids 

def parse_artist(token, artist_name):
    #first, search for an artist. this will have an artist id.
    artist = search_for_artist(token, artist_name)
    artist_id = artist["id"]
    #use the artist id to find the artists albums. this will have ids to individual albums
    artists_albums = artists_albums(token, artist_id)
    # parse json for artists albums. this will have ids to each album
    album_object_ids = parse_albums(token, artists_albums)
    # use the album id for two things: search for the album, and get the tracks from the album  
    #parse each individual track and upload object to database. return object id and append to list of song object ids.
    #using the song object id list, complete the album object and return the album object ids as a list
    #using the album object ids, finish the artist object
    


token = get_token()
result = search_for_artist(token, "The Smile")
#print(result["id"])
eminem_id = search_for_artist(token, "The Smile")["id"]
eminem_albums = artists_albums(token, eminem_id)
print(eminem_albums["items"])
#parsed_albums = 