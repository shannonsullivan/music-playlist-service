package com.amazon.ata.music.playlist.service.dependency;

import com.amazon.ata.aws.dynamodb.DynamoDbClientProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Module for providing object for the PlaylistDao and AlbumTrackDao that can't be
 * instantiated directly by Dagger.
 */
@Module
// MapperModule
public class DaoModule {
    /**
     * Provides an outside vendor's DynamoDBMapper.
     * @return The DynamoDBMapper
     */
    @Singleton
    @Provides
    DynamoDBMapper provideDynamoDBMapper() {
        return new DynamoDBMapper(DynamoDbClientProvider.getDynamoDBClient(Regions.US_EAST_2));
    }
}
