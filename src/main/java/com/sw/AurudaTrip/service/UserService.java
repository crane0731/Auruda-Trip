package com.sw.AurudaTrip.service;

import com.sw.AurudaTrip.domain.User;
import com.sw.AurudaTrip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User findUser(Long id){
        return userRepository.findById(id).orElseThrow(()->new RuntimeException("회원을 찾을 수 없습니다."));
    }
}
