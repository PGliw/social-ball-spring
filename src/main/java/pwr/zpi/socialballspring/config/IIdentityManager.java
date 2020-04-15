package pwr.zpi.socialballspring.config;

import pwr.zpi.socialballspring.model.User;

public interface IIdentityManager {
    User getCurrentUser();
}
