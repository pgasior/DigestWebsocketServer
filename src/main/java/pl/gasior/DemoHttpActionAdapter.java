package pl.gasior;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.http.HttpActionAdapter;

public class DemoHttpActionAdapter implements HttpActionAdapter {
    @Override
    public Object adapt(int code, WebContext context) {
        return null;
    }
}
