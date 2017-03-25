package com.example.deepakgarg.capstoneproject.data;

import ckm.simple.sql_provider.UpgradeScript;
import ckm.simple.sql_provider.annotation.ProviderConfig;
import ckm.simple.sql_provider.annotation.SimpleSQLConfig;

/**
 * Created by Deepak Garg on 26-03-2017.
 */

@SimpleSQLConfig(
        name = "FavouritesProvider",
        authority = "com.example.deepakgarg.capstoneproject",
        database = "favourites.db",
        version = 1)

public class FavProvider implements ProviderConfig {

    @Override
    public UpgradeScript[] getUpdateScripts() {
        return new UpgradeScript[0];
    }

}