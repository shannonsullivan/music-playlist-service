package com.amazon.ata.music.playlist.service.lambda;

import com.amazon.ata.music.playlist.service.dependency.DaggerServiceComponent;
import com.amazon.ata.music.playlist.service.models.requests.AddSongToPlaylistRequest;
import com.amazon.ata.music.playlist.service.models.results.AddSongToPlaylistResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * Entry point to Lambda that handles the request from the API Gateway
 */
public class AddSongToPlaylistActivityProvider implements RequestHandler<AddSongToPlaylistRequest, AddSongToPlaylistResult> {

    public AddSongToPlaylistActivityProvider() {}

    /**
     * Handles the incoming request by retrieving the playlist from the database.
     *
     * Instantiates a AddSongToPlaylistActivity with a playlistDao and DynamoDBMapper
     * from the ServiceComponent interface using the DaoModule class
     *
     * @param addSongToPlaylistRequest request object containing the playlist ID
     * @return the generated DaggerServiceComponent addSongToPlaylistResult result object
     */
    @Override
    public AddSongToPlaylistResult handleRequest(final AddSongToPlaylistRequest addSongToPlaylistRequest, Context context) {
        return DaggerServiceComponent.create()
                .provideAddSongToPlaylistActivity()
                .handleRequest(addSongToPlaylistRequest, context);
    }
}
