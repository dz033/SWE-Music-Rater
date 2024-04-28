import pymongo
from pymongo import MongoClient
import os
import base64
import json
from requests import post, get
CLIENT_ID= "9ae889ff3843458cbf365530b585f9a3"
CLIENT_SECRET= "ca07f779424c428e9dd2b80beb9dbe50"
API_URL = "https://api.spotify.com/v1/"

cluster = MongoClient("mongodb+srv://emiliomoya620:qM8DwyMzKq3Qam5J@cluster0.46qe8uw.mongodb.net/")
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
    #print(auth_string)
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
    #print(f"json result: {json_result}")
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
    url = f"{API_URL}albums/{album_id}/tracks"
    #print(f"error url: {url}")
    headers = get_auth_header(token)
    result = get(url, headers=headers).json()
    #print(f"ERROR LINE HERE: {result}")
    return result


def search_for_album(token, album_id):
    url = f"{API_URL}albums/{album_id}"
    headers = get_auth_header(token)
    result = get(url, headers=headers).json()
    return result


def parse_songs(token, album_id, artist_name, album_json, artist_genres):
    tracks = albums_tracks(token, album_id)
    #print(f"tracks: {tracks}")
    songs = tracks["items"]
    object_ids = []
    for song in songs:
        song_info = {
            "title": song["name"],
            "artist": artist_name,
            "releaseDate": album_json["release_date"],
            "score": 0,
            "genres": artist_genres,#album_json["genres"],
            "reviews": [],
            "coverArt": album_json["images"][0]["url"], #index 0 is the url
            "_class": "com.project.tempotalk.models.Song"
        }
        print(f"{song["name"]} info: {song_info} \n")
        object_ids.append(collection_songs.insert_one(song_info).inserted_id)
    return object_ids
    

def parse_albums(token, albums, artist_name, artist_genres):
    items = albums["items"]
    album_object_ids = []
    all_songs_ids = []
    for album in items:
        album_id = album["id"]
        album_json = search_for_album(token, album_id)
        if len(album_json["artists"]) == 1:
            songs_object_ids = parse_songs(token, album_id, artist_name, album_json, artist_genres)
            album_info = {
                "title": album_json['name'],
                "artist": artist_name,
                "releaseDate": album_json["release_date"],
                "score": 0,
                "tracklist": songs_object_ids,
                "genres": artist_genres,#album_json["genres"],
                "reviews": [],
                "coverArt": album_json["images"][0]["url"],
                "_class": "com.project.tempotalk.models.Album"    
                    }
            all_songs_ids.extend(songs_object_ids)
            print(f"{album["name"]} info: {album_info} \n")
            album_object_ids.append(collection_albums.insert_one(album_info).inserted_id)
    
    return album_object_ids, all_songs_ids

def parse_artist(token, artist_name):
    #first, search for an artist. this will have an artist id.
    artist = search_for_artist(token, artist_name)
    artist_id = artist["id"]
    #use the artist id to find the artists albums. this will have ids to individual albums
    artist_albums = artists_albums(token, artist_id)
    # parse json for artists albums. this will have ids to each album
    album_object_ids, songs_object_ids = parse_albums(token, artist_albums, artist["name"], artist["genres"])
    # use the album id for two things: search for the album, and get the tracks from the album  
    #parse each individual track and upload object to database. return object id and append to list of song object ids.
    #using the song object id list, complete the album object and return the album object ids as a list
    #using the album object ids, finish the artist object
    artist_info = {
        "name": artist["name"],
        "genres": artist["genres"],
        "discography": album_object_ids,
        "singles": songs_object_ids,
        "artistImage": artist["images"][0]["url"],
        "_class": "com.project.tempotalk.models.Artist" 
    }
    print(f"artist info: {artist_info} \n")
    collection_artists.insert_one(artist_info)


token = get_token()


def parse_multiple_artists(token, artist_list):
    f = open("database-populating\parsed_artists.txt", "r")
    parsed_artists = f.read().splitlines()
    f.close()
    for artist in artist_list:
        f = open("database-populating\parsed_artists.txt", "a")
        if artist.lower().strip() not in parsed_artists:
            f.write(f"{artist}\n")
            parse_artist(token, artist.lower())
        f.close()

artist_list = ["reol", "jj lin", "jay chou", "taylor swift", "mariah carey", "elton john", "the beatles", "michael jackson", 
               "kanye west", "ac/dc", "prince", "coldplay", "bruno mars", "ed sheeran", "le sserafim", "maroon 5", "the weeknd", "G.E.M", 
               "billie eilish", "yoasobi", "(g)i-dle", "imagine dragons", "the chainsmokers", "bol4", "one ok rock", "maneskin", "beyonce"]
parse_multiple_artists(token, artist_list)