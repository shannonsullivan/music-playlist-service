package com.amazon.ata.music.playlist.service.lambda;

import com.amazon.ata.music.playlist.service.dependency.DaggerServiceComponent;
import com.amazon.ata.music.playlist.service.models.requests.CreatePlaylistRequest;
import com.amazon.ata.music.playlist.service.models.results.CreatePlaylistResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * Entry point to Lambda that handles the request from the API Gateway
 */
public class CreatePlaylistActivityProvider implements RequestHandler<CreatePlaylistRequest, CreatePlaylistResult> {

    public CreatePlaylistActivityProvider() {}

    /**
     * Handles the incoming request by retrieving the playlist from the database.
     *
     * Instantiates a CreatePlaylistActivity with a playlistDao and DynamoDBMapper
     * from the ServiceComponent interface using the DaoModule class
     *
     * @param createPlaylistRequest request object containing the playlist ID
     * @return the generated DaggerServiceComponent createPlaylistResult result object
     */
    @Override
    public CreatePlaylistResult handleRequest(final CreatePlaylistRequest createPlaylistRequest, Context context) {
        return DaggerServiceComponent.create()
                .provideCreatePlaylistActivity()
                .handleRequest(createPlaylistRequest, context);
    }
}
