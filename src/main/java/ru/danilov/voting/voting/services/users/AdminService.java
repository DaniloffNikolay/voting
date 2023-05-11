package ru.danilov.voting.voting.services.users;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 * User: Nikolai Danilov
 * Date: 11.05.2023
 */
@Service
public class AdminService {

    @PreAuthorize("hasRole('ROLE_ADMIN') and hasRole('ROLE_SOME_OTHER')")
    public void doAdminStuff() {
        System.out.println("Only admin here");
    }
}
