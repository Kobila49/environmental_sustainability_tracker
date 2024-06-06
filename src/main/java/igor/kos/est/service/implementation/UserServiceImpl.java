package igor.kos.est.service.implementation;

import igor.kos.est.dto.request.UserDataRequest;
import igor.kos.est.entity.User;
import igor.kos.est.exceptions.NoEntityFoundException;
import igor.kos.est.repository.UserRepository;
import igor.kos.est.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> allUsers() {
        return new ArrayList<>(userRepository.findAll());
    }

    public User updateUser(UserDataRequest request, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoEntityFoundException(STR."User not found with id: \{id}"));
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setDateOfBirth(request.dateOfBirth());
        return userRepository.save(user);
    }
}

