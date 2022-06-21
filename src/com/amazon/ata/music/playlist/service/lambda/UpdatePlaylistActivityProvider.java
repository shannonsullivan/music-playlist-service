package com.amazon.ata.music.playlist.service.lambda;

import com.amazon.ata.music.playlist.service.dependency.DaggerServiceComponent;
import com.amazon.ata.music.playlist.service.models.requests.UpdatePlaylistRequest;
import com.amazon.ata.music.playlist.service.models.results.UpdatePlaylistResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * Entry point to Lambda that handles the request from the API Gateway
 */
public class UpdatePlaylistActivityProvider implements RequestHandler<UpdatePlaylistRequest, UpdatePlaylistResult> {

    public UpdatePlaylistActivityProvider() {}

    /**
     * Handles the incoming request by retrieving the playlist from the database.
     *
     * Instantiates a UpdatePlaylistActivity with a playlistDao and DynamoDBMapper
     * from the ServiceComponent interface using the DaoModule class
     *
     * @param updatePlaylistRequest request object containing the playlist ID
     * @return the generated DaggerServiceComponent updatePlaylistResult result object
     */
    @Override
    public UpdatePlaylistResult handleRequest(final UpdatePlaylistRequest updatePlaylistRequest, Context context) {
        return DaggerServiceComponent.create()
                .provideUpdatePlaylistActivity()
                .handleRequest(updatePlaylistRequest, context);
    }
}
