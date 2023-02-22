package jetdevs.service.ServiceImpl;

import jetdevs.exception.FileUploadException;
import jetdevs.model.User;
import jetdevs.service.BaseService;
import jetdevs.utils.UserHelper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BaseServiceImpl implements BaseService {

    @Override
    public User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();
        User user = UserHelper.getUser(auth.getName());
        if (user == null) {
            throw new FileUploadException(HttpStatus.UNAUTHORIZED.value(), "Please login.!!");
        }
        return user;
    }

}
