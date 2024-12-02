//package dev.chel_shev.fast.scheduler;
//
//import com.google.api.client.auth.oauth2.Credential;
//import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
//import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
//import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
//import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.googleapis.json.GoogleJsonResponseException;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.client.util.store.FileDataStoreFactory;
//import com.google.api.services.plus.Plus;
//import com.google.api.services.plus.PlusScopes;
//import com.google.api.services.youtube.YouTube;
//import com.google.api.services.youtube.model.SubscriptionListResponse;
//
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.security.GeneralSecurityException;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.Collections;
//
//import static com.sun.org.apache.xalan.internal.xsltc.compiler.Constants.REDIRECT_URI;
//
//public class YoutubeSchedulerApi {
//    private static final String CLIENT_SECRETS = "/client_secret_1030574782219-jo2sknph7eu1n06jesd2qtofpuk6bn3q.apps.googleusercontent.com.json";
//    private static final Collection<String> SCOPES =
//            Arrays.asList("https://www.googleapis.com/auth/youtube.readonly");
//
//    private static final String APPLICATION_NAME = "API code samples";
//    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
//
//    /**
//     * Be sure to specify the name of your application. If the application name is {@code null} or
//     * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
//     */
//
//    /**
//     * Directory to store user credentials.
//     */
//    private static final java.io.File DATA_STORE_DIR =
//            new java.io.File(System.getProperty("user.home"), ".store/plus_sample");
//
//    private static FileDataStoreFactory dataStoreFactory;
//
//    /**
//     * Global instance of the HTTP transport.
//     */
//    private static HttpTransport httpTransport;
//
//    static {
//        try {
//            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//        } catch (GeneralSecurityException | IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * Global instance of the JSON factory.
//     */
//    private static Plus plus;
//
//    /**
//     * Authorizes the installed application to access user's protected data.
//     */
//    private static Credential authorize() throws Exception {
//        // load client secrets
//        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
//                new InputStreamReader(YoutubeSchedulerApi.class.getResourceAsStream(CLIENT_SECRETS)));
//        if (clientSecrets.getDetails().getClientId().startsWith("Enter")
//                || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
//            System.out.println(
//                    "Enter Client ID and Secret from https://code.google.com/apis/console/?api=plus "
//                            + "into plus-cmdline-sample/src/main/resources/client_secrets.json");
//            System.exit(1);
//        }
//        // set up authorization code flow
//        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//                httpTransport, JSON_FACTORY, clientSecrets,
//                Collections.singleton(PlusScopes.PLUS_ME)).setDataStoreFactory(dataStoreFactory).build();
//        // authorize
//        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
//    }
//
//
//
//
//
//
//
//    public static void auth() throws IOException {
//        // Exchange auth code for access token
//        GoogleClientSecrets clientSecrets =
//                GoogleClientSecrets.load(
//                        JacksonFactory.getDefaultInstance(), new FileReader(CLIENT_SECRETS));
//        GoogleTokenResponse tokenResponse =
//                new GoogleAuthorizationCodeTokenRequest(
//                        new NetHttpTransport(),
//                        JacksonFactory.getDefaultInstance(),
//                        "https://oauth2.googleapis.com/token",
//                        clientSecrets.getDetails().getClientId(),
//                        clientSecrets.getDetails().getClientSecret(),
//                        authCode,
//                        REDIRECT_URI)  // Specify the same redirect URI that you use with your web
//                        // app. If you don't have a web version of your app, you can
//                        // specify an empty string.
//                        .execute();
//
//        String accessToken = tokenResponse.getAccessToken();
//    }
//
//
//
//
//
//    /**
//     * Build and return an authorized API client service.
//     *
//     * @return an authorized API client service
//     * @throws GeneralSecurityException, IOException
//     */
//    public static YouTube getService() throws Exception {
//        Credential credential = authorize();
//        return new YouTube.Builder(httpTransport, JSON_FACTORY, credential)
//                .setApplicationName(APPLICATION_NAME)
//                .build();
//    }
//
//    /**
//     * Call function to create API service object. Define and
//     * execute API request. Print API response.
//     *
//     * @throws GeneralSecurityException, IOException, GoogleJsonResponseException
//     */
//    public static void main(String[] args)
//            throws Exception {
//        httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//        dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
//        YouTube youtubeService = getService();
//        // Define and execute the API request
//        YouTube.Subscriptions.List request = youtubeService.subscriptions().list(Collections.singletonList("snippet"));
//        SubscriptionListResponse response = request.setChannelId("UCopYYmQC1SGEVI_EFRhphMA").execute();
//        System.out.println(response);
//    }
//}
