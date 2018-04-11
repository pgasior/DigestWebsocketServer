package pl.gasior;

import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.config.ConfigFactory;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.TokenCredentials;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.core.http.HttpActionAdapter;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.http.client.direct.DirectDigestAuthClient;
import org.pac4j.http.credentials.DigestCredentials;
import org.pac4j.http.credentials.authenticator.test.SimpleTestDigestAuthenticator;
import org.pac4j.http.credentials.authenticator.test.SimpleTestUsernamePasswordAuthenticator;
import org.pac4j.sparkjava.DefaultHttpActionAdapter;
import org.pac4j.sparkjava.SparkWebContext;

public class HttpDigestConfigFactory implements ConfigFactory {
    private final String mUser;
    private final String mPassword;

    public HttpDigestConfigFactory(String testUser, String testPassword) {
        mUser = testUser;
        mPassword = testPassword;
    }

    @Override
    public Config build(Object... parameters) {
        final DirectDigestAuthClient directDigestAuthClient = new DirectDigestAuthClient(new SimpleAuthenticator(mUser, mPassword));
        Clients clients = new Clients(directDigestAuthClient);
        Config config = new Config(clients);
        config.setHttpActionAdapter(new DefaultHttpActionAdapter());
        return config;
    }

    private class SimpleAuthenticator implements Authenticator<TokenCredentials> {
        private final String mUsername;
        private final String mPassword;

        public SimpleAuthenticator(String username, String password) {
            mUsername = username;
            mPassword = password;
        }

        @Override
        public void validate(TokenCredentials credentials, WebContext context) throws HttpAction, CredentialsException {
            if (credentials == null) {
                throw new CredentialsException("No credential");
            }
            if (!(credentials instanceof DigestCredentials)) {
                throw new CredentialsException ("Unsupported credentials type " + credentials.getClass());
            }
            DigestCredentials digestCredentials = (DigestCredentials) credentials;
            String username = digestCredentials.getUsername();
            if (!username.equals(mUsername)) {
                throw new CredentialsException("Invalid username");
            }

            String token = credentials.getToken();
            if (!digestCredentials.calculateServerDigest(false, mPassword).equals(token)) {
                throw new CredentialsException("Wrong password");
            }

            CommonProfile profile = new CommonProfile();
            profile.setId(username);
            credentials.setUserProfile(profile);
        }
    }
}
