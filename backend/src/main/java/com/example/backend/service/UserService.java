package com.example.backend.service;
import com.example.backend.model.User;
import com.example.backend.model.Car;
import com.example.backend.repository.CarRepository;
import com.example.backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
@Service
public class UserService {
    @Autowired
    private CarRepository carRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User register(String username, String password, String role, String email){
        if(userRepository.findByUsername(username).isPresent()){
            throw new RuntimeException("Tên đăng nhập đã tồn tại.");
        }
        if(userRepository.findByEmail(email).isPresent()){
            throw new RuntimeException("Email đã tồn tại");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        user.setEmail(email);
        return userRepository.save(user);
    }

    public boolean login(String username, String password){
        Optional<User> userOpt = userRepository.findByUsername(username);
        if(userOpt.isPresent()){
            User user = userOpt.get();
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }

    public boolean resetPassword(String username, String email, String newpassword){
        Optional<User> userOpt = userRepository.findByUsername(username);
        if(userOpt.isPresent()){
            User user = userOpt.get();
            if(user.getEmail().equals(email)){
                user.setPassword(passwordEncoder.encode(newpassword));
                userRepository.save(user);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User createUser(User user){
        return userRepository.save(user);
    }
    public User getUser(Long id){
    return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng."));
    }

    public User updateUser(Long id, User userDetails){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng."));

        user.setEmail(userDetails.getEmail());
        user.setUsername(userDetails.getUsername());
        return userRepository.save(user);
    }


    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public boolean addCartoUser(Long userId, Car car){
        return userRepository.findById(userId).map(user -> {
            car.setOwner(user);
            carRepository.save(car);
            return true;
        }).orElse(false);
    }
}
