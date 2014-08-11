package com.example.godong.westudy.Activity;

import android.app.AlertDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;


import com.common.CommonUtil;
import com.example.godong.westudy.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.json.JSONObject;

import com.facebook.AppEventsLogger;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphPlace;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.FriendPickerFragment;
import com.facebook.widget.LoginButton;
import com.facebook.widget.PickerFragment;
import com.facebook.widget.PlacePickerFragment;
import com.network.HttpUtil;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * A login screen that offers login via email/password.

 */
public class LoginActivity extends FragmentActivity implements LoaderCallbacks<Cursor>{

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
//    private UserLoginTask mAuthTask = null;

    /** UI References **/
    private EditText emailEdit;
    private EditText pwEdit;
    private View mProgressView;
    private View mLoginFormView;


    /** Facebook Login에 사용되는 변수 선언 **/
    private static final String PERMISSION = "publish_actions";
    private static final Location SEATTLE_LOCATION = new Location("") {
        {
            setLatitude(47.6097);
            setLongitude(-122.3331);
        }
    };

    private final String PENDING_ACTION_BUNDLE_KEY = "com.facebook.samples.hellofacebook:PendingAction";

    private LoginButton loginButton;
    private PendingAction pendingAction = PendingAction.NONE;
    private ViewGroup controlsContainer;
    private GraphUser user;
    private GraphPlace place;
    private List<GraphUser> tags;
    private boolean canPresentShareDialog;
    private boolean canPresentShareDialogWithPhotos;

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private enum PendingAction {
        NONE,
        POST_PHOTO,
        POST_STATUS_UPDATE
    }
    private UiLifecycleHelper uiHelper;

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
        @Override
        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
            Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
        }

        @Override
        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
            Log.d("HelloFacebook", "Success!");
        }
    };

    /** Facebook Login 관련 선언 끝 **/


    /** Activity Init **/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            String name = savedInstanceState.getString(PENDING_ACTION_BUNDLE_KEY);
            pendingAction = PendingAction.valueOf(name);
        }

        setContentView(R.layout.activity_login);

        emailEdit = (EditText) findViewById(R.id.login_edit_email);
        pwEdit = (EditText) findViewById(R.id.login_edit_pw);

        /** Local ID로 Login 하는 버튼 **/
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Set up the login form. **/
                /** Email 작성란 **/
                /** Password 작성란 **/

                RequestParams params = new RequestParams();
                params.put("email",emailEdit.getText());
                params.put("password",pwEdit.getText());

                HttpUtil.post("http://192.168.1.7:3000/login", null, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                        // called when response HTTP status is "200 OK"
                        Toast toast = Toast.makeText(getApplicationContext(), "로그인 성공!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 100);
                        toast.show();

                        Intent intentLoginActivity = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intentLoginActivity);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        JSONObject errObj = CommonUtil.stringToJSONObject(new String(errorResponse));

                        try {
                            String errMsg = errObj.getString("message");
                            Toast toast = Toast.makeText(getApplicationContext(), errMsg, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } catch (Exception je) {
                            Log.e("JSON Error", je.toString());
                        }
                    }
                });




                /** login **/
//                attemptLogin();
            }
        });

        Button signUpButton = (Button) findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(
            new OnClickListener() {
                public void onClick(View v) {
                    Intent intentJoinActivity = new Intent(LoginActivity.this, JoinActivity.class);
                    startActivity(intentJoinActivity);
                }
            });

        /** Facebook ID로 Login 하는 버튼 **/
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                LoginActivity.this.user = user;
                updateUI();
                // It's possible that we were waiting for this.user to be populated in order to post a
                // status update.
                handlePendingAction();
            }
        });


        controlsContainer = (ViewGroup) findViewById(R.id.main_ui_container);

        final FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment != null) {
            // If we're being re-created and have a fragment, we need to a) hide the main UI controls and
            // b) hook up its listeners again.
            controlsContainer.setVisibility(View.GONE);
            if (fragment instanceof FriendPickerFragment) {
                setFriendPickerListeners((FriendPickerFragment) fragment);
            } else if (fragment instanceof PlacePickerFragment) {
                setPlacePickerListeners((PlacePickerFragment) fragment);
            }
        }

        // Listen for changes in the back stack so we know if a fragment got popped off because the user
        // clicked the back button.
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (fm.getBackStackEntryCount() == 0) {
                    // We need to re-show our UI.
                    controlsContainer.setVisibility(View.VISIBLE);
                }
            }
        });

        // Can we present the share dialog for regular links?
        canPresentShareDialog = FacebookDialog.canPresentShareDialog(this,
                FacebookDialog.ShareDialogFeature.SHARE_DIALOG);
        // Can we present the share dialog for photos?
        canPresentShareDialogWithPhotos = FacebookDialog.canPresentShareDialog(this,
                FacebookDialog.ShareDialogFeature.PHOTOS);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

    }


    /************************************** Local Login 관련 Method *********************************************/

    private void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }


    /*********************************** Facebook Login 관련 Method ********************************************/


    @Override
    protected void onResume() {
        super.onResume();
        uiHelper.onResume();

        // Call the 'activateApp' method to log an app event for use in analytics and advertising reporting.  Do so in
        // the onResume methods of the primary Activities that an app may be launched into.
        AppEventsLogger.activateApp(this);

        updateUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);

        outState.putString(PENDING_ACTION_BUNDLE_KEY, pendingAction.name());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data, dialogCallback);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (pendingAction != PendingAction.NONE &&
                (exception instanceof FacebookOperationCanceledException ||
                        exception instanceof FacebookAuthorizationException)) {
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle(R.string.cancelled)
                    .setMessage(R.string.permission_not_granted)
                    .setPositiveButton(R.string.ok, null)
                    .show();
            pendingAction = PendingAction.NONE;
        } else if (state == SessionState.OPENED_TOKEN_UPDATED) {
            handlePendingAction();
        }
        updateUI();
    }

    private void updateUI() {
        Session session = Session.getActiveSession();
//        boolean enableButtons = (session != null && session.isOpened());
//
//        postStatusUpdateButton.setEnabled(enableButtons || canPresentShareDialog);
//        postPhotoButton.setEnabled(enableButtons || canPresentShareDialogWithPhotos);
//        pickFriendsButton.setEnabled(enableButtons);
//        pickPlaceButton.setEnabled(enableButtons);
//
//        if (enableButtons && user != null) {
//            profilePictureView.setProfileId(user.getId());
//            greeting.setText(getString(R.string.hello_user, user.getFirstName()));
//        } else {
//            profilePictureView.setProfileId(null);
//            greeting.setText(null);
//        }
    }

    @SuppressWarnings("incomplete-switch")
    private void handlePendingAction() {
        PendingAction previouslyPendingAction = pendingAction;
        // These actions may re-set pendingAction if they are still pending, but we assume they
        // will succeed.
        pendingAction = PendingAction.NONE;

        switch (previouslyPendingAction) {
            case POST_PHOTO:
                postPhoto();
                break;
            case POST_STATUS_UPDATE:
                postStatusUpdate();
                break;
        }
    }

    private interface GraphObjectWithId extends GraphObject {
        String getId();
    }

    private void showPublishResult(String message, GraphObject result, FacebookRequestError error) {
        String title = null;
        String alertMessage = null;
        if (error == null) {
            title = getString(R.string.success);
            String id = result.cast(GraphObjectWithId.class).getId();
            alertMessage = getString(R.string.successfully_posted_post, message, id);
        } else {
            title = getString(R.string.error);
            alertMessage = error.getErrorMessage();
        }

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(alertMessage)
                .setPositiveButton(R.string.ok, null)
                .show();
    }

    private void onClickPostStatusUpdate() {
        performPublish(PendingAction.POST_STATUS_UPDATE, canPresentShareDialog);
    }

    private FacebookDialog.ShareDialogBuilder createShareDialogBuilderForLink() {
        return new FacebookDialog.ShareDialogBuilder(this)
                .setName("Hello Facebook")
                .setDescription("The 'Hello Facebook' sample application showcases simple Facebook integration")
                .setLink("http://developers.facebook.com/android");
    }

    private void postStatusUpdate() {
        if (canPresentShareDialog) {
            FacebookDialog shareDialog = createShareDialogBuilderForLink().build();
            uiHelper.trackPendingDialogCall(shareDialog.present());
        } else if (user != null && hasPublishPermission()) {
            final String message = getString(R.string.status_update, user.getFirstName(), (new Date().toString()));
            Request request = Request
                    .newStatusUpdateRequest(Session.getActiveSession(), message, place, tags, new Request.Callback() {
                        @Override
                        public void onCompleted(Response response) {
                            showPublishResult(message, response.getGraphObject(), response.getError());
                        }
                    });
            request.executeAsync();
        } else {
            pendingAction = PendingAction.POST_STATUS_UPDATE;
        }
    }

    private void onClickPostPhoto() {
        performPublish(PendingAction.POST_PHOTO, canPresentShareDialogWithPhotos);
    }

    private FacebookDialog.PhotoShareDialogBuilder createShareDialogBuilderForPhoto(Bitmap... photos) {
        return new FacebookDialog.PhotoShareDialogBuilder(this)
                .addPhotos(Arrays.asList(photos));
    }

    private void postPhoto() {
        Bitmap image = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_drawer);
        if (canPresentShareDialogWithPhotos) {
            FacebookDialog shareDialog = createShareDialogBuilderForPhoto(image).build();
            uiHelper.trackPendingDialogCall(shareDialog.present());
        } else if (hasPublishPermission()) {
            Request request = Request.newUploadPhotoRequest(Session.getActiveSession(), image, new Request.Callback() {
                @Override
                public void onCompleted(Response response) {
                    showPublishResult(getString(R.string.photo_post), response.getGraphObject(), response.getError());
                }
            });
            request.executeAsync();
        } else {
            pendingAction = PendingAction.POST_PHOTO;
        }
    }

    private void showPickerFragment(PickerFragment<?> fragment) {
        fragment.setOnErrorListener(new PickerFragment.OnErrorListener() {
            @Override
            public void onError(PickerFragment<?> pickerFragment, FacebookException error) {
                String text = getString(R.string.exception, error.getMessage());
                Toast toast = Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

        controlsContainer.setVisibility(View.GONE);

        // We want the fragment fully created so we can use it immediately.
        fm.executePendingTransactions();

        fragment.loadData(false);
    }

    private void onClickPickFriends() {
        final FriendPickerFragment fragment = new FriendPickerFragment();

        setFriendPickerListeners(fragment);

        showPickerFragment(fragment);
    }

    private void setFriendPickerListeners(final FriendPickerFragment fragment) {
        fragment.setOnDoneButtonClickedListener(new FriendPickerFragment.OnDoneButtonClickedListener() {
            @Override
            public void onDoneButtonClicked(PickerFragment<?> pickerFragment) {
                onFriendPickerDone(fragment);
            }
        });
    }

    private void onFriendPickerDone(FriendPickerFragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();

        String results = "";

        List<GraphUser> selection = fragment.getSelection();
        tags = selection;
        if (selection != null && selection.size() > 0) {
            ArrayList<String> names = new ArrayList<String>();
            for (GraphUser user : selection) {
                names.add(user.getName());
            }
            results = TextUtils.join(", ", names);
        } else {
            results = getString(R.string.no_friends_selected);
        }

        showAlert(getString(R.string.you_picked), results);
    }

    private void onPlacePickerDone(PlacePickerFragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();

        String result = "";

        GraphPlace selection = fragment.getSelection();
        if (selection != null) {
            result = selection.getName();
        } else {
            result = getString(R.string.no_place_selected);
        }

        place = selection;

        showAlert(getString(R.string.you_picked), result);
    }

    private void onClickPickPlace() {
        final PlacePickerFragment fragment = new PlacePickerFragment();
        fragment.setLocation(SEATTLE_LOCATION);
        fragment.setTitleText(getString(R.string.pick_seattle_place));

        setPlacePickerListeners(fragment);

        showPickerFragment(fragment);
    }

    private void setPlacePickerListeners(final PlacePickerFragment fragment) {
        fragment.setOnDoneButtonClickedListener(new PlacePickerFragment.OnDoneButtonClickedListener() {
            @Override
            public void onDoneButtonClicked(PickerFragment<?> pickerFragment) {
                onPlacePickerDone(fragment);
            }
        });
        fragment.setOnSelectionChangedListener(new PlacePickerFragment.OnSelectionChangedListener() {
            @Override
            public void onSelectionChanged(PickerFragment<?> pickerFragment) {
                if (fragment.getSelection() != null) {
                    onPlacePickerDone(fragment);
                }
            }
        });
    }

    private void showAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null)
                .show();
    }

    private boolean hasPublishPermission() {
        Session session = Session.getActiveSession();
        return session != null && session.getPermissions().contains("publish_actions");
    }

    private void performPublish(PendingAction action, boolean allowNoSession) {
        Session session = Session.getActiveSession();
        if (session != null) {
            pendingAction = action;
            if (hasPublishPermission()) {
                // We can do the action right away.
                handlePendingAction();
                return;
            } else if (session.isOpened()) {
                // We need to get new permissions, then complete the action when we get called back.
                session.requestNewPublishPermissions(new Session.NewPermissionsRequest(this, PERMISSION));
                return;
            }
        }

        if (allowNoSession) {
            pendingAction = action;
            handlePendingAction();
        }
    }

}



