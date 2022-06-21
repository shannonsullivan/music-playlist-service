package com.amazon.ata.music.playlist.service.lambda;

import com.amazon.ata.music.playlist.service.dependency.DaggerServiceComponent;
import com.amazon.ata.music.playlist.service.models.requests.GetPlaylistRequest;
import com.amazon.ata.music.playlist.service.models.results.GetPlaylistResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * Entry point to Lambda that handles the request from the API Gateway
 */
public class GetPlaylistActivityProvider implements RequestHandler<GetPlaylistRequest, GetPlaylistResult> {

    public GetPlaylistActivityProvider() {}

    /**
     * Handles the incoming request by retrieving the playlist from the database.
     *
     * Instantiates a GetPlaylistActivity with a playlistDao and DynamoDBMapper
     * from the ServiceComponent interface using the DaoModule class
     *
     * @param getPlaylistRequest request object containing the playlist ID
     * @return the generated DaggerServiceComponent getPlaylistResult result object
     */
    @Override
    public GetPlaylistResult handleRequest(final GetPlaylistRequest getPlaylistRequest, Context context) {
        return DaggerServiceComponent.create()
                .provideGetPlaylistActivity()
                .handleRequest(getPlaylistRequest, context);
    }
}
