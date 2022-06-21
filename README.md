## Music Playlist Service
Service allows customer to create, retrieve, and update custom list of songs. 
Customer can add song to beginning or end of list and retrieve songs in order, 
revers order, or shuffled.

API endpoints are implemented for client to create, get, and update playlist. The 
client will log user in and pass the customer ID when calling the playlist service.

* Dagger framework provides dependency injection
* AWS Lambda and API Gateway are used for method calls and service management tools
* Cloudformation and DynamoDB create collection tables and store data
* LinkedList collection is used to queue song at start of list
* Custom exceptions created invalid requests 
    * `PlaylistNotFoundException`
    * `InvalidAttributeValueExcpetion`
    * `AlbumTrackNotFoundException`
  
#### Get Playlist Endpoint

* Accepts `GET` requests to `/playlists/:id`
* Accepts a playlist ID to return the corresponding playlist for
* If the given playlist ID is not found, will throw a `PlaylistNotFoundException`

#### Create Playlist Endpoint

* Accepts `POST` requests to `/playlists`
* Accepts data to create a new playlist with a provided name, a given customer
  ID, and an optional list of tags. Returns the new playlist, including a
  unique playlist ID assigned by the Music Playlist Service.
* If the customer ID or playlist name contains any of the invalid characters,
  will throw an `InvalidAttributeValueException`.

#### Update Playlist Endpoint

* Accepts `PUT` requests to `/playlists/:id`
* Accepts data to update a playlist including a playlist ID, an updated
  playlist name, and the customer ID associated with the playlist. Returns
  the updated playlist.
* If the playlist ID is not found, will throw a `PlaylistNotFoundException`
* If the customer ID or playlist name contains any of the invalid characters,
  will throw an `InvalidAttributeValueException`.

#### Add Song To Playlist Endpoint

* Accepts `POST` requests to `/playlists/:id/songs`
* Accepts a playlist ID and a song to be added. The song is specified by the
  album’s ASIN and song track number
* If the playlist is not found, will throw a `PlaylistNotFoundException`
* If the given album ASIN doesn’t exist, or if the given track number does
  not exist for the album ASIN, will throw an `AlbumTrackNotFoundException`

#### Get Playlist Songs Endpoint

* Accepts `GET` requests to `/playlists/:id/songs`
* Retrieves all songs of a playlist with the given playlist ID
    * Returns the song list in default playlist order
    * If the optional `order` parameter is provided, this API will return the
      song list in order, reverse order, or shuffled order, based on the value
      of `order`
        * DEFAULT - same as default behavior, returns songs in playlist order
        * REVERSED - returns playlist songs in reversed order
        * SHUFFLED - returns playlist songs in a randomized order
* If the playlist ID is not found, will throw a `PlaylistNotFoundException`
