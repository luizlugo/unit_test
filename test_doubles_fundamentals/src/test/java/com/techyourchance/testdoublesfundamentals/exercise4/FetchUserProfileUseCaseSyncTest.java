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
    UserCacheTd mUserCacheTd;
    FetchUserProfileUseCaseSync SUT;

    @Before
    public void setup() throws Exception {
        mUserProfileHttpEndpointSyncTd = new UserProfileHttpEndpointSyncTd();
        mUserCacheTd = new UserCacheTd();
        SUT = new FetchUserProfileUseCaseSync(mUserProfileHttpEndpointSyncTd, mUserCacheTd);
    }

    // fetch user profile succeeds - Interaction with API
    @Test
    public void fetchUserProfile_userProfileSuccess_interactionWithAPI() {
        SUT.fetchUserProfileSync(USER_ID);
        assertThat(mUserProfileHttpEndpointSyncTd.mUserId, is(USER_ID));
        assertThat(mUserCacheTd.mInteractions, is(1));
    }

    // fetch user profile fails - General error
    @Test
    public void fetchUserProfile_userProfileFails_generalError() {
        mUserProfileHttpEndpointSyncTd.mIsGeneralError = true;
        FetchUserProfileUseCaseSync.UseCaseResult result = SUT.fetchUserProfileSync(USER_ID);
        assertThat(result, is(FetchUserProfileUseCaseSync.UseCaseResult.FAILURE));
        assertThat(mUserCacheTd.mInteractions, is(0));
    }

    // fetch user profile fails - Network error
    @Test
    public void fetchUserProfile_userProfileFails_networkError() {
        mUserProfileHttpEndpointSyncTd.mIsNetworkError = true;
        FetchUserProfileUseCaseSync.UseCaseResult result = SUT.fetchUserProfileSync(USER_ID);
        assertThat(result, is(FetchUserProfileUseCaseSync.UseCaseResult.NETWORK_ERROR));
        assertThat(mUserCacheTd.mInteractions, is(0));
    }

    // fetch user profile fails - auth error - profile not cached
    @Test
    public void fetchUserProfile_userProfileFails_authError() {
        mUserProfileHttpEndpointSyncTd.mIsAuthError = true;
        FetchUserProfileUseCaseSync.UseCaseResult result = SUT.fetchUserProfileSync(USER_ID);
        assertThat(result, is(FetchUserProfileUseCaseSync.UseCaseResult.FAILURE));
        assertThat(mUserCacheTd.mInteractions, is(0));
    }

    // fetch user profile fails - server error - profile not cached
    @Test
    public void fetchUserProfile_userProfileFails_serverError() {
        mUserProfileHttpEndpointSyncTd.mIsServerError = true;
        FetchUserProfileUseCaseSync.UseCaseResult result = SUT.fetchUserProfileSync(USER_ID);
        assertThat(result, is(FetchUserProfileUseCaseSync.UseCaseResult.FAILURE));
        assertThat(mUserCacheTd.mInteractions, is(0));
    }

    // ---------------------------------------------------------------------------------------------
    // Helper classes
    private static class UserProfileHttpEndpointSyncTd implements UserProfileHttpEndpointSync {
        public String mUserId = "";
        public boolean mIsGeneralError;
        public boolean mIsNetworkError;
        public boolean mIsServerError;
        public boolean mIsAuthError;

       @Override
        public EndpointResult getUserProfile(String userId) throws NetworkErrorException {
            mUserId = userId;

            if (mIsGeneralError) {
                return new EndpointResult(EndpointResultStatus.GENERAL_ERROR, mUserId, null, null);
            } else if (mIsAuthError) {
                return new EndpointResult(EndpointResultStatus.AUTH_ERROR, mUserId, null, null);
            } else if (mIsServerError) {
                return new EndpointResult(EndpointResultStatus.SERVER_ERROR, mUserId, null, null);
            } else if (mIsNetworkError) {
                throw new NetworkErrorException();
            }

            return new EndpointResult(EndpointResultStatus.SUCCESS, userId, FULL_NAME, IMAGE_URL);
        }
    }

    private static class UserCacheTd implements UsersCache {
        public User mUser;
        public int mInteractions;

        @Override
        public void cacheUser(User user) {
            mUser = user;
            mInteractions++;
        }

        @Nullable
        @Override
        public User getUser(String userId) {
            return mUser;
        }
    }
}