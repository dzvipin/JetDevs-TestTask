package jetdevs.service;

import jetdevs.model.User;

public interface BaseService {
    User getAuthenticatedUser();
}
