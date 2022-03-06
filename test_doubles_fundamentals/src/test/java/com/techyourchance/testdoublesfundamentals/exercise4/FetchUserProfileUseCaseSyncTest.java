package com.techyourchance.testdoublesfundamentals.exercise4;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.techyourchance.testdoublesfundamentals.example4.networking.NetworkErrorException;
import com.techyourchance.testdoublesfundamentals.exercise4.networking.UserProfileHttpEndpointSync;
import com.techyourchance.testdoublesfundamentals.exercise4.users.User;
import com.techyourchance.testdoublesfundamentals.exercise4.users.UsersCache;

import org.jetbrains.annotations.Nullable;
import org.junit.Before;
import org.junit.Test;

public class FetchUserProfileUseCaseSyncTest {
    public static final String USER_ID = "USER_ID";
    public static final String IMAGE_URL = "IMAGE_URL";
    public static final String FULL_NAME = "FULL_NAME";

    UserProfileHttpEndpointSyncTd mUserProfileHttpEndpointSyncTd;
    FetchUserProfileUseCaseSync SUT;

    @Before
    public void setUp() throws Exception {
        mUserProfileHttpEndpointSyncTd = new UserProfileHttpEndpointSyncTd();
        SUT = new FetchUserProfileUseCaseSync(mUserProfileHttpEndpointSyncTd, new UserCacheTd());
    }

    // fetch user profile succeeds - Interaction with API
    @Test
    public void fetchUserProfile_userProfileSuccess_interactionWithAPI() {
        SUT.fetchUserProfileSync(USER_ID);
        assertThat(mUserProfileHttpEndpointSyncTd.mUserId, is(USER_ID));
    }

    // fetch user profile succeeds - profile cache
    @Test
    public void fetchUserProfile_userProfileSuccess_userProfileCached() {
        SUT.fetchUserProfileSync(USER_ID);
    }

    // fetch user profile fails - General error - profile not cached
    // fetch user profile fails - network error - profile not cached
    // fetch user profile fails - auth error - profile not cached
    // fetch user profile fails - server error - profile not cached

    // ---------------------------------------------------------------------------------------------
    // Helper classes
    private static class UserProfileHttpEndpointSyncTd implements UserProfileHttpEndpointSync {
        public String mUserId = "";

       @Override
        public EndpointResult getUserProfile(String userId) throws NetworkErrorException {
            mUserId = userId;
            return new EndpointResult(EndpointResultStatus.SUCCESS, userId, FULL_NAME, IMAGE_URL);
        }
    }

    private static class UserCacheTd implements UsersCache {
        @Override
        public void cacheUser(User user) {
        }

        @Nullable
        @Override
        public User getUser(String userId) {
            return null;
        }
    }
}