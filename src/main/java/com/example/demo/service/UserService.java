package com.example.demo.service;

import com.example.demo.dto.CartDto;
import com.example.demo.dto.UserDto;
import com.example.demo.model.Address;
import com.example.demo.model.Cart;
import com.example.demo.model.User;
import com.example.demo.model.enums.Role;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserInfoDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    public User getUserById(UUID userId) {
//        return userRepository.findById(userId).orElse(null);
//    }
//
//    public List<User> getAllUsers(){
//        return userRepository.findAll();
//    }
//    public User saveUser(User user) {
//        boolean isUpdatedUser = (user.getId() != null);
//        if (isUpdatedUser) {
//            User existingUser = userRepository.getReferenceById(user.getId());
//
//            if (user.getPassword().isEmpty()) {
//                user.setPassword(existingUser.getPassword());
//            } else {
//                encodePassword(user);
//            }
//        } else {
//            encodePassword(user);
//        }
//        userRepository.save(user);
//        return user;
//    }
//    public void encodePassword(User user) {
//        String encodePass = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encodePass);
//    }
//    public User getUser(UUID id) throws UserNotFoundException {
//        try {
//            return userRepository.getReferenceById(id);
//        } catch (NoSuchElementException ex) {
//            throw new UserNotFoundException("Couldn't find any user with id " + id);
//        }
//    }
//
//    public void deleteUser(UUID userId) {
//        userRepository.deleteById(userId);
//    }
//    public String isEmailUnique(UUID id, String email) {
//        Optional<User> userByEmail = userRepository.findUserByEmail(email);
//        boolean isCreatingNew = (id == null);
//
//        if (isCreatingNew) {
//            if (userByEmail != null) return "Duplicate";
//        } else {
//            if (!Objects.equals(userByEmail.get().getId(), id)) {
//                return "Duplicate";
//            }
//        }
//        return "OK";
//    }
//    public boolean checkLoginRegistration(String email) {
//        Optional<User> user = userRepository.findUserByEmail(email);
//        return user.isEmpty();
//    }
//    public Optional<User> findUserByEmail(String email){
//        return userRepository.findUserByEmail(email);
//    }
//
//
//    public User createUser(User user){
//        return userRepository.save(user);
//    }
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CartService cartService;

    @Autowired
    private WishService wishService;

    public Optional<User> findUserByUserName(String username){
        return userRepository.findUserByUserName(username);
    }

    public Boolean userNameExists(String username){ return userRepository.existsByUsername(username);}

    public Optional<User> findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    public Boolean userEmailExists(String email){ return userRepository.existsByEmail(email);}

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(UUID id){
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User user, boolean isAdmin){
        User newUser = new User(user.getUsername(), user.email, passwordEncoder.encode(user.getPassword()), user.getPhone(), isAdmin);
        wishService.createWish(newUser.getWish());
        cartService.createWish(newUser.getCart());
        return userRepository.save(newUser);
    }

    public User updateUser(UUID id, String userName, String password){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            user.get().setUsername(userName);
            if(!password.isEmpty())
                user.get().setPassword(passwordEncoder.encode(password));
            return userRepository.save(user.get());
        }
        return null;
    }

    public User updateUserByAdmin(UUID id, String userName, String password, String email, List<Role> roles, String address, boolean enable){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            user.get().setUsername(userName);
            user.get().setPassword(passwordEncoder.encode(password));
            user.get().setEmail(email);
            user.get().setRoles(roles);
            return userRepository.save(user.get());
        }
        return null;
    }

    public User deActivateUser(UUID id){
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(value -> value.setEnabled(false));
        return user.orElse(null);
    }

    public User activate(UUID id){
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(value -> value.setEnabled(true));
        return user.orElse(null);
    }

    public boolean validId(UserDetails userDetails, String id){
        UserInfoDetails userInfoDetails = (UserInfoDetails) userDetails;
        return userInfoDetails.getUserId().equals(id);
    }
    public UserDto mapToDto(User user){
        return new UserDto(user.getId(),user.getUsername(),user.getEmail(),user.getPassword(),user.getRoles(),user.getPhone(),user.isDeleted(),user.getOrders(),user.getWish().getId(),user.getCart().getId(), user.getAddresses());
    }
    public List<UserDto> mapToDtoList(List<User> users){
        return users.stream().map(this::mapToDto).collect(Collectors.toList());
    }

}