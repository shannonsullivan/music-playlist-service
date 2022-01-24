package com.amazon.ata.music.playlist.service.lambda;

import com.amazon.ata.music.playlist.service.dependency.DaggerServiceComponent;
import com.amazon.ata.music.playlist.service.models.requests.GetPlaylistSongsRequest;
import com.amazon.ata.music.playlist.service.models.results.GetPlaylistSongsResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * Entry point to Lambda that handles the request from the API Gateway
 */
public class GetPlaylistSongsActivityProvider implements RequestHandler<GetPlaylistSongsRequest, GetPlaylistSongsResult> {

    public GetPlaylistSongsActivityProvider() {}

    /**
     * Handles the incoming request by retrieving the playlist from the database.
     *
     * Instantiates a GetPlaylistSongsActivity with a playlistDao and DynamoDBMapper
     * from the ServiceComponent interface using the DaoModule class
     *
     * @param getPlaylistSongsRequest request object containing the playlist ID
     * @return the generated DaggerServiceComponent getPlaylistSongsResult result object
     */
    @Override
    public GetPlaylistSongsResult handleRequest(final GetPlaylistSongsRequest getPlaylistSongsRequest, Context context) {
        return DaggerServiceComponent.create()
                .provideGetPlaylistSongsActivity()
                .handleRequest(getPlaylistSongsRequest, context);
    }
}
