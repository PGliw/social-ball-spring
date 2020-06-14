package pwr.zpi.socialballspring.config;

public class AuthenticationConstants {
    private AuthenticationConstants() {
    }

    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5 * 60 * 60;
    public static final String SIGNING_KEY = "SportAppKey";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
