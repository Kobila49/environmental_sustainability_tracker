package igor.kos.est.service;

import igor.kos.est.dto.request.UserDataRequest;
import igor.kos.est.entity.User;

import java.util.List;

public interface UserService {

    List<User> allUsers();

    User updateUser(UserDataRequest request, Long id);
}
