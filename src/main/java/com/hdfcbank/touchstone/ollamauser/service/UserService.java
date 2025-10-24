package com.hdfcbank.touchstone.ollamauser.service;

import com.hdfcbank.touchstone.ollamauser.entity.Department;
import com.hdfcbank.touchstone.ollamauser.entity.Role;
import com.hdfcbank.touchstone.ollamauser.entity.User;
import com.hdfcbank.touchstone.ollamauser.repository.DepartmentRepository;
import com.hdfcbank.touchstone.ollamauser.repository.RoleRepository;
import com.hdfcbank.touchstone.ollamauser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;

    public User createUser(String username, String email, String password, Set<String> roleNames, Set<String> departmentNames) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }

        Set<Role> roles = new HashSet<>();
        for (String r : roleNames) {
            Role role = roleRepository.findByName(r).orElseGet(() -> roleRepository.save(Role.builder().name(r).build()));
            roles.add(role);
        }

        Set<Department> depts = new HashSet<>();
        for (String d : departmentNames) {
            Department dept = departmentRepository.findByName(d).orElseGet(() -> departmentRepository.save(Department.builder().name(d).build()));
            depts.add(dept);
        }

        User user = User.builder()
                .username(username)
                .email(email)
                .password(password)
                .roles(roles)
                .departments(depts)
                .build();

        return userRepository.save(user);
    }
}
